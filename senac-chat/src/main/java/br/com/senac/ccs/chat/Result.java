package br.com.senac.ccs.chat;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private String message;
    private List<Participant> participants;

    public Result() {
    }

    public Result( String message ) {
        this.message = message;
    }
    
    public Result( String message, List<Participant> participants ) {
        this.message = message;
        this.participants = participants;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants( List<Participant> participants ) {
        this.participants = participants;
    }
    
}