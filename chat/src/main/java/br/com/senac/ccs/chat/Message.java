package br.com.senac.ccs.chat;

public interface Message {

    public User getFrom();

    public User getTo();

    public String getHTMLCode();
}
