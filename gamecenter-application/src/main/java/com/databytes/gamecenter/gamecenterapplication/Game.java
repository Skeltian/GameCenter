package com.databytes.gamecenter.gamecenterapplication;

import java.util.ArrayList;

/**
 * The Class Game.
 */
public class Game {

	/** The release year. */
	private int gameid, releaseYear;
	
	/** The rating. */
	private String title, desc, rating;
	
	/** The user reviews. */
	private ArrayList<Preferences> userReviews;
	
	/** The rating avg. */
	private double ratingAvg;
	
	/**
	 * Instantiates a new game.
	 */
	public Game() {
		
	}
	
	/**
	 * Instantiates a new game.
	 *
	 * @param gameid the gameid
	 * @param title the title
	 * @param releaseYear the release year
	 * @param rating the rating
	 * @param desc the desc
	 */
	public Game(int gameid, String title, int releaseYear, String rating, String desc) {
		this.gameid = gameid;
		this.releaseYear = releaseYear;
		this.rating = rating;
		this.title = title;
		this.desc = desc;
	}
	
	/**
	 * Contructor used for calculating statistics.
	 *
	 * @param title Title of game
	 * @param ratingAvg Average rating of the game
	 */
	public Game(String title, double ratingAvg) {
		this.title = title;
		this.setRatingAvg(ratingAvg);
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
	 * Gets the release year.
	 *
	 * @return the release year
	 */
	public int getReleaseYear() {
		return releaseYear;
	}
	
	/**
	 * Sets the release year.
	 *
	 * @param releaseYear the new release year
	 */
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	
	/**
	 * Sets the rating.
	 *
	 * @param rating the new rating
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param desc the new description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Gets the user reviews.
	 *
	 * @return the user reviews
	 */
	public ArrayList<Preferences> getUserReviews() {
		return userReviews;
	}
	
	/**
	 * Sets the user reviews.
	 *
	 * @param userReviews the new user reviews
	 */
	public void setUserReviews(ArrayList<Preferences> userReviews) {
		this.userReviews = userReviews;
	}
	
	/**
	 * Gets the rating avg.
	 *
	 * @return the rating avg
	 */
	public double getRatingAvg() {
		return ratingAvg;
	}
	
	/**
	 * Sets the rating avg.
	 *
	 * @param ratingAvg the new rating avg
	 */
	public void setRatingAvg(double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}
	
}
