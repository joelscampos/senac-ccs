package br.com.senac.ccs.chat;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.String;
import java.lang.RuntimeException;

@WebServlet("/ChatServlet")
public class Mensagens extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();


        final User user = (User) req.getSession(false).getAttribute("user");
        user.setResponse(res);
        user.setRequest(req);

        // envia a mensagem de entrada  
        ChatUser.sendMessage(
                new GenericMessage(
                user,
                "                                               "
                + "                                               "
                + "                                               "
                + "                                               "
                + "entrou na sala: "
                + new SimpleDateFormat("hh:mm:ss").format(new Date())
                + "                                               "));
        out.flush();

        // dorme eternamente, ate uma excecao ser jogada pois o usuario saiu da sala  
        while (user.isConnected()) {
            try {
                // espera uma nova mensagem e a envia
                Message msg = user.getNewMessage();

                // checa a sessao  
                user.checkSession();

                // se for uma mensagem, mostra ela, se nao, verifica a conexao  
                if (msg != null) {
                    user.showMessage(msg);
                } else {
                    user.showMessage(null);
                }

            } /*
             * catch (java.net.SocketException e) {
             * user.addLog("socketexception:" + exceptionToString(e));
             * user.disconnect();  
		    }
             */ catch (Exception e) {
                ChatUser.sendMessage(
                        new GenericMessage(user, exceptionToString(e)));
                out.println("exception classe mensagens");

                //user.addLog("deu exception, desconectar: " + e.toString());  
                user.disconnect();
            }

        }

        // tira o usuario da lista e tira sua sessao  
        ChatUser.removeUser(user);
        out.println(
                new SimpleDateFormat("hh:mm").format(new Date())
                + "Devido a inatividade sessão foi desconectada.<br>Faça novo login.");


        // invalida a sessao  
        try {
            req.getSession().invalidate();
//		    session.invalidate();  
        } catch (IllegalStateException e) {
            // a sessao ja estava invalidada  
        }
    }

    private String exceptionToString(Exception e) {
        // TODO Auto-generated method stub
        return null;
    }
}
