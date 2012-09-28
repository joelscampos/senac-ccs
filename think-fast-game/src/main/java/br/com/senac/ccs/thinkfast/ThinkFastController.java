package br.com.senac.ccs.thinkfast;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(urlPatterns = { "/thinkfast" },
            asyncSupported = true, loadOnStartup = 1)
public class ThinkFastController extends HttpServlet {
    
    private ThinkFastGame game;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.game = new ThinkFastGame();
        this.game.init();
    }
    
    
    
    @Override
    public void doGet( HttpServletRequest req,
                          HttpServletResponse resp )
            throws ServletException, IOException {
        
        String action = req.getParameter( "action" );
        String id = req.getSession().getId();
        if ( "play".equals( action )) {
            String name = req.getParameter( "name" );
            AsyncContext async = req.startAsync();
            game.play( id, name, async );
        }
        else if ( "answer".equals( action )) {
            String answer = req.getParameter( "answer" );
            game.answer( id, answer );

        }
        else if ( "bind".equals( action )) {
            AsyncContext async = req.startAsync();
            game.bind( id, async );            
        }
        
    }

}