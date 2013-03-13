package edu.eci.arsw.client;

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
	MessageConsumer consumer=null;
	String actualUser;
	Session session;
	Connection connection;
	private List area;
	
	public ReceivedMessagesUpdateThread(List receivedMessages, String user) {
		super();
		actualUser=user;
		this.area = receivedMessages;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://192.168.0.6:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("MENSAJES");
			consumer = session.createConsumer(destination);	
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

	public void run(){
		while (true){
			try {
				ObjectMessage message = (ObjectMessage)consumer.receive();	
				if(area.getItemCount()==10) area.remove(0);
				System.out.println(((Message)message.getObject()).getTo()+ " . "+ actualUser);
				if(((Message)message.getObject()).getTo()==actualUser || ((Message)message.getObject()).getTo()=="multicast") area.add(((Message)message.getObject()).getText());
				Thread.sleep(100);
			} catch (JMSException e) {
				throw new RuntimeException("Error on JMS message processing");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
