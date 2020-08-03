package edu.uncc.cci.algods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Authors: Ankit Pandita, Avijit Jaiswal, Jinraj Jain
 */

public class RandomRestart extends Board {
    private int numStepsSuccess = 0;
    private int numIterationsSuccess = 0;
    private boolean boardModified = true;
    private int consecutiveSidewaysMoves = 0;
    private final int LIMIT_CONSECUTIVE_SIDEWAYS_MOVES = 100;

    public RandomRestart(int numOfQueens) {
        super(numOfQueens);
    }

    public int[] runRandomRestartHCSearchWithoutSidewayMoves() {
        int currentStateHeuristic = this.calcHeuristic(this.board);
        List<int[][]> possibleStates = new ArrayList<>();
        this.resetRegMoves();
        while (this.calcHeuristic(this.board) != 0 && boardModified) {
            int columnNo = 0;
            int[][] possibleState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
            for (int i = 0; i < this.getNumberOfQueens(); i++) {
                if (this.getNumberOfQueens() >= 0)
                    System.arraycopy(this.board[i], 0, possibleState[i], 0, this.getNumberOfQueens());
            }
            currentStateHeuristic = this.calcHeuristic(this.board);
            this.boardModified = false;
            while (columnNo < this.getNumberOfQueens()) {
                int queenPositionInCurrentColumn = -1;

                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    if (possibleState[i][columnNo] == this.QUEEN)
                        queenPositionInCurrentColumn = i;
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    possibleState[i][columnNo] = this.QUEEN;
                    int[][] newState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
                    for (int k = 0; k < this.getNumberOfQueens(); k++) {
                        if (this.getNumberOfQueens() >= 0)
                            System.arraycopy(possibleState[k], 0, newState[k], 0, this.getNumberOfQueens());
                    }
                    if (this.calcHeuristic(this.board) > this.calcHeuristic(newState))
                        possibleStates.add(newState);
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                possibleState[queenPositionInCurrentColumn][columnNo] = this.QUEEN;
                columnNo += 1;
            }
            Random randomNumber = new Random();
            if (possibleStates.size() != 0) {
                int pick = randomNumber.nextInt(possibleStates.size());
                int minimumHeuristic = currentStateHeuristic;
                if (minimumHeuristic > this.calcHeuristic(possibleStates.get(pick))) {
                    minimumHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.boardModified = true;
                    this.regMoves += 1;
                    possibleStates.clear();
                }
            } else {
                this.resetBoard();
                this.setBoard();
                this.boardModified = true;
                possibleStates.clear();
            }
        }
        if (this.calcHeuristic(this.board) == 0) {
            this.numStepsSuccess += this.regMoves;
            this.numIterationsSuccess += 1;
        }
        return new int[]{numStepsSuccess, numIterationsSuccess, this.getResets()};
    }

    public int[] runRandomRestartHCSearchWithSidewayMoves() {
        int currentStateHeuristic = this.calcHeuristic(this.board);
        List<int[][]> possibleStates = new ArrayList<>();
        this.resetRegMoves();

        while (this.calcHeuristic(this.board) != 0 && boardModified) {
            int columnNo = 0;
            int[][] possibleState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
            for (int i = 0; i < this.getNumberOfQueens(); i++) {
                if (this.getNumberOfQueens() >= 0)
                    System.arraycopy(this.board[i], 0, possibleState[i], 0, this.getNumberOfQueens());
            }
            currentStateHeuristic = this.calcHeuristic(this.board);
            this.boardModified = false;

            while (columnNo < this.getNumberOfQueens()) {
                int queenPositionInCurrentColumn = -1;
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    if (possibleState[i][columnNo] == this.QUEEN)
                        queenPositionInCurrentColumn = i;
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                for (int i = 0; i < this.getNumberOfQueens(); i++) {
                    possibleState[i][columnNo] = this.QUEEN;
                    int[][] newState = new int[this.getNumberOfQueens()][this.getNumberOfQueens()];
                    for (int k = 0; k < this.getNumberOfQueens(); k++) {
                        if (this.getNumberOfQueens() >= 0)
                            System.arraycopy(possibleState[k], 0, newState[k], 0, this.getNumberOfQueens());
                    }
                    if (this.calcHeuristic(this.board) >= this.calcHeuristic(newState) && this.areBoardsEqual(this.board, newState))
                        possibleStates.add(newState);
                    possibleState[i][columnNo] = this.NOT_QUEEN;
                }
                possibleState[queenPositionInCurrentColumn][columnNo] = this.QUEEN;
                columnNo += 1;
            }
            Random randomNumber = new Random();
            int minimumHeuristic = currentStateHeuristic;
            if (possibleStates.size() != 0) {
                int pick = randomNumber.nextInt(possibleStates.size());
                if (minimumHeuristic > this.calcHeuristic(possibleStates.get(pick))) {
                    minimumHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.boardModified = true;
                    this.consecutiveSidewaysMoves = 0;
                    this.regMoves += 1;
                    possibleStates.clear();
                } else if (minimumHeuristic == this.calcHeuristic(possibleStates.get(pick)) &&
                        this.consecutiveSidewaysMoves < this.LIMIT_CONSECUTIVE_SIDEWAYS_MOVES) {
                    minimumHeuristic = this.calcHeuristic(possibleStates.get(pick));
                    this.board = this.copyState(possibleStates.get(pick));
                    this.boardModified = true;
                    this.consecutiveSidewaysMoves += 1;
                    this.regMoves += 1;
                    possibleStates.clear();
                } else {
                    this.resetBoard();
                    this.setBoard();
                    this.boardModified = true;
                    possibleStates.clear();
                }
            } else {
                this.resetBoard();
                this.setBoard();
                this.boardModified = true;
                possibleStates.clear();
            }
        }
        if (this.calcHeuristic(this.board) == 0) {
            this.numStepsSuccess += this.regMoves;
            this.numIterationsSuccess += 1;
        }
        return new int[]{numStepsSuccess, numIterationsSuccess, this.getResets()};
    }
}