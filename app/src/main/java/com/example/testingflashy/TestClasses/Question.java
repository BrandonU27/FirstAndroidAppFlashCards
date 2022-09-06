package com.example.testingflashy.TestClasses;

public class Question {
    private String question;
    private Options op1;
    private Options op2;
    private Options op3;
    private Options op4;

    Question(String _question, Options _op1, Options _op2, Options _op3, Options _op4){
        question = _question;
        op1 = _op1;
        op2 = _op2;
        op3 = _op3;
        op4 = _op4;
    }
}
