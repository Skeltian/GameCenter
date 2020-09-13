package com.databytes.gamecenter.gamecenterapplication;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Center Controller.
 */
@Controller
public class CenterController {
	
	/** The database manager. */
	DatabaseManager dbm;
	
	/**
	 * Instantiates a new center controller.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Autowired
	public CenterController() throws SQLException {
		dbm = new DatabaseManager();
	}
	
	/**
	 * Search user attribute.
	 *
	 * @return the user
	 */
	@ModelAttribute("searchUser")
	public User searchUser() {
		return new User(); // field for searching users
	}
	
	/**
	 * Search game attribute.
	 *
	 * @return the game
	 */
	@ModelAttribute("searchGame")
	public Game searchGame() {
		return new Game();
	}
	
	/**
	 * Review game attribute.
	 *
	 * @return the game
	 */
	@ModelAttribute("reviewGame")
	public Game reviewGame() {
		return new Game();
	}
	
	/**
	 * Gets the error page.
	 *
	 * @param model the model
	 * @return the error page
	 */
	@GetMapping("/error")
	public String getError(Model model) {
		model.addAttribute("searchUser", new User());
		return "error";
	}
	
	/**
	 * Gets the home page.
	 *
	 * @param model the model
	 * @return the home page
	 */
	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("topGames", dbm.getTopGames());
		model.addAttribute("topGamesMale", dbm.getTopGamesMale());
		model.addAttribute("topGamesFemale", dbm.getTopGamesFemale());
		model.addAttribute("allGames", dbm.getAllGames());
		
		return "index";
	}
	
	/**
	 * Gets the signup page.
	 *
	 * @param model the model
	 * @return the signup page
	 */
	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("userinfo", new User());
		return "signup";
	}
	
	/**
	 * Add user to DB if username is unique.
	 *
	 * @param userinfo the userinfo attribute
	 * @return the home page
	 */
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute("userinfo") User userinfo) {
		
		if (dbm.getUser(userinfo.getUsername()) != null) {
			return "error_duplicateusername";
		} 
		
		dbm.addUser(userinfo.getfName(), userinfo.getlName(), userinfo.getAddr(), userinfo.getUsername(), userinfo.getPassword(), userinfo.getSex());
		return "redirect:/";
	}
	
	/**
	 * Gets the login page.
	 *
	 * @param model the model
	 * @return the login page
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("userinfo", new User());
		return "login";
	}
	
	/**
	 * Determines if the user's input is valid. If so, the user is logged in.
	 *
	 * @param userinfo the userinfo attribute
	 * @param response the HTTP response
	 * @param usernameCookie the username cookie
	 * @return the home page
	 */
	@PostMapping("/login")
	public String postLogin(@ModelAttribute User userinfo, HttpServletResponse response,
    		@CookieValue(value = "username", defaultValue = "") String usernameCookie) {

    	String username = userinfo.getUsername();
    	String password = userinfo.getPassword();
    	User user = dbm.getUser(username);

    	/* prevents operations on null object */
    	if (user == null) {
    		return "error_invalidlogin";
    	}
    	
    	if (password.equals(user.getPassword())) {
    		Cookie userCookie = new Cookie("username", String.valueOf(user.getUsername()));
    		response.addCookie(userCookie);
    		System.out.println("User " + username + " has successfully logged in.");
    		return "redirect:/";
    	} else {
    		return "error_invalidlogin";
    	}
    }
	
	/**
	 * Gets the myprofile page.
	 *
	 * @param model the model
	 * @param username the username cookie
	 * @return the myprofile page
	 */
	@GetMapping("/myprofile")
	public String getMyprofile(Model model, @CookieValue(value="username", defaultValue="") String username) {
		if (username.equals("")) {
			return "error_notloggedin";
		} else {
			User user = dbm.getUser(username);
			user.setFriends(dbm.getFriends(user.getUserid()));
			user.setRatings(dbm.getPreferences(user.getUserid()));
			model.addAttribute("userinfo", user);
			return "myprofile";
		}
	}
	
    /**
     * Logout get.
     *
     * @param response the HTTP response
     * @param username the username cookie
     * @return the home page
     */
    @GetMapping("/logout")
    public String logoutGet(HttpServletResponse response, @CookieValue("username") String username) {

        Cookie userCookie = new Cookie("username", username);
        userCookie.setMaxAge(0);
		response.addCookie(userCookie);
    	return "redirect:/";
    }
    
    /**
     * Searches for the given username and displays a page with information on the user.
     *
     * @param model the model
     * @param searchUser the user to search
     * @param searcher the username of the searcher
     * @return the searched user's profile
     */
    @PostMapping("/searchUser")
    public String searchUserPost(Model model, @ModelAttribute("searchUser") User searchUser, 
    		@CookieValue(value="username", defaultValue="") String searcher) {
    	User user = dbm.getUser(searchUser.getUsername());
    	if (user == null) { /* no user found */
    		return "error_nouserfound";
    	} else if (searcher.equals(user.getUsername())) { /* user searches for theirself */
    		return "error";
    	} else {
    		user.setRatings(dbm.getPreferences(searchUser.getUsername()));
    		model.addAttribute("userinfo", user);
        	return "userinfo";
    	}
    }
    
    /**
     * Addfriend post.
     *
     * @param username the username of the user
     * @param friend the username of the friend
     * @return the home page
     */
    @PostMapping("/addfriend")
    public String addfriendPost(@CookieValue(value="username", defaultValue="") String username,
    		@ModelAttribute("userinfo") User friend) {
    	if (username.equals("")) {
			return "error_notloggedin";
		} else {
			/* determine if user is already friends */
			boolean found = false;
			User user = dbm.getUser(username);
			ArrayList<User> f = dbm.getFriends(user.getUserid());
			if (f != null) {
				for (int i = 0; i < f.size(); i++) {
					if (f.get(i).getUserid()== friend.getUserid()) {
						found = true;
						break;
					}
				}
			}
			
			if (!found) {
				dbm.addFriend(dbm.getUser(username).getUserid(), friend.getUserid());
				return "redirect:/";
			} else {
				return "error_friendalreadyadded";
			}
		}
    }
    
    /**
     * Searches for the given game and displays a page with information on the game.
     *
     * @param model the model
     * @param searchGame the game to search
     * @return the searched game's information
     */
    @PostMapping("/searchGame")
    public String searchGamePost(Model model, @ModelAttribute("searchGame") Game searchGame) {
    	ArrayList<Game> games = dbm.getGames(searchGame.getTitle());
    	if (games.isEmpty()) {
    		return "error_nogamefound";
    	} else {
    		for (int i = 0; i < games.size(); i++) { /* add reviews for each game */
    			Game g = games.get(i);
    			g.setUserReviews(dbm.getGamePreferences(g.getGameid()));
    		}
    		model.addAttribute("gameinfo", games);
        	return "gamepage";
    	}
    	
    }
    
    /**
     * Allows the user to review a game (if they haven't already).
     *
     * @param model the model
     * @param username the username cookie
     * @param title the game's title
     * @param releaseYear the game's release year
     * @return the review page
     */
    @PostMapping("/review*")
    public String reviewPost(Model model, @CookieValue(value="username", defaultValue="") String username,
    		@RequestParam(value="title", required=true) String title,
    		@RequestParam(value="releaseYear", required=false) int releaseYear) {
    	
    	if (username.equals("")) { /* user not logged in */
    		return "error_notloggedin";
    	} else {
    		User user = dbm.getUser(username);
    		Game game = dbm.getGame(title, releaseYear);
    		/* determine if user has already reviewed the game*/
			boolean found = false;
			ArrayList<Preferences> p = dbm.getPreferences(user.getUserid());
			if (p != null) {
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i).getGameid()== game.getGameid()) {
						found = true;
						break;
					}
				}
			}
			
			if (!found) {
				model.addAttribute("preferences", new Preferences(user.getUserid(), game.getGameid(), 
	    				0, new String(), title, username));
				return "review";
			} else {
				return "error_gamealreadyreviewed";
			}
    	}
    }
    
    /**
     * Stores the user's review to the database.
     *
     * @param review the review
     * @return the home page
     */
    @PostMapping("/reviewsuccess")
    public String reviewPost(@ModelAttribute("preferences") Preferences review) {
    	dbm.addPreference(review);
    	return "redirect:/";
    }
}
