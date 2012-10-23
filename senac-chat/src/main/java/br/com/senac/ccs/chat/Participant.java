package br.com.senac.ccs.chat;

import com.google.common.base.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "PARTICIPANTS")
public class Participant {

    @Id
    private String id;
    private String name;

    
    private transient Listener<String> statusMessageListener;
    private transient Listener<String> chatMessageListener;

    private Participant() {}

    public Participant( String id, String name ) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void sendChatMessage(String message) {
        if (chatMessageListener != null) {
            chatMessageListener.onEvent(message);
        }
    }

    public Listener<String> getChatMessageListener() {
        return chatMessageListener;
    }

    public void setChatMessageListener(Listener<String> chatMessageListener) {
        this.chatMessageListener = chatMessageListener;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Participant) {
            final Participant other = (Participant) obj;
            return Objects.equal(getId(), other.getId());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }

}
