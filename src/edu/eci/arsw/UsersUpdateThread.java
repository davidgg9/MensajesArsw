package edu.eci.arsw;

import java.awt.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;


import org.apache.activemq.ActiveMQConnectionFactory;

import edu.eci.arsw.gestorUsuarios.UpdateUserList;
import edu.eci.arsw.server.DireccionServidor;

public class UsersUpdateThread extends Thread {
	List destiny;
	String user;

	MessageConsumer consumer=null;

	Session session;
	Connection connection;
	public UsersUpdateThread(List destiny, String user) {
		super();
		this.destiny=destiny;
		this.user =user;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://"+DireccionServidor.direccionServidor+":61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createTopic("lu");
			consumer = session.createConsumer(destination);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void run (){
		while (true){
			try {
				ObjectMessage message = (ObjectMessage)consumer.receive();	
				String[] userlist=((UpdateUserList)message.getObject()).getUserList();
				destiny.removeAll();
				for (int i = 0; i < userlist.length; i++) {
					if(!userlist[i].equals(user))destiny.add(userlist[i]);
				}
				Thread.sleep(100);
			} catch (JMSException e) {
				throw new RuntimeException("Error on JMS message processing");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}


}
