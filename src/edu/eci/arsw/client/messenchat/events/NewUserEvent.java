package edu.eci.arsw.client.messenchat.events;


//OJO! NO MODIFIQUE EL NOMBRE NI LOS M�TODOS DE LA CLASE!

public class NewUserEvent {

	private String userName;

	public NewUserEvent(String userName) {
		super();
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}
	
}
