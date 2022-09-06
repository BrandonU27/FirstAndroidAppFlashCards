package com.example.testingflashy.TestClasses;

public class Question {
    final String question;
    final Options op1;
    final Options op2;
    final Options op3;
    final Options op4;

    Question(String _question, Options _op1, Options _op2, Options _op3, Options _op4){
        question = _question;
        op1 = _op1;
        op2 = _op2;
        op3 = _op3;
        op4 = _op4;
    }
}
