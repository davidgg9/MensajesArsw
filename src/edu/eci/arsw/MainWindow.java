package edu.eci.arsw;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.Connection;
import javax.jms.Destination;
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

import edu.eci.arsw.server.DireccionServidor;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private List receivedMessages;
	private JTextArea messageToSend;
	private JButton sendButton;
	private JTextField myname;
	private List destiny;
	
	ReceivedMessagesUpdateThread windowUpdateThread;

	public MainWindow(String user) throws HeadlessException {
		super("MESSENCHAT- "+user);
		destiny=new List();
		usuariosConectados();
		myname=new JTextField(user);
		JPanel addrPanel=new JPanel();
		addrPanel.setLayout(new FlowLayout());
		addrPanel.add(destiny);
		addrPanel.add(myname);
		
		receivedMessages=new List(20,false);
		messageToSend=new JTextArea();
		sendButton=new JButton("SEND");
		this.setLayout(new BorderLayout());
		
		JScrollPane topJsp=new JScrollPane();
		topJsp.setSize(topJsp.getWidth(),300);
		JScrollPane botJsp=new JScrollPane();
		
		//CREAR Y ASOCIAR EL HILO (SUSCRIPTOR) DE LOS MENSAJES CON LA VENTANA DE TEXTO
		windowUpdateThread=new ReceivedMessagesUpdateThread(receivedMessages, user);
		windowUpdateThread.start();
		
		topJsp.getViewport().add(receivedMessages);
		botJsp.getViewport().add(messageToSend);
		this.getContentPane().add(topJsp,BorderLayout.NORTH);
		this.getContentPane().add(botJsp,BorderLayout.CENTER);
		this.getContentPane().add(sendButton,BorderLayout.SOUTH);
		this.getContentPane().add(addrPanel,BorderLayout.EAST);
		this.setSize(500,500);
		
		sendButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg=messageToSend.getText();
						Message m=new Message(myname.getText(),destiny.getSelectedItem(),msg);
						sendMessage(m);
					}
				}
		);
	}
	
	private void usuariosConectados() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://"+DireccionServidor.direccionServidor+":61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		 
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("MENSAJE");
		
		MessageProducer producer = session.createProducer(destination);
		ObjectMessage objectMessage = session.createObjectMessage(m); 
		producer.send(objectMessage);
		
		ObjectMessage message = (ObjectMessage)consumer.receive();	
		if(area.getItemCount()==area.getRows()) area.remove(0);
		user=((Message)message.getObject()).getTo();
		if(user==actualUser) area.add(((Message)message.getObject()).getText());
		Thread.sleep(100);
	}

	protected void sendMessage(Message m) {
		try{
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://"+DireccionServidor.direccionServidor+":61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			 
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("MENSAJE");
			
			MessageProducer producer = session.createProducer(destination);
			ObjectMessage objectMessage = session.createObjectMessage(m); 
			producer.send(objectMessage);
			
			producer.close();
			session.close();
			connection.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
