package br.com.senac.ccs.thinkfast;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;


public class Question {

    private final String description;
    private final List<String> answers;
    private final String answer;

    public Question( final String description, final List<String> answers, final String answer ) {
        this.description = description;
        this.answers = answers;
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @JsonIgnore
    public String getAnswer() {
        return answer;
    }
}