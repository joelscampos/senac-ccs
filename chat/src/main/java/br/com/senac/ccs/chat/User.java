
package br.com.senac.ccs.chat;

import java.io.IOException;
import java.io.PrintWriter;
//Bibliotecas da classe Servlet
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface User {

    public String getName();

    boolean isConnected();

    public void disconnect();

    public void checkSession();

    public HttpServletResponse getResponse();

    public HttpServletRequest getRequest();

    public void setResponse(HttpServletResponse res);

    public void setRequest(HttpServletRequest req);

    public void addMessage(Message msg);

    public Message getNewMessage();

    public void showMessage(Message msg) throws IOException;
}
