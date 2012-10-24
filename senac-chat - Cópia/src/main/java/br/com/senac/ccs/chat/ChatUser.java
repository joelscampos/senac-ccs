package br.com.senac.ccs.chat;

import static com.google.common.collect.Sets.newHashSet;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatUser {

    private final ConcurrentHashMap<String, Participant> participants;
    private final Lock lock;
    //private final MessageRepository messageRepository;
    
    
    //@Autowired
    public ChatUser (/*MessageRepository repository*/) {
        //this.messageRepository = repository;
        this.participants = new ConcurrentHashMap<String, Participant>();
        this.lock = new ReentrantLock();
    }
    
    
    public String play( String id, String name ) {
        lock.lock();        
        try {
            Participant currentParticipant = new Participant (id, name);

            participants.put(id, currentParticipant);            
        }
        finally {
            lock.unlock();
       }
       return String.format("Bem vindo %s", name);
    }


    public void bind(String id, Listener<String> listener) {
        if (participants.contains(id)) {
            participants.get(id).setChatMessageListener(listener);
        }
    }

    public String sendChatMessage( String id, String message ) {
        lock.lock();
        try {
            Participant currentParticipant = participants.get(id);
            Set<Participant> otherUsers = newHashSet(participants.values());
            otherUsers.remove(currentParticipant);
            String otherUsersMessage = MessageFormat.format("{0} disse: {1}", currentParticipant.getName(), message);
            for (Participant participant : otherUsers) {
                participant.sendChatMessage(otherUsersMessage);
            }
            //messageRepository.save(new Message(currentParticipant, message));
        } finally {
            lock.unlock();
        }
        return MessageFormat.format("Voce disse: {0}", message);
                
    }
}
