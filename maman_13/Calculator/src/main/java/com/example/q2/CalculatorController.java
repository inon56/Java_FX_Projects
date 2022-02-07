/*
Author Inon Levi
The Calculator controller class communicate with the calculator.fxml file and responsible for the functionality.
The calculator supports sequence of actions and after every secondary operand the user need to insert an equal sign.
 */

package com.example.q2;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class CalculatorController {
    private String operator;
    private Map<Integer, String> operands;
    private Map<Integer, Boolean> operandsSigns;
    private double result = 0;
    private int currOperandNumber;
    private boolean midCalc = false;
    @FXML
    private TextField expressionText;
    @FXML
    private TextField resultText;

    public void initialize()
    {
        operands = new HashMap<Integer, String>();
        operandsSigns = new HashMap<Integer, Boolean>(){{
            put(1, false);
            put(2, false);
    }};
        clear();
    }

    @FXML
    void onNumberClicked(MouseEvent event) {
        String digitEntered = (((Button)event.getSource()).getText());
        if (midCalc) {
            operands.put(currOperandNumber, operands.get(currOperandNumber).concat(digitEntered));
        } else {
            operands.put(currOperandNumber, digitEntered);
            operandsSigns.put(currOperandNumber, false);
            midCalc = true;
        }
        printInsertedOperand(operands.get(currOperandNumber));
    }

    private Double GetSignedOperand(int operandNumber) {
        return  operandsSigns.get(operandNumber) ?
                Double.parseDouble(operands.get(operandNumber)) * (-1) :
                Double.parseDouble(operands.get(operandNumber));
    }

    private String GetSignedOperandText(int operandNumber) {
        return  operandsSigns.get(operandNumber) ?
                "-" + operands.get(operandNumber) :
                operands.get(operandNumber);
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Button)event.getSource()).getId().replace("btn","");
        if(symbol.equals("Equal"))
        {
            boolean validRes = true;
            if (currOperandNumber == 1) {
                calculate(GetSignedOperand(1));
                printOperandOnEquality();
            } else {
                validRes = calculate(
                        GetSignedOperand(1),
                        GetSignedOperand(2),
                        operator);
                if (validRes)
                    printExpressionOnEquality();
                else
                    printErrorCalc();
                currOperandNumber = 1;
                operands.put(2, "");
                operands.put(1, String.valueOf(Math.abs(result)));
                operandsSigns.put(1, result >= 0 ? false : true);
            }
            if (validRes)
                printResult();
            // reset();
            midCalc = false;
        }
        else if(symbol.equals("Clear"))
        {
            clear();
        }
        else if(symbol.equals("Sign"))
        {
            operandsSigns.put(currOperandNumber, !operandsSigns.get(currOperandNumber));
            printSignedNumber();
        }
        else if(symbol.equals("Dot"))
        {
            addDot();
        }
        else // At operation on the numbers
        {
            switch (symbol)
            {
                case "Plus":
                    operator = "+";
                    break;
                case "Minus":
                    operator = "-";
                    break;
                case "Multiply":
                    operator = "*";
                    break;
                case "Divide":
                    operator = "/";
                    break;
            } // End of switch
            if (currOperandNumber == 1) {
                currOperandNumber = 2;
            }
            printExpressionOnOperation();
        }
    }

    private void printInsertedOperand(String operand){
        resultText.setText(operand);
    }

    private void printResult() {
        resultText.setText(String.valueOf(result));
    }

    private void printExpressionOnOperation() {
        expressionText.setText(GetSignedOperandText(1) + operator);
    }


    private void printOperandOnEquality(){
        expressionText.setText(GetSignedOperandText(1));
    }

    private void printExpressionOnEquality(){
        expressionText.setText(GetSignedOperandText(1) + operator + GetSignedOperandText(2));
    }

    private void clear(){
        resultText.setText("0");
        expressionText.setText("");
        reset();
    }

    private void reset(){
        currOperandNumber = 1;
        midCalc = false;
        operands.put(1, "");
        operands.put(2, "");
    }

    private void addDot(){
        if (!operands.get(currOperandNumber).contains("."))
            operands.put(currOperandNumber, operands.get(currOperandNumber).concat("."));
    }

    // Changes between positive and negative number
    private void printSignedNumber(){
        if(resultText.getText(0,1).equals("-"))
            resultText.setText(resultText.getText(1,resultText.getLength()));
        else
            resultText.setText("-" + resultText.getText());
    }

    private void printErrorCalc(){
        resultText.setAlignment(Pos.CENTER_LEFT);
        resultText.setText("Invalid calculation");
        resultText.setAlignment(Pos.CENTER_RIGHT);
    }

    private boolean calculate(double num) {
        result = num;
        return true;
    }

    private boolean calculate(double num1in, double num2in, String operator) {
        switch (operator)
        {
            case "+":
                result =  num1in + num2in;
                break;
            case "-":
                result =  num1in - num2in;
                break;
            case "*":
                result =  num1in * num2in;
                break;
            case "/":
                if(Math.abs(num2in - 0) <  0.000001d) {
                    result = 0.0;
                    return false;
                } else {
                    result = num1in / num2in;
                }
                break;
        } // End of switch
        return true;
    }
}