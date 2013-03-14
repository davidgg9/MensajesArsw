package edu.eci.arsw.gestorUsuarios;

import java.awt.List;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceivedUsersAddThread extends Thread {
	MessageConsumer consumer=null;
	Session session;
	Connection connection;
	List listaUsuario;



	public ReceivedUsersAddThread(List listaUsuario) {
		super();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue("ni");
			consumer = session.createConsumer(destination);	
			this.listaUsuario= listaUsuario;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public void run(){
		while(true){
			getNewUsers();
			sendUsersList();

		}

	}


	private void sendUsersList() {
		try {
			Destination destination = session.createTopic("lu");
			 
			MessageProducer producer = session.createProducer(destination);
			ObjectMessage objectMessage = session.createObjectMessage(new UpdateUserList(listaUsuario.getItems())); 
			producer.send(objectMessage);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}


	private void getNewUsers() {
		try {
			ObjectMessage message = (ObjectMessage)consumer.receive();
			listaUsuario.add(((NewUser)message.getObject()).getUser());
			Thread.sleep(100);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
