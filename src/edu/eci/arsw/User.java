package edu.eci.arsw;

import java.io.Serializable;

public class User implements Serializable{

	private String user;

	/**
	 * @param user
	 */
	public User(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
	
	
	

}
