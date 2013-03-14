package edu.eci.arsw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.activemq.ActiveMQConnectionFactory;

import edu.eci.arsw.gestorUsuarios.NewUser;
import edu.eci.arsw.server.DireccionServidor;

public class LoginUser extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField user;
	private JButton login;

	public LoginUser(){
		super();
		user= new JTextField();
		login = new JButton("Login");
		setSize(new Dimension(300,100));
		add(user,BorderLayout.CENTER);
		add(login,BorderLayout.SOUTH);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MainWindow(user.getText()).setVisible(true);
				setVisible(false);
				sendUser();

			}
		});
	}

	protected void sendUser() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://"+DireccionServidor.direccionServidor+":61616");
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			 
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			 
			Destination destination = session.createQueue("ni");
			 
			MessageProducer producer = session.createProducer(destination);
			ObjectMessage objectMessage = session.createObjectMessage(new NewUser(user.getText())); 
			producer.send(objectMessage);
			
			producer.close();
			session.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LoginUser().setVisible(true);

	}

}
