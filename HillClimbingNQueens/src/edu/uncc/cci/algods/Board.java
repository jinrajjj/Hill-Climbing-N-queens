package edu.uncc.cci.algods;

import java.util.Random;

/**
 * Authors: Ankit Pandita, Avijit Jaiswal, Jinraj Jain
 */

public abstract class Board {
    private Random randomNumber = new Random();
    public final int QUEEN = 1;
    public final int NOT_QUEEN = 0;
    public int numberOfQueens;
    public int[][] board;
    private int[][] board1;
    private int[][] board2;
    private int[][] board3;
    public int regMoves = 0;
    public int sideMoves = 0;
    public int resetCount = -1;

    //Initializing done in the constructor
    public Board(int numberOfQueens) {
        this.numberOfQueens = numberOfQueens;
        this.board1 = new int[numberOfQueens][numberOfQueens];
        this.board2 = new int[numberOfQueens][numberOfQueens];
        this.board3 = new int[numberOfQueens][numberOfQueens];
        this.board = new int[numberOfQueens][numberOfQueens];
        this.resetBoard();
        this.setBoard();
    }

    public int getNumberOfQueens() {
        return this.numberOfQueens;
    }

    public int[][] getBoard1() {
        return this.board1;
    }

    public int[][] getBoard2() {
        return this.board2;
    }

    public int[][] getBoard3() {
        return this.board3;
    }

    //Select any one of the three boards
    public void setBoard() {
        int n = randomNumber.nextInt(2);
        if (n == 0)
            this.board = this.copyState(board1);
        else if (n == 1)
            this.board = this.copyState(board2);
        else
            this.board = this.copyState(board3);
    }

    public void resetBoard() {
        int queenCount = this.numberOfQueens;
        for (int i = 0; i < queenCount; i++) {
            int queenPosY = randomNumber.nextInt(queenCount - 1);
            for (int j = 0; j < queenCount; j++) {
                if (j == queenPosY)
                    this.board1[j][i] = QUEEN;
                else
                    this.board1[j][i] = NOT_QUEEN;
            }
        }

        for (int i = 0; i < queenCount; i++) {
            int queenPosY = randomNumber.nextInt(queenCount - 1);
            for (int j = 0; j < queenCount; j++) {
                if (j == queenPosY)
                    this.board2[j][i] = QUEEN;
                else
                    this.board2[j][i] = NOT_QUEEN;
            }
        }

        for (int i = 0; i < queenCount; i++) {
            int queenPosY = randomNumber.nextInt(queenCount - 1);
            for (int j = 0; j < queenCount; j++) {
                if (j == queenPosY)
                    this.board3[j][i] = QUEEN;
                else
                    this.board3[j][i] = NOT_QUEEN;
            }
        }
        this.resetCount += 1; // Increment the number of resets
    }

    public int[][] getGameBoard() {
        return this.board;
    }
    public int calcHeuristic(int[][] state) {
        int[][] board = state;
        int heuristicValue = 0;
        for (int i = 0; i < this.numberOfQueens; i++) {
            int[] row = board[i];
            int queenCount = 0;
            for (int r : row) {
                if (r == QUEEN)
                    queenCount += 1;
            }
            if (queenCount > 1) {
                for (int q = queenCount; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }
        }

        for (int i = 0; i < this.numberOfQueens; i++) {
            int queenCount = 0;
            for (int j = 0; j < this.numberOfQueens; j++) {
                if (board[j][i] == QUEEN)
                    queenCount += 1;
            }

            if (queenCount > 1) {
                for (int q = queenCount; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }
        }

        int numOfQueens = 0;
        int iteration = 0;
        while (iteration < (this.numberOfQueens - 1)) {
            numOfQueens = 0;
            int xValue = 0;
            int yValue = iteration;
            while (xValue <= iteration) {
                if (board[xValue][yValue] == QUEEN)
                    numOfQueens += 1;
                yValue -= 1;
                xValue += 1;
            }
            if (numOfQueens > 1) {
                for (int q = numOfQueens; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }
            iteration += 1;
        }

        numOfQueens = 0;
        int diagonal = this.numberOfQueens - 1;
        for (int i = 0; i < this.numberOfQueens; i++) {
            if (board[i][diagonal] == QUEEN)
                numOfQueens += 1;
            diagonal -= 1;
        }

        if (numOfQueens > 1) {
            for (int q = numOfQueens; q > 1; --q) {
                heuristicValue += q - 1;
            }
        }

        numOfQueens = 0;
        iteration = 1;
        while (iteration < (this.numberOfQueens - 1)) {
            numOfQueens = 0;
            int xValue = iteration;
            int yValue = this.numberOfQueens - 1;
            while (xValue < this.numberOfQueens) {
                if (board[xValue][yValue] == QUEEN)
                    numOfQueens += 1;

                yValue -= 1;
                xValue += 1;
            }
            if (numOfQueens > 1) {
                for (int q = numOfQueens; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }
            iteration += 1;
        }

        numOfQueens = 0;
        iteration = this.numberOfQueens - 2;
        while (iteration > 0) {
            numOfQueens = 0;
            int xValue = 0;
            int yValue = iteration;

            while (yValue < this.numberOfQueens) {
                if (board[xValue][yValue] == QUEEN)
                    numOfQueens += 1;

                yValue += 1;
                xValue += 1;
            }

            if (numOfQueens > 1) {
                for (int q = numOfQueens; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }

            iteration -= 1;
        }

        numOfQueens = 0;
        diagonal = 0;
        for (int i = 0; i < this.numberOfQueens; i++) {
            if (board[i][diagonal] == QUEEN)
                numOfQueens += 1;
            diagonal += 1;
        }

        if (numOfQueens > 1) {
            for (int q = numOfQueens; q > 1; --q) {
                heuristicValue += q - 1;
            }
        }

        numOfQueens = 0;
        iteration = 1;
        while (iteration < this.numberOfQueens) {
            numOfQueens = 0;
            int xValue = iteration;
            int yValue = 0;
            while (xValue < this.numberOfQueens) {
                if (board[xValue][yValue] == QUEEN)
                    numOfQueens += 1;
                yValue += 1;
                xValue += 1;
            }
            if (numOfQueens > 1) {
                for (int q = numOfQueens; q > 1; --q) {
                    heuristicValue += q - 1;
                }
            }
            iteration += 1;
        }

        return heuristicValue;
    }

    //Reset regular moves after each iteration
    public void resetRegMoves() {
        this.regMoves = 0;
    }

    //Return number of resets done
    public int getResets() {
        return this.resetCount;
    }

    //Reset sideway Moves after each iteration
    public void resetSideMoves() {
        this.sideMoves = 0;
    }

    //Function to copy a state into a temporary state and return new state.
    public int[][] copyState(int[][] oldState) {
        int[][] newState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
        //iteratively copy each element from old to the new state
        for (int i = 0; i < oldState.length; i++) {
            System.arraycopy(oldState[i], 0, newState[i], 0, oldState[i].length);
        }
        return newState;
    }

    //Function to display initial game state heuristic value and board for each iteration.
    public void startGameState() {
        System.out.println("\n\n ------------------------------------------------------ ");
        System.out.println("Initial State");
        System.out.println("Current State Heuristic Value: " + this.calcHeuristic(this.board)); //heuristic value
        System.out.println("Current State: \n");
        for (int[] intArray : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                System.out.print(intArray[j] + " ");
            }
            System.out.println("");
        }
    }

    //Function to print current state heuristic value and state board in each step
    public void printGameState() {
        System.out.println("\nCurrent State Heuristic Value: " + this.calcHeuristic(this.board)); //heuristic value
        System.out.println("Current State: \n"); //print each element of the current state board
        for (int[] intArray : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                System.out.print(intArray[j] + " ");
            }
            System.out.println("");
        }
    }

    //Function to check if two boards passed are equal
    public boolean areBoardsEqual(int[][] boardOne, int[][] boardTwo) {
        boolean are2BoardsEqual = true;
        for (int i = 0; i < this.getNumberOfQueens(); i++) {
            for (int j = 0; j < this.getNumberOfQueens(); j++) {
                if (boardOne[i][j] != boardTwo[i][j]) {
                    are2BoardsEqual = false;
                    break;
                }
            }
        }
        return !are2BoardsEqual;
    }
}

