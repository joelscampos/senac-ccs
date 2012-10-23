/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "MESSAGES")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    @OneToOne(cascade = CascadeType.ALL)
    private Participant participant;
    private Date timestamp;

    private Message () {}

    public Message(Participant participant, String text) {
        this.text = text;
        this.timestamp = new Date();
        this.participant = participant;
    }

    private Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Participant getUser() {
        return participant;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            final Message other = (Message) obj;
            return Objects.equal(getId(), other.getId());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("user", participant)
                .add("timestamp", timestamp)
                .add("text", text)
                .toString();
    }
}
