package com.databytes.gamecenter.gamecenterapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * The Class DatabaseManager.
 */
public class DatabaseManager {

	/** The connection. */
	private static Connection connection;
	
	/**
	 * Instantiates a new database manager.
	 *
	 * @param datasource the datasource
	 * @throws SQLException the SQL exception
	 */
	@Autowired
	public DatabaseManager() throws SQLException {
		MysqlDataSource database = new MysqlDataSource();
		database.setUser("root");
		database.setServerName("localhost");
		database.setPort(3306);
		database.setDatabaseName("gamecenter");
		database.setPassword("z*>L?yZc=3Z%b9b8R,9e4V@X"); /* Replace this with the MySQL server password */
		database.setServerTimezone("EST");
		connection = database.getConnection();
	}
	
	/**
	 * Adds the user to the Database and returns their unique ID.
	 *
	 * @param fName User's first name
	 * @param lName User's last name
	 * @param addr User's address
	 * @param username User's username
	 * @param password User's password
	 * @param sex the sex
	 * @return the int
	 */
	public int addUser(String fName, String lName, String addr, String username, String password, String sex) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO gamecenter.user (fName, lName, addr, username, password, sex) VALUES "
					+ "(?, ?, ?, ?, ?, ?);");
			statement.setString(1, fName);
			statement.setString(2, lName);
			statement.setString(3, addr);
			statement.setString(4, username);
			statement.setString(5, password);
			statement.setString(6, sex);
			statement.executeUpdate();
			
			statement = connection.prepareStatement(
					"SELECT userid FROM gamecenter.user ORDER BY userid DESC LIMIT 1;");
			ResultSet result = statement.executeQuery();
			result.next();
			return result.getInt("userid");
			
		} catch (SQLException e) {
			System.out.println("There was an issue adding the user.");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return -1;
		}
	}
	
	/**
	 * Gets the user.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User getUser(String username) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM gamecenter.user WHERE username = ?;");
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			result.next();
			
			return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getString(6), result.getString(7));
			
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the user [by email].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets all the games with 'title'.
	 *
	 * @param title the title of the game(s)
	 * @return the games
	 */
	public ArrayList<Game> getGames(String title) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM gamecenter.game WHERE title = ?;");
			statement.setString(1, title);
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<Game> games = new ArrayList<Game>();
			while(result.next()) {
				games.add(new Game(result.getInt(1), result.getString(2), result.getInt(3),
						result.getString(4), result.getString(5)));
			}
			return games;
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the game [by title].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the game by title and release year.
	 *
	 * @param title the game's title
	 * @param releaseYear the game's release year
	 * @return the game
	 */
	public Game getGame(String title, int releaseYear) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM gamecenter.game WHERE title = ? AND releaseYear = ?;");
			statement.setString(1, title);
			statement.setInt(2, releaseYear);
			
			ResultSet result = statement.executeQuery();
			result.next();
			
			return new Game(result.getInt(1), result.getString(2), result.getInt(3),
						result.getString(4), result.getString(5));
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the game [by title].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Adds the preference.
	 *
	 * @param review the review
	 */
	public void addPreference(Preferences review) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO gamecenter.preferences (userid, gameid, userRating, review) VALUES "
					+ "(?, ?, ?, ?);");
			statement.setInt(1, review.getUserid());
			statement.setInt(2, review.getGameid());
			statement.setInt(3, review.getUserRating());
			statement.setString(4, review.getReview());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("There was an issue adding the review.");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Adds the friend.
	 *
	 * @param userid1 the userid of user1
	 * @param userid2 the userid of user2
	 */
	public void addFriend(int userid1, int userid2) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO gamecenter.friends (user1, user2) VALUES (?, ?);");
			statement.setInt(1, userid1);
			statement.setInt(2, userid2);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("There was an issue adding friends between user1[" + userid1 + "] and user2[" + userid2 + "].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Gets the friends of a certain user.
	 *
	 * @param userid the user's userid
	 * @return the friends
	 */
	public ArrayList<User> getFriends(int userid) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT U.userid, U.fName, U.lName, U.addr, U.username, U.password, U.sex FROM gamecenter.friends F INNER JOIN gamecenter.user U ON F.user2=U.userid " + 
					"WHERE user1=?;");
			statement.setInt(1, userid);
			
			ResultSet result = statement.executeQuery();
			ArrayList<User> friends = new ArrayList<User>();
			while(result.next()) {
				friends.add(new User(result.getInt(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6), result.getString(7)));
			}
			return friends;
			
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving friends for user[" + userid + "].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the preferences for a certain user.
	 *
	 * @param userid the user's userid
	 * @return the user's preferences
	 */
	public ArrayList<Preferences> getPreferences(int userid) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT P.userid, P.gameid, P.userRating, P.review, G.title, U.username FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid WHERE P.userid=?;");
			statement.setInt(1, userid);
			
			ResultSet result = statement.executeQuery();
			ArrayList<Preferences> reviews = new ArrayList<Preferences>();
			while(result.next()) {
				reviews.add(new Preferences(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4), result.getString(5), 
						result.getString(6)));
			}
			return reviews;
			
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving reviews for user[" + userid + "].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the preferences for a user, given a username.
	 *
	 * @param username the user's username
	 * @return the user's preferences
	 */
	public ArrayList<Preferences> getPreferences(String username) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT P.userid, P.gameid, P.userRating, P.review, G.title, U.username FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid WHERE U.username=?;");
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			ArrayList<Preferences> reviews = new ArrayList<Preferences>();
			while(result.next()) {
				reviews.add(new Preferences(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4), result.getString(5), 
						result.getString(6)));
			}
			return reviews;
			
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving reviews for user[" + username + "].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the preferences for a given game.
	 *
	 * @param gameid the game's gameid
	 * @return the game's preferences
	 */
	public ArrayList<Preferences> getGamePreferences(int gameid) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT P.userid, P.gameid, P.userRating, P.review, G.title, U.username FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid WHERE G.gameid=?;");
			statement.setInt(1, gameid);
			
			ResultSet result = statement.executeQuery();
			ArrayList<Preferences> reviews = new ArrayList<Preferences>();
			while(result.next()) {
				reviews.add(new Preferences(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4), result.getString(5),
						result.getString(6)));
			}
			return reviews;
			
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving reviews for game[" + gameid + "].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the top games.
	 *
	 * @return the games in order of highest rating
	 */
	public ArrayList<Game> getTopGames() {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT G.title, AVG(P.userRating) AS ratingAvg FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid GROUP BY G.title ORDER BY ratingAvg DESC;");
			
			ResultSet result = statement.executeQuery();
			ArrayList<Game> topGames = new ArrayList<Game>();
			while (result.next()) {
				topGames.add(new Game(result.getString(1), result.getDouble(2)));
			}
			return topGames;
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the top games.");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the top games for men.
	 *
	 * @return the top games for men
	 */
	public ArrayList<Game> getTopGamesMale() {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT G.title, AVG(P.userRating) AS ratingAvg FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid WHERE U.SEX='M' GROUP BY G.title ORDER BY ratingAvg DESC;");
			
			ResultSet result = statement.executeQuery();
			ArrayList<Game> topGames = new ArrayList<Game>();
			while (result.next()) {
				topGames.add(new Game(result.getString(1), result.getDouble(2)));
			}
			return topGames;
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the top games [for males].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the top games for women.
	 *
	 * @return the top games for women
	 */
	public ArrayList<Game> getTopGamesFemale() {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT G.title, AVG(P.userRating) AS ratingAvg FROM gamecenter.user U INNER JOIN gamecenter.preferences P ON U.userid=P.userid " + 
					"INNER JOIN gamecenter.game G ON P.gameid=G.gameid WHERE U.SEX='F' GROUP BY G.title ORDER BY ratingAvg DESC;");
			
			ResultSet result = statement.executeQuery();
			ArrayList<Game> topGames = new ArrayList<Game>();
			while (result.next()) {
				topGames.add(new Game(result.getString(1), result.getDouble(2)));
			}
			return topGames;
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the top games [for males].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets all games in the database.
	 *
	 * @return all games
	 */
	public ArrayList<Game> getAllGames() {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM gamecenter.game;");
			
			
			ResultSet result = statement.executeQuery();
			ArrayList<Game> games = new ArrayList<Game>();
			while (result.next()) {
				games.add(new Game(result.getInt(1), result.getString(2), result.getInt(3), result.getString(4),
						result.getString(5)));
			}
			return games;
		} catch (SQLException e) {
			System.out.println("There was an issue retrieving the top games [for males].");
			System.err.println("Error " + e.getErrorCode() + ": " + e.getMessage());
			return null;
		}
	}
	
}
