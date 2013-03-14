package edu.eci.arsw.gestorUsuarios;

import java.io.Serializable;

public class UpdateUserList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String [] userList;
	
	
	public UpdateUserList (String [] userList){
	this.userList =userList;
	}
	
	
	public String[] getUserList() {
		return userList;
	}
	

}
