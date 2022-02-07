package com.example.q2;

import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Arrays;
import java.util.Random;


public class GameOfLifeController {

    private GraphicsContext gc;
    private static int size = 10;
    private int[][] mat;
    private double rectSize;

    @FXML
    private Button button1;

    @FXML
    private Canvas canvas;

    public GameOfLifeController() {
        mat = new int[size][size];
        rectSize = 30;
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        rectSize = canvas.getWidth() / size;
        MatrixUtiles.fillMatrix(mat);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, rectSize*10, rectSize*10);
        drawMatrix(mat);
    }

    @FXML
    public void buttonPressed(ActionEvent event) {
        int[][] newMat = new int[10][10];
        newMat = MatrixUtiles.nextGeneration(mat);
        drawMatrix(newMat);
        mat = newMat;
    }

    // Drawing the matrix on the canvas, called each time the button is pressed
    public void drawMatrix(int[][] mat){
        double x = 0;
        double y = 0;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mat[i][j] == 1) {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(x, y, rectSize-1, rectSize-1);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x, y, rectSize-1, rectSize-1);
                }
                x = x + rectSize;
            }
            y = y + rectSize;
            x = 0;
        }
    }
}