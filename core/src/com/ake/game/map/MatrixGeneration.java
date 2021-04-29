package com.ake.game.map;

import java.util.*;
import java.lang.Math;

/**
 * Matrix Generation Algorithm
 * 
 * @author Ankit Oli, Shreyas Borole
 */
class MatrixGeneration {

    private final int n;
    private final int initialObstaclesCount;

    private int obstaclesCount;
    private final Random random;
    private int indexI, indexJ;
    private int endPointCount;
    private int count;
    private int matrix[][];
    private int distance;

    public MatrixGeneration(int matrixSize, long seed) {
        this.n = matrixSize;
        this.initialObstaclesCount = this.n * 5;
        this.obstaclesCount = initialObstaclesCount;
        this.endPointCount = (n / 2) + 1;
        this.count = 0;
        this.matrix = new int[this.n][this.n];
        this.random = new Random(seed);
    }

    private void initMatrix() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = 0;
    }

    private void generateObstacles() {
        for (int i = 0; i < obstaclesCount; i++) {
            indexI = random.nextInt(n);
            indexJ = random.nextInt(n);
            matrix[indexI][indexJ] = 1;
        }
    }

    public void displayMatrix() {
        System.out.println("Number of Destinations : " + endPointCount);
        System.out.println("Number of Obstacles : " + initialObstaclesCount);
        System.out.println("Number of Obstacles added : " + obstaclesCount);

        for (int i = 0; i < n; i++) {
            System.out.println();
            for (int j = 0; j < n; j++)
                System.out.print("  " + matrix[i][j]);
        }
        System.out.println();
    }

    public int[][] generateMatrix() {
        int x, y, d = 1, I, J;
        initMatrix();
        generateObstacles();
        I = random.nextInt(n);
        J = random.nextInt(n);
        for (int i = 0; i <= n; i++) {
            do{
                indexI = random.nextInt(n);
                indexJ = random.nextInt(n);
            }while(indexI == I && indexJ == J);
            x = Math.abs(I - indexI);
            y = Math.abs(J - indexJ); 
            distance = d * (x + y);
            if (distance > 5)
            addEndPoints(matrix, indexI, indexJ);
        }
        matrix[I][J] = -1;
        switch (check(I, J)) {
        case 1:
            matrix[I][J + 1] = 0;
            obstaclesCount--;
            break;
        case 2:
            matrix[I][J - 1] = 0;
            obstaclesCount--;
            break;
        case 3:
            matrix[I + 1][J] = 0;
            obstaclesCount--;
            break;
        case 4:
            matrix[I - 1][J] = 0;
            obstaclesCount--;
            break;
        case 5:
            matrix[I - 1][J] = 0;
            obstaclesCount--;
            break;
        case 6:
            matrix[I][J - 1] = 0;
            obstaclesCount--;
            break;
        case 7:
            matrix[I + 1][J] = 0;
            obstaclesCount--;
            break;
        case 8:
            matrix[I + 1][J] = 0;
            obstaclesCount--;
            break;
        case 9:
            matrix[I - 1][J] = 0;
            matrix[I + 1][J] = 0;
            obstaclesCount -= 2;
            break;
        }
        return matrix;
    }

    private boolean isSafe(int i, int j) {
        if (i >= 0 && i < n && j >= 0 && j < n)
            return true;
        return false;
    }

    private int check(int i, int j) {
        int obstacle = 1;
        // [row][col]
        if (i == 0 || j == 0 || i == n - 1 || j == n - 1) {
            // EDGES
            // left
            if (j == 0 && i - 1 >= 0 && i + 1 < n && j + 1 < n)
                if (matrix[i - 1][j] == obstacle && matrix[i + 1][j] == obstacle
                        && matrix[i][j + 1] == obstacle)
                    return 1;
            // right
            if (j == n - 1 && i - 1 >= 0 && i + 1 < n && j - 1 >= 0)
                if (matrix[i - 1][j] == obstacle && matrix[i + 1][j] == obstacle
                        && matrix[i][j - 1] == obstacle)
                    return 2;
            // top
            if (i == 0 && i + 1 < n && j - 1 >= 0 && j + 1 < n)
                if (matrix[i + 1][j] == obstacle && matrix[i][j - 1] == obstacle
                        && matrix[i][j + 1] == obstacle)
                    return 3;
            // bottom
            if (i == n - 1 && i - 1 >= 0 && j - 1 >= 0 && j + 1 < n)
                if (matrix[i - 1][j] == obstacle && matrix[i][j - 1] == obstacle
                        && matrix[i][j + 1] == obstacle)
                    return 4;

            // CORNERS
            // bottom-left
            if (j == 0 && i == n - 1 && i - 1 >= 0 && j + 1 < n)
                if (matrix[i - 1][j] == obstacle && matrix[i][j + 1] == obstacle)
                    return 5;
            // bottom-right
            if (j == n - 1 && i == n - 1 && i - 1 >= 0 && j - 1 >= 0)
                if (matrix[i - 1][j] == obstacle && matrix[i][j - 1] == obstacle)
                    return 6;
            // top-left
            if (j == 0 && i == 0 && i + 1 < n && j + 1 < n)
                if (matrix[i + 1][j] == obstacle && matrix[i][j + 1] == obstacle)
                    return 7;
            // top-right
            if (j == n - 1 && i == 0 && i + 1 < n && j - 1 >= 0)
                if (matrix[i + 1][j] == obstacle && matrix[i][j - 1] == obstacle)
                    return 8;
        } else {
            // CENTER
            if (matrix[i - 1][j] == obstacle && matrix[i + 1][j] == obstacle
                    && matrix[i][j - 1] == obstacle && matrix[i][j + 1] == obstacle)
                return 9;
        }
        return 0;
    }

    private void addEndPoints(int matrix[][], int i, int j) {
        if (isSafe(i, j) && count != endPointCount) {
            matrix[i][j] = 9;
            count++;

            switch (check(i, j)) {
            case 1:
                matrix[i][j + 1] = 0;
                obstaclesCount--;
                break;
            case 2:
                matrix[i][j - 1] = 0;
                obstaclesCount--;
                break;
            case 3:
                matrix[i + 1][j] = 0;
                obstaclesCount--;
                break;
            case 4:
                matrix[i - 1][j] = 0;
                obstaclesCount--;
                break;
            case 5:
                matrix[i - 1][j] = 0;
                obstaclesCount--;
                break;
            case 6:
                matrix[i][j - 1] = 0;
                obstaclesCount--;
                break;
            case 7:
                matrix[i + 1][j] = 0;
                obstaclesCount--;
                break;
            case 8:
                matrix[i + 1][j] = 0;
                obstaclesCount--;
                break;
            case 9:
                matrix[i - 1][j] = 0;
                matrix[i + 1][j] = 0;
                obstaclesCount -= 2;
                break;
            }

        }
    }

}