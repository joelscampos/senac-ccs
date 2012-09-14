package br.com.senac.ccs.thinkfast;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet( urlPatterns = { "/thinkfast" },
             asyncSupported = true )
public class ThinkFastController extends HttpServlet {

    @Override
    protected void doGet( HttpServletRequest req,
                          HttpServletResponse resp )
            throws ServletException, IOException {
        super.doGet( req, resp );
    }
    
    public class Question {
        private String description;
        private java.util.List<String> answers;
        private String correctAnswer;
        
        public Question( String description,
                         java.util.List<String> answers,
                         String correctAnswer ) {
            this.description = description;
            this.answers = answers;
            this.correctAnswer = correctAnswer;
        }
    }
}