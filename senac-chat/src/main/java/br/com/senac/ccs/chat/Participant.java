package br.com.senac.ccs.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.constraints.NotNull;

public class Participant {

    private String id;
    private String name;
    private int score;
    private Screen screen;

    public Participant() {
        this.score = 0;
    }

    public Participant( String id, String name ) {
        this();
        this.id = id;
        this.name = name;
    }

    public Participant( String id, String name, Screen screen ) {
        this( id, name );
        this.screen = screen;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //@NotNull /*necessario colocar a api lá no pom*/
    public void setName(String name) {
        this.name = name;
    }
    
    public int getScore() {
        return score;
    }
    
    public void incrementScore() {
        this.score++;
    }

    public void setScreen( Screen screen ) {
        this.screen = screen;
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    
    public void notify( Result result ) {
        if ( screen != null ) {
            screen.show( result );
        }
    }

    public void reduceScore() {
        this.score--;
    }
}
