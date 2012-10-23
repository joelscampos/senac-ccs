package br.com.senac.ccs.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
import java.util.Hashtable;

public class ChatUser implements User {

    private String name = null;
    private HttpServletResponse res = null;
    private HttpServletRequest request = null;
    private boolean connected = true;

    public ChatUser(String name) {
        this.name = name;
        if (ChatUser.getByName(getName()) != null) {
            throw new IllegalArgumentException("Invalid username");
        } else {
            ChatUser.addUser(this);
        }
    }
    // getName

    public String getName() {
        return name;
    }
    // getResponse

    public HttpServletResponse getResponse() {
        return res;
    }
    // setResponse

    public void setResponse(HttpServletResponse res) {
        this.res = res;
    }
    // getRequest

    public HttpServletRequest getRequest() {
        return request;
    }
    // setRequest

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    // isConnected

    public boolean isConnected() {
        return connected;
    }
    // disconnect

    public void disconnect() {
        connected = false;
    }
    // checkSession

    public void checkSession() {
        try {
            if (getRequest().getSession() == null
                    || getRequest().getSession().getAttribute("user") == null) {
                this.disconnect();
            }
        } catch (IllegalStateException isex) {
            //this.disconnect();  //tem algum problema aqui, pq ta chamando esse metodo...
        }
    }
    // message
    private ArrayList messages = new ArrayList();

    public void addMessage(Message msg) {
        messages.add(msg);
        synchronized (messages) {
            // sinaliza para quem está esperando
            messages.notifyAll();
        }
    }
    // getNewMessage

    public synchronized Message getNewMessage() {
        if (messages.isEmpty()) {
            //iremos inserir o codigo para esperar aqui
            try {
                synchronized (messages) {
                    messages.wait(1);
                }
            } catch (InterruptedException e) {
                // o que fazer se der um exception?
                // desconecta
                this.disconnect();
                // ele desconectou
                return (null);
            }
            // se deu timeout
            if (messages.isEmpty()) {
                return (null);
            }
        }
        return (Message) (messages.remove(0));
    }
    /*
     * Já que não temos uma interface e classe chamada Room que serviria para
     * controlar os usuários dentro de uma sala, simulamos uma, sem usar
     * orientação a objetos, com métodos static na classe ChatUser:
     */
    // users
    private static Hashtable users = new Hashtable();
    // addUser

    public static void addUser(User user) {
        synchronized (users) {
            users.put(user.getName(), user);
        }
    }
    // removeUser

    public static void removeUser(User user) throws IOException {
        synchronized (users) {
            // se não existir
            if (!users.containsValue(user)) {
                return;
            }
            // remove
            users.remove(user.getName());
            // desconecta
            user.disconnect();
            // notifica da saída
            synchronized (user) {
                user.notifyAll();
            }
        }
    }
    // getByName

    protected static User getByName(String name) {
        return (User) users.get(name);
    }
    // getUsers

    public static Hashtable getUsers() {
        synchronized (users) {
            return (new Hashtable(users));
        }
    }
    // sendMessage

    public static void sendMessage(Message msg) throws IOException {
        // se a mensagem tem um destinatário específico
        if (msg != null && msg.getTo() != null) {
            msg.getTo().addMessage(msg);
            msg.getFrom().addMessage(msg);
            return;
        }

        // caso contrário envia para todos
        Map usersList = getUsers();
        Iterator i = usersList.keySet().iterator();
        while (i.hasNext()) {
            String username = (String) i.next();
            User user = ((User) usersList.get(username));
            try {
                user.addMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
                removeUser(user);
            }
        }
    }

    // showMessage
    public void showMessage(Message msg) throws IOException {
        if (msg != null) {
            PrintWriter out = res.getWriter();
            out.println(msg.getHTMLCode());
            out.flush();

        }

        return;
    }
}
