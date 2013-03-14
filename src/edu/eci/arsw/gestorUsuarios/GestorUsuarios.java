package edu.eci.arsw.gestorUsuarios;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.List;

import javax.swing.JFrame;

public class GestorUsuarios extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List listaUsuario;
	private ReceivedUsersAddThread newUsersThread;
	private ReceivedUsersDeleteThread deleteUsersThread;
	public GestorUsuarios(){
		super("Lista de Usuarios");
		listaUsuario = new List(10, false);
		setSize(200, 500);
		add(listaUsuario,BorderLayout.CENTER);
		add(new Button(), BorderLayout.SOUTH);
		newUsersThread = new ReceivedUsersAddThread(listaUsuario);
		deleteUsersThread = new ReceivedUsersDeleteThread(listaUsuario);
		newUsersThread.start();
		deleteUsersThread.start();
	}
	
	public static void main(String[] args) {

	}

}
