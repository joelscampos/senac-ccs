package br.com.senac.ccs.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    
    //@Autowired
    public ChatUser() {
        this.participants = new ConcurrentHashMap<String, Participant>();
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
            result = new Result(String.format("Welcome %s!", participant.getName()), new ArrayList<Participant>(participants.values()));
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
    
    
    public Result sendChatMessage( String id, String message ) {
        lock.lock();
        Result result = null;
        try {
            final List<Participant> all = new ArrayList<Participant>( participants.values());
            Participant participantCurrent = participants.remove(id);
            participantCurrent.notify(new Result("Parabeeeens!! :)", all) );
            for (Participant participant : participants.values()) {
                    participant.notify(new Result(String.format ("O participante %s respondeu mais rapido, tente novamente", participantCurrent.getName()), all));
            }
            participants.put(id, participantCurrent);
            
            //messageRepository.save(new Message(currentParticipant, message));
        } finally {
            lock.unlock();
        }
        return result;
                
    }
}
