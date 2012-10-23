package br.com.senac.ccs.chat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class Login extends HttpServlet {

    @Override
    public void service(HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = res.getWriter();

        // se ja existir uma sessao, anula ela  
        /*if (req.getSession() != null
                && req.getSession().getAttribute("user") != null) {
            req.getSession().invalidate();
        }*/

        // cria um usuario  
        try {
            User user = new ChatUser(req.getParameter("login"));
            // seta o usuario na session  
            req.getSession().setAttribute("user", user);
            // seta o tempo limite da session (10 minutos)  
            req.getSession().setMaxInactiveInterval(5 * 60);
        } catch (IllegalArgumentException iaex) {
            // se ja existe um usuario, mostra a pagina de login denovo  
            res.getWriter().println(
                    "<html>Ja existe um usuario com esse login:<br><form action=LoginServlet><input name=login><input type=submit value=Login></form></html>");
            return;
        }
        res.setContentType("text/html");
        res.getWriter().println(
                "<html>"
                + "<frameset rows=*,100>"
                + "<frame name='text' src='ChatServlet'>"
                + "<frame name='form' src='FormServlet'>"
                + "</frameset></html>");

    }
}
