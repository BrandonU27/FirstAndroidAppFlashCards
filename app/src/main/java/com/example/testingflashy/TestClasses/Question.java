package com.example.testingflashy.TestClasses;

import java.io.Serializable;

public class Question implements Serializable {
    private final String question;
    private final String answer;

    public Question(String _question, String _answer){
        question = _question;
        answer = _answer;
    }

    public String getQuestion(){return question;}
    public String getAnswer(){return answer;}
}
