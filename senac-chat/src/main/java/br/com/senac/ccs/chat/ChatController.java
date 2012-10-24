package br.com.senac.ccs.chat;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;


@Controller
@RequestMapping(value="/chat/*", produces={"application/json"}) //quando o browser requisitar /thinkfast/algumacoisa, o 
public class ChatController {
    
    @Autowired
    private ChatUser chatUser;

    @RequestMapping(value="entrar", method = RequestMethod.GET )
    public @ResponseBody Result entrar(/*@Valid*/ Participant participant, HttpSession session) {
        String id = session.getId();
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        return chatUser.play(id, participant.getName(), screen);
        
    }
    
    @RequestMapping(value="bind", method = RequestMethod.GET )
    public @ResponseBody DeferredResult<Result> bind( HttpSession session ) {  //o DeferredResult diz para o spring que Ã© para fazer um start async
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        chatUser.bind(session.getId(), screen);
        return deferredResult;
    }
    
    @RequestMapping(value = "sendChatMessage", method = RequestMethod.GET)
    public @ResponseBody String sendChatMessage(@RequestBody String message, HttpSession session) {
        return chatUser.sendChatMessage( session.getId(), message ).getMessage();
    }
    
}