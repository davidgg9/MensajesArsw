package edu.eci.arsw;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
		login = new JButton();
		setSize(new Dimension(300,100));


		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MainWindow(user.getText()).setVisible(true);
				setVisible(false);
				sendMessage();

			}
		});
	}

	protected void sendMessage() {


	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LoginUser().setVisible(true);

	}

}
