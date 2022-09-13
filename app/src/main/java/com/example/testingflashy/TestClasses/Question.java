package com.example.testingflashy.TestClasses;

import java.security.cert.PKIXRevocationChecker;

public class Question {
    private String question;
    private  String answer;

    public Question(String _question, String _answer){
        question = _question;
        answer = _answer;
    }

    public String getQuestion(){return question;}
    public String getAnswer(){return answer;}
}
