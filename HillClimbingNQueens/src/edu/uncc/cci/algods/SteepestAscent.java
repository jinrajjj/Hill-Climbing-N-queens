package edu.uncc.cci.algods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Authors: Ankit Pandita, Avijit Jaiswal, Jinraj Jain
 */

public class SteepestAscent extends Board {
    private int numberOfStepsWhenSuccess = 0;
    private int numberOfIterationsWhenSuccess = 0;
    private int numberOfStepsWhenFailure = 0;
    private int numberOfIterationsWhenFailure = 0;
    private boolean isBoardChanged = true;
    private static int printCountSteepestAscent = 0;

    public SteepestAscent(int numOfQueens) {
        super(numOfQueens);
    }

    public int[] runSteepestAscentHillClimbingSearch() {
        printCountSteepestAscent++;
        printStartState();
        this.isBoardChanged = true;
        int currentStateHeuristic = this.calcHeuristic(this.board);
        List<int[][]> possibleStates = new ArrayList<>();
        this.resetRegMoves();
        while (this.calcHeuristic(this.board) != 0 && (this.isBoardChanged)) {
            int columnNumber = 0;
            int[][] possibleState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
            for (int i = 0; i < this.getNumberOfQueens(); i++) {
                if (this.getNumberOfQueens() >= 0)
                    System.arraycopy(this.board[i], 0, possibleState[i], 0, this.getNumberOfQueens());
            }
            currentStateHeuristic = this.calcHeuristic(this.board);
            this.isBoardChanged = false;
            while (columnNumber < this.getNumberOfQueens()) {
                int queenPositionInCurrentColumn = -1;
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    if (possibleState[i][columnNumber] == this.QUEEN)
                        queenPositionInCurrentColumn = i;
                    possibleState[i][columnNumber] = this.NOT_QUEEN;
                }
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    possibleState[i][columnNumber] = this.QUEEN;
                    int[][] newState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
                    for (int k = 0; k < this.getNumberOfQueens(); k++) {
                        if (this.getNumberOfQueens() >= 0)
                            System.arraycopy(possibleState[k], 0, newState[k], 0, this.getNumberOfQueens());
                    }
                    if (this.calcHeuristic(this.board) > this.calcHeuristic(newState))
                        possibleStates.add(newState); // Store the state in the list of successor states.
                    possibleState[i][columnNumber] = this.NOT_QUEEN;
                }
                possibleState[queenPositionInCurrentColumn][columnNumber] = this.QUEEN;
                columnNumber += 1;
            }

            Random rand = new Random();
            if (possibleStates.size() != 0) {
                int pick = rand.nextInt(possibleStates.size());
                int minHeuristic = currentStateHeuristic;
                if (minHeuristic > this.calcHeuristic(possibleStates.get(pick))) {
                    minHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.isBoardChanged = true;
                    this.regMoves += 1;
                    printCurrentState();
                    possibleStates.clear();
                }
            } else {
                if (possibleStates.size() == 0) {
                    this.isBoardChanged = false;
                    possibleStates.clear();
                }
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
        if (printCountSteepestAscent <= 4) {
            this.printGameState();
        }
    }

    private void printStartState() {
        if (printCountSteepestAscent <= 4) {
            this.startGameState();
        }
    }

}
