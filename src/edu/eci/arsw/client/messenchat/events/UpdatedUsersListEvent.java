package edu.eci.arsw.client.messenchat.events;

import java.util.LinkedList;
import java.util.List;

//OJO! NO MODIFIQUE EL NOMBRE NI LOS MÉTODOS DE LA CLASE!

public class UpdatedUsersListEvent {

	private List<String> userNamesList;

	public UpdatedUsersListEvent() {
		userNamesList=new LinkedList<String>();
	}
	
	public void addUserName(String name){
		userNamesList.add(name);
	}

	public List<String> getUserNamesList() {
		return userNamesList;
	}

	public void setUserNamesList(List<String> userNamesList) {
		this.userNamesList = userNamesList;
	}
	
}
