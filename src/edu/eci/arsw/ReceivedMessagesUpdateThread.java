package edu.eci.arsw;

import java.awt.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.swing.JTextArea;

import org.apache.activemq.ActiveMQConnectionFactory;


public class ReceivedMessagesUpdateThread extends Thread {

	private List area;
	private String actualUser;
	
	MessageConsumer consumer=null;

	Session session;
	Connection connection;

	public ReceivedMessagesUpdateThread(List receivedMessages, String user) {
		super();ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://localhost:61616");
		actualUser=user;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createTopic("MENSAJE");
			consumer = session.createConsumer(destination);	
			this.area = receivedMessages;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	public void run(){
		String user;
		while (true){
			try {
				ObjectMessage message = (ObjectMessage)consumer.receive();	
				if(area.getItemCount()==area.getRows()) area.remove(0);
				user=((Message)message.getObject()).getTo();
				if(user==actualUser) area.add(((Message)message.getObject()).getText());
				Thread.sleep(100);
			} catch (JMSException e) {
				throw new RuntimeException("Error on JMS message processing");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
