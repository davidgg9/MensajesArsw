package edu.eci.arsw.gestorUsuarios;

import java.io.Serializable;

public class NewUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String user;

	/**
	 * @param user
	 */
	public NewUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
	
	
	

}
