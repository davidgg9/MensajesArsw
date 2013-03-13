package edu.eci.arsw.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	private List receivedMessages;
	private JTextArea messageToSend;
	private JButton sendButton;
	private JTextField destiny, myname;
	ReceivedMessagesUpdateThread windowUpdateThread;
	public MainWindow(TextArea loginUser) throws HeadlessException {
		super("MESSENCHAT -"+ loginUser.getText());
		destiny=new JTextField("DESTINY");
		myname=new JTextField(loginUser.getText());
		JPanel addrPanel=new JPanel();
		addrPanel.setLayout(new FlowLayout());
		addrPanel.add(destiny);
		addrPanel.add(myname);
		receivedMessages=new List(4,false);
		messageToSend=new JTextArea();
		sendButton=new JButton("SEND");
		this.setLayout(new BorderLayout());
		JScrollPane topJsp=new JScrollPane();
		topJsp.setSize(topJsp.getWidth(),300);
		JScrollPane botJsp=new JScrollPane();	
		//CREAR Y ASOCIAR EL HILO (SUSCRIPTOR) DE LOS MENSAJES CON LA VENTANA DE TEXTO
		windowUpdateThread=new ReceivedMessagesUpdateThread(receivedMessages);
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
						Message m=new Message(myname.getText(),destiny.getText(),msg);
						System.out.println(m.getTo() + m.getFrom() + m.getText());
						//enviar como evento el objeto m
						ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://192.168.0.6:61616");
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
	}
	
	public static void main(String[] args) {
		new LoginVentana();
//		new MainWindow().setVisible(true);
	}
}
