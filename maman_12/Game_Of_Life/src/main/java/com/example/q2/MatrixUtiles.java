package com.example.q2;

import java.util.Random;
import java.util.Arrays;

public class MatrixUtiles {

    private static Random rand = new Random();

    // Filling the matrix with random values
    public static void fillMatrix(int[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                mat[i][j] = rand.nextInt(2);
            }
        }
    }

    // Change values of matrix "Game of life" to next generation
    public static int[][] nextGeneration(int[][] mat) {
        int[][] newMat = new int[mat.length][mat.length];
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat.length; j++){
                newMat[i][j] = mat[i][j];
            }
        }

        int aliveNeighbors = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                // count neighbors
                if (i > 0) {
                    if (j > 0) {
                        aliveNeighbors += mat[i - 1][j - 1];
                    }
                    if (j < (mat.length -1) ) {
                        aliveNeighbors += mat[i - 1][j + 1];
                    }
                    aliveNeighbors += mat[i - 1][j];
                }
                if (i < (mat.length -1)) {
                    if (j > 0) {
                        aliveNeighbors += mat[i + 1][j - 1];
                    }
                    if (j < (mat.length - 1)) {
                        aliveNeighbors += mat[i + 1][j + 1];
                    }
                    aliveNeighbors += mat[i + 1][j];
                }
                if (j > 0) {
                    aliveNeighbors += mat[i][j - 1];
                }
                if (j < (mat.length - 1)) {
                    aliveNeighbors += mat[i][j + 1];
                }
                // make decision
                if (aliveNeighbors == 3) {
                    System.out.println("new gen 1");
                    newMat[i][j] = 1;
                } else if (aliveNeighbors != 2) {
                    System.out.println("new gen 0");
                    newMat[i][j] = 0;
                }
                aliveNeighbors = 0;
            }
        }
        return newMat;
    }


}
