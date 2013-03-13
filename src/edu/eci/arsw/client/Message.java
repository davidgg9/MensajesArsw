package edu.eci.arsw.client;

import java.io.Serializable;


//NO MODIFICAR ESTA CLASE!!! (PARA EVITAR PROBLEMAS DE COMPATIBILIDAD CON OTROS CLIENTES)
public class Message implements Serializable{

	public Message(String from, String to, String text) {
		super();
		this.from = from;
		this.to = to;
		this.text = text;
	}
	public Message(String from2, String to2, String[] items) {
		super();
		this.from = from2;
		this.to = to2;
		for (int i = 0; i < items.length; i++) {
			text+=items[i]+",";
		}
		text=text.substring(text.length()-1);
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private String from;
	private String to;
	private String text;
	
}
