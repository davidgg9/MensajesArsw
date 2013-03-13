package edu.eci.arsw.client;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

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
				
				mw =new MainWindow(loginUser);
				mw.setVisible(true);
				setVisible(false);
			}

		});
	}

}
