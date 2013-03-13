package edu.eci.arsw.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.List;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.activemq.ActiveMQConnectionFactory;


public class MainWindow extends JFrame {
	protected String direccionServer ="172.16.1.248";  
	private List receivedMessages;
	private JTextArea messageToSend;
	private JButton sendButton, endButton;
	private List destiny;
	private JTextField myname;
	ReceivedMessagesUpdateThread windowUpdateThread;
	RecivedUserUpdate userUpdate;
	public MainWindow(TextArea loginUser) throws HeadlessException {
		super("MESSENCHAT -"+ loginUser.getText());
		JPanel botones=new JPanel();
		destiny=new List(10,false);
		myname=new JTextField(loginUser.getText());
		JPanel addrPanel=new JPanel();
		addrPanel.setLayout(new FlowLayout());
		addrPanel.add(destiny);
		addrPanel.add(myname);
		receivedMessages=new List(4,false);
		messageToSend=new JTextArea();
		sendButton=new JButton("SEND");
		endButton=new JButton("Log Out");
		botones.add(sendButton);
		botones.add(endButton);
		this.setLayout(new BorderLayout());
		JScrollPane topJsp=new JScrollPane();
		topJsp.setSize(topJsp.getWidth(),300);
		JScrollPane botJsp=new JScrollPane();	
		//CREAR Y ASOCIAR EL HILO (SUSCRIPTOR) DE LOS MENSAJES CON LA VENTANA DE TEXTO
		windowUpdateThread=new ReceivedMessagesUpdateThread(receivedMessages, loginUser.getText());
		userUpdate = new RecivedUserUpdate(destiny);
		windowUpdateThread.start();
		userUpdate.start();
		topJsp.getViewport().add(receivedMessages);
		botJsp.getViewport().add(messageToSend);
		this.getContentPane().add(topJsp,BorderLayout.NORTH);
		this.getContentPane().add(botJsp,BorderLayout.CENTER);
		this.getContentPane().add(botones,BorderLayout.SOUTH);
		this.getContentPane().add(addrPanel,BorderLayout.EAST);
		this.setSize(500,500);
		sendButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg=messageToSend.getText();
						Message m=new Message(myname.getText(),destiny.getSelectedItem(),msg);
						//enviar como evento el objeto m
						ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://" +direccionServer+":61616");
						Connection connection;
						try {
							connection = connectionFactory.createConnection();
							connection.start();
							Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
							Destination destination = session.createTopic("MENSAJES");
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
				);
		endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Message m=new Message(myname.getText(),destiny.getSelectedItem(),myname.getText());
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://192.168.0.6:61616");
				Connection connection;
				try {
					connection = connectionFactory.createConnection();
					connection.start();
					Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Destination destination = session.createQueue("eliminaUsuario");
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
				MainWindow.this.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		new LoginVentana();
		//		new MainWindow().setVisible(true);
	}
}
