package br.com.senac.ccs.chat;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;


@Controller
@RequestMapping(value="/chat/*", produces="text/plain;charset=UTF-8") //quando o browser requisitar /chat/algumacoisa, o 
public class ChatController {
    
    @Autowired
    private ChatUser chatUser;

    @RequestMapping(value = "entrar", method = RequestMethod.GET)
    public @ResponseBody String entrar(@RequestParam String participantName, HttpSession session) {
        return chatUser.play(session.getId(), participantName);        
    }
    @RequestMapping(value="bind", method = RequestMethod.GET )
    public @ResponseBody DeferredResult<String> bind( HttpSession session ) {  //o DeferredResult diz para o spring que é para fazer um start async
        SpringMvcDeferredResultListener<String> listener = new SpringMvcDeferredResultListener<String>();
        chatUser.bind(session.getId(), listener);
        return listener.getDeferredResult();
    }
    @RequestMapping(value = "sendChatMessage", method = RequestMethod.GET)
    public @ResponseBody String sendChatMessage(@RequestParam String message, HttpSession session) {
        return chatUser.sendChatMessage( session.getId(), message );
    }
    
}