package edu.eci.arsw.gestorusuario;

import java.awt.BorderLayout;
import java.awt.List;
import javax.swing.JFrame;

public class GestorUsuario extends JFrame{
	private List listaUsuario;
	public GestorUsuario(){
		super("Lista de Usuarios");
		listaUsuario = new List(10, false);
		setSize(200, 500);
		add(listaUsuario,BorderLayout.CENTER);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GestorUsuario().setVisible(true);

	}

}
