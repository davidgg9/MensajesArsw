package edu.eci.arsw.messenchat.events;

//OJO! NO MODIFIQUE EL NOMBRE NI LOS MÉTODOS DE LA CLASE!

public class UserLogoutEvent {

	private String userName;

	public UserLogoutEvent(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
