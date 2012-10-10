package br.com.senac.ccs.thinkfast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.AsyncContext;

public class ThinkFastGame {

    private final ConcurrentHashMap<String, Participant> participants;
    private final Lock lock;
    private final List<Question> questions;
    private Question currentQuestion;

    public ThinkFastGame() {
        this.participants = new ConcurrentHashMap<String, Participant>();
        this.questions = new ArrayList<Question>();
        this.lock = new ReentrantLock();
    }

    public Result play( String id, String name, Screen screen ) throws IOException {
        /*criar uma instancia de participantes
         * 
         */
        
        lock.lock();
        Result result = null;
        try {
            Participant participant = new Participant( id, name, screen );
            participants.put( id, participant );
            result = new Result( currentQuestion, "Welcome $s");            
        }
        finally {
            lock.unlock();
        }
        return result;
    }

    public void bind( String id, Screen screen ) {
        /* pegar o participante do id e setar*/
        Participant participant = participants.get(id);
        participant.setScreen( screen );
    }

    public void answer( String id, String answer ) throws IOException {
        lock.lock();
        try {
            
            if ( this.currentQuestion.getAnswer().equals( answer )) {
                Question question = currentQuestion;
                Collections.shuffle( questions );
                currentQuestion = questions.get( 0 );
                questions.add( question );
                Participant winner = participants.remove( id );
                winner.notify( new Result( currentQuestion, "Congratulations"));
                for ( Participant participant : participants.values()) {
                    participant.notify( new Result( currentQuestion, String.format( "O participante %s respondeu " + 
                                                                                    "mais rapido, tente novamente", winner.getName())));
                }
            }
            else {
                Participant participant = participants.get( id );
                participant.notify( new Result( "Nope!! :("));
            }
        }
        finally {
            lock.unlock();
        }
                /*avisa todo mundo quando respondeu certo, ou avisa o cara quando ele responder errado*/
    }

    public void init() {
        this.questions.add( new Question( "Qual a capital dos EUA?", Arrays.asList( new String[]{ "Washington DC", "California", "Nevada" } ), "Washington DC" ) );
        this.questions.add( new Question( "Qual a capital da Russia?", Arrays.asList( new String[]{ "Berlin", "Paris", "Moscou" } ), "Moscou" ) );
        this.questions.add( new Question( "Qual a capital do Brasil?", Arrays.asList( new String[]{ "Berlin", "Brasilia", "Moscou" } ), "Brasilia" ) );

        this.currentQuestion = questions.get( 0 );
        
    }
}