package edu.eci.arsw.gestorusuario;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.List;
import javax.swing.JFrame;

public class GestorUsuario extends JFrame{
	private List listaUsuario;
	private ReceivedUsersAddThread newUsersThread;
	private ReceivedUsersDeleteThread deleteUsersThread;
	public GestorUsuario(){
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GestorUsuario().setVisible(true);

	}

}
