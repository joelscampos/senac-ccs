/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;

/**
 *
 * @author yara
 */
public class GenericMessage implements Message {
    
	private User from = null;
	private User to = null;
	private String text = null;
	//esse é um construtor-tem o mesmo nome da classe
	public GenericMessage(User from,String texto) {
		this.from = from;
		this.text = texto;
	}
	//segundo construtor
	public GenericMessage(User from,User to,String texto) {
		this(from,texto);
		this.to   = to;
	}
	public User getFrom() {
		return from;
	}
	public User getTo() {
		return to;
	}
	public String getHTMLCode() {
		return(" - " + getFrom().getName() + (getTo()!=null? " fala para " + getTo().getName() : "") + ": " + text + "<br>\n");
	}

}