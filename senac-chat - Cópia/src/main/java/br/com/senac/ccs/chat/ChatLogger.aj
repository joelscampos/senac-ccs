
public aspect ChatLogger {

    pointcut participantPlay(): execution( public void br.com.senac.ccs.chat.ChatUser.play( String, String, javax.servlet.AsyncContext ));

    after(): participantPlay() {
        System.out.println( String.format( "Participant entrou no chat" ));
    }
}
