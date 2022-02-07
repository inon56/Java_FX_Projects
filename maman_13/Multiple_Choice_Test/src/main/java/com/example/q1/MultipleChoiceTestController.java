/*
Author Inon Levi
The MultipleChoiceTest controller class communicate with the multipleChoiceTest.fxml file and responsible for the functionality.
The application is loading an exam files and the user need to mark the button of the wright answer and press submit
and at the end of the test he can start a new test by pressing the New game button.

 */

package com.example.q1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Label;

public class MultipleChoiceTestController{
    private Random rand = new Random();
    int numQuestions;
    int correctAnswers;
    int currQuestionNumber;
    ArrayList<Question> questions;


    @FXML
    private Label questionNumber;
    @FXML
    private Label questionLine;
    @FXML
    private RadioButton answerA;
    @FXML
    private RadioButton answerB;
    @FXML
    private RadioButton answerC;
    @FXML
    private RadioButton answerD;
    @FXML
    private Label verifier;
    @FXML
    private Label grade;
    @FXML
    private ToggleGroup buttonToggle;
    @FXML
    private Button submitButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button newGameButton;

    @FXML
    public void initialize() {
        initGame();
    }


    private void setButtons(){
        verifier.setVisible(true);
        grade.setVisible(false);
        newGameButton.setVisible(true);
        newGameButton.setDisable(true);
        setNewQuestionButtons();
    }

    private void setNewQuestionButtons() {
        submitButton.setDisable(false);
        nextButton.setDisable(true);
    }

    public void initGame() {
        setButtons();
        correctAnswers = 0;
        currQuestionNumber = 0;
        questions = new ArrayList<Question>();
        loadFile();
        printNewPage();
    }

    private void PrintQuestion() {
        questionNumber.setText(String.valueOf(currQuestionNumber+1));
        print(questions.get(currQuestionNumber)); // Show the question and the list of answers to the user
    }

    private void print(Question question){
        List<String> answers = question.getAnswers();
        questionLine.setText(question.getQuestion());
        answerA.setText(answers.get(0));
        answerB.setText(answers.get(1));
        answerC.setText(answers.get(2));
        answerD.setText(answers.get(3));
    }

    private void loadFile(){
        try {
            Charset charset = Charset.forName("ISO-8859-1");
            List<String> lines = Files.readAllLines(Path.of("exam.txt"), charset);
            if (lines.size() % 5 != 0) {
                throw new IOException("File not valid: file should contains lines of question and 4 answers");
            }
            numQuestions = lines.size() / 5;
            for (int i = 0; i < lines.size(); i=i+5) {
                questions.add(new Question(
                        lines.get(i),
                        lines.get(i+1),
                        lines.get(i+2),
                        lines.get(i+3),
                        lines.get(i+4)));
            }
        }
        catch (IOException e){
            System.out.println("Failed to load questions file");
            e.printStackTrace();
        }
    }

    private void printNewPage() {
        PrintQuestion();
        setNewQuestionButtons();
        verifier.setText("");
    }

    private void printGrade() {
        int gradePercentage = (int)(((float)correctAnswers / (float)numQuestions) * 100);
        grade.setVisible(true);
        grade.setText("Grade: " + String.valueOf(gradePercentage) + "%");
    }

    @FXML
    void buttonPressed(ActionEvent event) {
        if (event.getSource() == submitButton)
        {
            String correctAnswer = questions.get(currQuestionNumber).getCorrectAnswer();
            boolean cond = ((RadioButton)buttonToggle.getSelectedToggle())
                    .getText()
                    .equals(correctAnswer);
            if (cond)
            {
                correctAnswers++;
                verifier.setText("Correct!");
            }
            else
                verifier.setText("Wrong! The answer is: " + questions.get(currQuestionNumber).getCorrectAnswer());
            submitButton.setDisable(true);
            if (currQuestionNumber < numQuestions - 1) {
                nextButton.setDisable(false);
            } else {
                newGameButton.setDisable(false);
                printGrade();
            }
        }
        if (event.getSource() == nextButton)
        {
            currQuestionNumber++;
            printNewPage();
            if(currQuestionNumber == numQuestions - 1)
            {
                nextButton.setDisable(true);
            }
        }
        if (event.getSource() == newGameButton)
        {
            initGame();
        }

    }


}