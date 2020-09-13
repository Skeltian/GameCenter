package com.databytes.gamecenter.gamecenterapplication;

/**
 * The Class Preferences.
 */
public class Preferences {

	/** The user rating. */
	private int userid, gameid, userRating;
	
	/** The username. */
	private String review, gameTitle, username;
	
	/**
	 * Instantiates a new preferences.
	 */
	public Preferences() {
		
	}
	
	/**
	 * Instantiates a new preferences.
	 *
	 * @param userid the userid
	 * @param gameid the gameid
	 * @param userRating the user rating
	 * @param review the review
	 * @param gameTitle the game title
	 * @param username the username
	 */
	public Preferences(int userid, int gameid, int userRating, String review, String gameTitle, String username) {
		this.userid = userid;
		this.gameid = gameid;
		this.userRating = userRating;
		this.review = review;
		this.gameTitle = gameTitle;
		this.username = username;
	}
	
	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	
	/**
	 * Sets the userid.
	 *
	 * @param userid the new userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	/**
	 * Gets the gameid.
	 *
	 * @return the gameid
	 */
	public int getGameid() {
		return gameid;
	}
	
	/**
	 * Sets the gameid.
	 *
	 * @param gameid the new gameid
	 */
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	
	/**
	 * Gets the user rating.
	 *
	 * @return the user rating
	 */
	public int getUserRating() {
		return userRating;
	}
	
	/**
	 * Sets the user rating.
	 *
	 * @param userRating the new user rating
	 */
	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}
	
	/**
	 * Gets the review.
	 *
	 * @return the review
	 */
	public String getReview() {
		return review;
	}
	
	/**
	 * Sets the review.
	 *
	 * @param review the new review
	 */
	public void setReview(String review) {
		this.review = review;
	}
	
	/**
	 * Gets the game title.
	 *
	 * @return the game title
	 */
	public String getGameTitle() {
		return gameTitle;
	}
	
	/**
	 * Sets the game title.
	 *
	 * @param gameTitle the new game title
	 */
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
