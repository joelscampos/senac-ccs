package br.com.senac.ccs.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.constraints.NotNull;

//@Entity(name = "PARTICIPANTS")
public class Participant {

    private String id;
    private String name;
    private Screen screen;

    public Participant() {
        
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

    //@NotNull /*necessario colocar a api lÃ¡ no pom*/
    public void setName(String name) {
        this.name = name;
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
}
