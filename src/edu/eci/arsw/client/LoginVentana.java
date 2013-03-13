package edu.eci.arsw.client;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.activemq.ActiveMQConnectionFactory;

public class LoginVentana extends JFrame {
	private JButton sendButton;
	private Panel panel;
	private TextArea loginUser;
	private MainWindow mw;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginVentana(){
		panel= new Panel();
		sendButton = new JButton("Login");
		loginUser = new TextArea();
		add(panel,BorderLayout.CENTER);
		panel.add(sendButton, BorderLayout.NORTH);
		panel.add(loginUser,BorderLayout.SOUTH);
		setVisible(true);
		setSize(500,200);
		
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendNewUser();
				mw =new MainWindow(loginUser);
				mw.setVisible(true);
				setVisible(false);
			}

		});
	}

	protected void sendNewUser() {
		Message m=new Message(loginUser.getText(),"gestor",loginUser.getText());
		//enviar como evento el objeto m
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://192.168.0.6:61616");
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("nuevoUsuario");
			MessageProducer producer = session.createProducer(destination);
			ObjectMessage message = session.createObjectMessage(m); 
			producer.send(message);
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
