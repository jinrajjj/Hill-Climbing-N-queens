package edu.uncc.cci.algods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Authors: Ankit Pandita, Avijit Jaiswal, Jinraj Jain
 */

public class SidewaysMove extends Board {
    private int numberOfStepsWhenSuccess = 0;
    private int numberOfIterationsWhenSuccess = 0;
    private int numberOfStepsWhenFailure = 0;
    private int numberOfIterationsWhenFailure = 0;
    private boolean boardModified = true;
    private int consecutiveMoves = 0;
    private final int LIMIT_SIDEWAYS_CONSECUTIVE_MOVES = 100;
    private static int printCountSidewaysMove = 0;

    public SidewaysMove(int numOfQueens) {
        super(numOfQueens);
    }

    public int[] runWithSidewaysMoveHCSearchAlgorithm() {
        printCountSidewaysMove++;
        printStartState();
        this.boardModified = true;
        int currentBoardStateHeuristic = this.calcHeuristic(this.board);
        List<int[][]> possibleStates = new ArrayList<>();
        this.resetRegMoves();
        while (this.calcHeuristic(this.board) != 0 && (this.boardModified)) {
            boolean moveMade = false;
            int columnNo = 0;
            int[][] possibleState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
            for (int i = 0; i < this.getNumberOfQueens(); i++) {
                if (this.getNumberOfQueens() >= 0)
                    System.arraycopy(this.board[i], 0, possibleState[i], 0, this.getNumberOfQueens());
            }
            currentBoardStateHeuristic = this.calcHeuristic(this.board);
            this.boardModified = false;
            while (columnNo < this.getNumberOfQueens()) {
                int currentColumnQueenPosition = -1;
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    if (possibleState[i][columnNo] == this.QUEEN)
                        currentColumnQueenPosition = i;
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                for (int i = 0; i < this.getNumberOfQueens(); i++) { //For each row, place a queen in the column and store the state in the list if h <= h(current state)
                    possibleState[i][columnNo] = this.QUEEN;
                    int[][] newMove = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
                    for (int k = 0; k < this.getNumberOfQueens(); k++) {
                        if (this.getNumberOfQueens() >= 0)
                            System.arraycopy(possibleState[k], 0, newMove[k], 0, this.getNumberOfQueens());
                    }
                    if (this.calcHeuristic(this.board) >= this.calcHeuristic(newMove) && this.areBoardsEqual(this.board, newMove))
                        possibleStates.add(newMove);
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                possibleState[currentColumnQueenPosition][columnNo] = this.QUEEN;
                columnNo += 1;
            }

            Random randomNumber = new Random();
            int minimumHeuristic = currentBoardStateHeuristic;
            if (possibleStates.size() != 0) {
                int pick = randomNumber.nextInt(possibleStates.size());
                if (minimumHeuristic > this.calcHeuristic(possibleStates.get(pick))) {
                    minimumHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.boardModified = true;
                    this.regMoves += 1;
                    this.consecutiveMoves = 0;
                    printCurrentState();
                    possibleStates.clear();
                } else if (minimumHeuristic == this.calcHeuristic(possibleStates.get(pick)) &&
                        this.consecutiveMoves < this.LIMIT_SIDEWAYS_CONSECUTIVE_MOVES) {
                    minimumHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.boardModified = true;
                    this.consecutiveMoves += 1;
                    this.regMoves += 1;
                    printCurrentState();
                    possibleStates.clear();
                }
            } else {
                this.boardModified = false;
            }
        }

        if (this.calcHeuristic(this.board) == 0) {
            this.numberOfStepsWhenSuccess += this.regMoves;
            this.numberOfIterationsWhenSuccess += 1;
        } else {
            printCurrentState();
            this.numberOfStepsWhenFailure += this.regMoves;
            this.numberOfIterationsWhenFailure += 1;
        }

        return new int[]{numberOfStepsWhenSuccess, numberOfIterationsWhenSuccess, numberOfStepsWhenFailure, numberOfIterationsWhenFailure};
    }

    private void printCurrentState() {
        if (printCountSidewaysMove <= 4) {
            this.printGameState();
        }
    }

    private void printStartState() {
        if (printCountSidewaysMove <= 4) {
            this.startGameState();
        }
    }
}

