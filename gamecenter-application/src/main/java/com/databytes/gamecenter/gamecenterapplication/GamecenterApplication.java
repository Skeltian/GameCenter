package com.databytes.gamecenter.gamecenterapplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * The Class GamecenterApplication.
 */
@SpringBootApplication
public class GamecenterApplication {

	/** The database. */
	@Autowired
	public static MysqlDataSource database = new MysqlDataSource();
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(GamecenterApplication.class, args);
	}

}
