package com.example.q1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private String correctAnswer;

    public Question(
            String question,
            String answerACorrect,
            String answerB,
            String answerC,
            String answerD){
        this.question = question;
        this.answers = Arrays.asList(answerACorrect, answerB, answerC, answerD);
        this.correctAnswer = answerACorrect;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getAnswers() {
        Collections.shuffle(answers);
        return answers;
    }


}
