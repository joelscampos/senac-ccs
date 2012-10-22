package br.com.senac.ccs.chat;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;


@Controller
@RequestMapping(value="/thinkfast/*", produces={"application/json"}) //quando o browser requisitar /thinkfast/algumacoisa, o 
public class ChatController {
    
    @Autowired
    private ChatUser game;

    @RequestMapping(value="play", method = RequestMethod.GET )
    public @ResponseBody Result play(/*@Valid*/ Participant participant, HttpSession session) {
        String id = session.getId();
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        return game.play(id, participant.getName(), screen);
        
    }
    @RequestMapping(value="bind", method = RequestMethod.GET )
    public @ResponseBody DeferredResult<Result> bind( HttpSession session ) {  //o DeferredResult diz para o spring que é para fazer um start async
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        game.bind(session.getId(), screen);
        return deferredResult;
    }
    @RequestMapping(value="answer", method = RequestMethod.POST, consumes={"application/json"} )
    public @ResponseBody Result answer( @RequestBody Answer answer, HttpSession session) {
        return game.answer( session.getId(), answer );
    }
    
}