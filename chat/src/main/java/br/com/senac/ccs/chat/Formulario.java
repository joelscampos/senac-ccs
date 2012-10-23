package br.com.senac.ccs.chat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.String;
import java.lang.RuntimeException;

@WebServlet("/FormServlet")
public class Formulario extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        PrintWriter out = res.getWriter();


        final User user = (User) req.getSession(false).getAttribute("user");

        if (req.getParameter("message") != null) {
            // se mensagem nao for vazia  
            if (!req.getParameter("message").trim().equals("")) {
                // envia a mensagem
                Message msg = new GenericMessage(user, ChatUser.getByName(req.getParameter("para")), req.getParameter("message"));
                // envia a mensagem  
                ChatUser.sendMessage(msg);
            }
        }
        // mostra o formulario novamente  
        doGet(req, res);

    }

    //  retornar o formulário para novas mensagens, primeiro listando os usuários
    public void doGet(HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        PrintWriter out = res.getWriter();


        // pega o codigo de listagem de usuarios  
        String codigoPara = "<option selected value=>Todos</option>";
        Iterator i = new HashMap(ChatUser.getUsers()).keySet().iterator();
        while (i.hasNext()) {
            String value = (String) i.next();
            codigoPara += "<option value="
                    + value
                    + ">"
                    + value
                    + "</option>";
        }

        // mostra o formulario  
        res.getWriter().print(
                "<html><form name=chat action="
                + "FormServlet" //+ this.getClass().getName()  
                + " method=post>\n"
                + "<input type=hidden size=60 name=show value=form>\n"
                + "\n\t<input name=message> para <select name=para>"
                + codigoPara
                + "</select>"
                + "\n\t<input type=submit value='Enviar'>\n"
                + "</form>\n"
                + "</html>");


    }
}
