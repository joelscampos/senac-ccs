package br.com.senac.ccs.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatUser {

    private final ConcurrentHashMap<String, Participant> participants;
    private List<Participant> Participants;
    private final Lock lock;
    private final List<Question> questions;
    private Question currentQuestion;
    @Autowired
    private QuestionRepository questionRepository;
    
    public ChatUser() {
        this.participants = new ConcurrentHashMap<String, Participant>();
        this.questions = new ArrayList<Question>();
        this.lock = new ReentrantLock();
        this.Participants = new ArrayList<Participant>();
    }

    public Result play( String id, String name, Screen screen ) {
            lock.lock();
            Result result = null;
       try {
            Participant participant = new Participant (id, name, screen);

            Participants.add(participant);
            participants.put(id, participant);
            result = new Result(currentQuestion, String.format("Welcome %s!", participant.getName()), new ArrayList<Participant>(participants.values()));
        }
        finally {
            lock.unlock();
       }
       return result;
    }


    public void bind( String id, Screen screen ) {
        Participant participant = participants.get(id);
        participant.setScreen(screen);
    }

    public Result answer( String id, Answer answer ) {
        lock.lock();        
        Result result = null;
        try {
            if (this.currentQuestion.getAnswer().equals( answer )) 
            {
                Question question = currentQuestion;
                questions.remove (question);
                Collections.shuffle(questions);
                currentQuestion = questions.get(0);
                questions.add(question);
                final List<Participant> all = new ArrayList<Participant>( participants.values());
                Participant winner = participants.remove(id);
                winner.incrementScore();
                winner.notify(new Result(currentQuestion, "Parabeeeens!! :)", all) );
                for (Participant participant : participants.values()) {
                    participant.notify(new Result(currentQuestion, String.format ("O participante %s respondeu mais rapido, tente novamente", winner.getName()), all));
                }
                participants.put(id, winner);

            } else {
                Participant participant = participants.get(id);
                participant.reduceScore();
                result = new Result("Incorreto!! :(", Participants);
            }
        }
        finally {
            lock.unlock();
        }
        return result;
    }
    @PostConstruct
    public void init() {
        final Answer correctAnswer1 = new Answer("Washington DC");
        questionRepository.save( new Question( "Qual a capital dos EUA?", Arrays.asList( new Answer[]{ 
            correctAnswer1, 
            new Answer("California"), 
            new Answer("Nevada") } ), correctAnswer1) );
        final Answer correctAnswer2 = new Answer("Moscou");
        questionRepository.save( new Question( "Qual a capital da Russia?", Arrays.asList( new Answer[]{ 
            new Answer("Berlin"), 
            new Answer("Paris"), correctAnswer2} ), correctAnswer2) );
        final Answer correctAnswer3 = new Answer("42");
        questionRepository.save( new Question( "Qual a resposta da vida, do universo e tudo mais?", Arrays.asList( new Answer[]{ 
            new Answer("32"), correctAnswer3, 
            new Answer("52") } ), correctAnswer3) );
        final Answer correctAnswer4 = new Answer("Windows");
        questionRepository.save( new Question( "Entre as opcoes, qual nao e um sistema operacional?", Arrays.asList( new Answer[]{ 
            correctAnswer4, 
            new Answer("MAC OS"), 
            new Answer("Linux") } ), correctAnswer4) );
        final Answer correctAnswer5 = new Answer("Baixar o Firefox");
        questionRepository.save( new Question( "Qual e a funcao do Internet Explorer?", Arrays.asList( new Answer[]{ 
            new Answer("Navegar na internet"), 
            new Answer("Abrir e-mails"), correctAnswer5} ), correctAnswer5) );
        this.questions.addAll(questionRepository.findAll());
        this.currentQuestion = questions.get( 0 );
    }
}
