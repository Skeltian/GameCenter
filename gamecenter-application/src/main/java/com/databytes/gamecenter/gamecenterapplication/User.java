package com.databytes.gamecenter.gamecenterapplication;

import java.util.ArrayList;

/**
 * The Class User.
 */
public class User {
	
	/** The userid. */
	private int userid;
	
	/** The sex. */
	private String fName, lName, addr, username, password, sex;
	
	/** The friends. */
	private ArrayList<User> friends; // list of friend's ids
	
	/** The ratings. */
	private ArrayList<Preferences> ratings; // list of user's ratings
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param userid the userid
	 * @param fName the f name
	 * @param lName the l name
	 * @param addr the addr
	 * @param username the username
	 * @param password the password
	 * @param sex the sex
	 */
	public User(int userid, String fName, String lName, String addr, String username, String password, String sex) {
		this.userid = userid;
		this.fName = fName;
		this.lName = lName;
		this.addr = addr;
		this.username = username;
		this.password = password;
		this.sex = sex;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getfName() {
		return fName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param fName the new first name
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getlName() {
		return lName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lName the new last name
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddr() {
		return addr;
	}
	
	/**
	 * Sets the address.
	 *
	 * @param addr the new address
	 */
	public void setAddr(String addr) {
		this.addr = addr;
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
	
	/**
	 * Gets the friends.
	 *
	 * @return the friends
	 */
	public ArrayList<User> getFriends() {
		return friends;
	}
	
	/**
	 * Sets the friends.
	 *
	 * @param arrayList the new friends
	 */
	public void setFriends(ArrayList<User> arrayList) {
		this.friends = arrayList;
	}
	
	/**
	 * Gets the ratings.
	 *
	 * @return the ratings
	 */
	public ArrayList<Preferences> getRatings() {
		return ratings;
	}
	
	/**
	 * Sets the ratings.
	 *
	 * @param ratings the new ratings
	 */
	public void setRatings(ArrayList<Preferences> ratings) {
		this.ratings = ratings;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the sex.
	 *
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	
	/**
	 * Sets the sex.
	 *
	 * @param sex the new sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

}
