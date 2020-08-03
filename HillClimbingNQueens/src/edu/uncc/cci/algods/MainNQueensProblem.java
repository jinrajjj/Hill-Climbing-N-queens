package edu.uncc.cci.algods;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Authors: Ankit Pandita, Avijit Jaiswal, Jinraj Jain
 */

public class MainNQueensProblem {
    public static void main(String[] args) {
        int numberOfQueens = 0;
        Scanner input = new Scanner(System.in);
        DecimalFormat format = new DecimalFormat("##.##");
        int[] hillClimbingSteepestAscent = {0, 0, 0, 0};
        int[] hillClimbingSideways = {0, 0, 0, 0};
        int[] RandomRestartWithoutSideways = {0, 0, 0, 0};
        int[] RandomRestartWithSideways = {0, 0, 0, 0};
        while (numberOfQueens <= 3) {
            System.out.println("Please enter the number of queens (must be > 3): ");
            numberOfQueens = input.nextInt();
        }
        System.out.println("Enter the number of runs: ");
        int numOfRuns = input.nextInt();
        int choice = -1;
        while (choice <= 0 || choice >= 5) {
            System.out.println("Please select the Hill Climbing Search Method: ");
            System.out.println("1. Hill Climbing Search using Steepest Ascent");
            System.out.println("2. Hill Climbing Search using Sideways Move");
            System.out.println("3. Random Restart Hill climbing Search without Sideways Move");
            System.out.println("4. Random Restart Hill climbing Search with Sideways Move");
            System.out.println("Enter choice: ");
            choice = input.nextInt();
        }
        input.close();
        switch (choice) {
            case 1: // Steepest Ascent Hill Climbing Search
                for (int i = 0; i < numOfRuns; i++) {
                    SteepestAscent game = new SteepestAscent(numberOfQueens); //class object
                    int[] results = game.runSteepestAscentHillClimbingSearch();
                    hillClimbingSteepestAscent[0] += results[0];
                    hillClimbingSteepestAscent[1] += results[1];
                    hillClimbingSteepestAscent[2] += results[2];
                    hillClimbingSteepestAscent[3] += results[3];
                }
                System.out.println("\n -------------------------------------------------------------------------------- ");
                System.out.println("Hill Climbing Search using Steepest Ascent");
                System.out.println("Number of Queens: " + numberOfQueens);
                System.out.println("Number of Iterations: " + numOfRuns);
                System.out.println("Success Rate: " + format.format((hillClimbingSteepestAscent[1] * 100) / (float) numOfRuns) + "%");
                System.out.println("Failure Rate: " + format.format((hillClimbingSteepestAscent[3] * 100) / (float) numOfRuns) + "%");
                if (hillClimbingSteepestAscent[1] != 0)
                    System.out.println("Average Number of Steps when Algorithm Succeeds: " + (hillClimbingSteepestAscent[0] / hillClimbingSteepestAscent[1]));
                if (hillClimbingSteepestAscent[3] != 0)
                    System.out.println("Average Number of Steps when Algorithm Fails: " + (hillClimbingSteepestAscent[2] / hillClimbingSteepestAscent[3]));
                break;
            case 2: // Sideways Move Hill Climbing Search
                for (int i = 0; i < numOfRuns; i++) {
                    SidewaysMove game = new SidewaysMove(numberOfQueens); //class object
                    int[] results = game.runWithSidewaysMoveHCSearchAlgorithm();
                    hillClimbingSideways[0] += results[0];
                    hillClimbingSideways[1] += results[1];
                    hillClimbingSideways[2] += results[2];
                    hillClimbingSideways[3] += results[3];
                }
                System.out.println("\n --------------------------------------------------------------------------------");
                System.out.println("Hill Climbing Search using Sideways Move");
                System.out.println("Number of Queens: " + numberOfQueens);
                System.out.println("Number of Iterations: " + numOfRuns);
                System.out.println("Success Rate: " + format.format((hillClimbingSideways[1] * 100) / (float) numOfRuns) + "%");
                System.out.println("Failure Rate: " + format.format((hillClimbingSideways[3] * 100) / (float) numOfRuns) + "%");
                if (hillClimbingSideways[1] != 0)
                    System.out.println("Average Number of Steps when Algorithm Succeeds: " + (hillClimbingSideways[0] / hillClimbingSideways[1]));
                if (hillClimbingSideways[3] != 0)
                    System.out.println("Average Number of Steps when Algorithm Fails: " + (hillClimbingSideways[2] / hillClimbingSideways[3]));
                break;
            case 3: // Random Restart algorithm
                for (int i = 0; i < numOfRuns; i++) {
                    RandomRestart game = new RandomRestart(numberOfQueens); //class object
                    int[] results = game.runRandomRestartHCSearchWithoutSidewayMoves();
                    RandomRestartWithoutSideways[0] += results[0];
                    RandomRestartWithoutSideways[1] += results[1];
                    RandomRestartWithoutSideways[2] += results[2];
                }
                System.out.println("\n -------------------------------------------------------------------------------- ");
                System.out.println("Random Restart Hill Climbing Search without Sideways Move");
                System.out.println("Number of Queens: " + numberOfQueens);
                System.out.println("Number of Iterations: " + numOfRuns);
                System.out.println("Success Rate: " + format.format((RandomRestartWithoutSideways[1] * 100) / (float) numOfRuns) + "%");
                System.out.println("Average Number of Steps when Algorithm Succeeds: " + (RandomRestartWithoutSideways[0] / numOfRuns));
                System.out.println("Average Number of Restarts when Algorithm Succeeds: " + (RandomRestartWithoutSideways[2] / numOfRuns));
                break;
            case 4: // Random Restart with Sideways Move.
                for (int i = 0; i < numOfRuns; i++) {
                    RandomRestart game = new RandomRestart(numberOfQueens); //class object
                    int[] results = game.runRandomRestartHCSearchWithSidewayMoves();
                    RandomRestartWithSideways[0] += results[0];
                    RandomRestartWithSideways[1] += results[1];
                    RandomRestartWithSideways[2] += results[2];
                }
                System.out.println("\n -------------------------------------------------------------------------------- ");
                System.out.println("Random Restart Hill Climbing Search with Sideways Move");
                System.out.println("Number of Queens: " + numberOfQueens);
                System.out.println("Number of Iterations: " + numOfRuns);
                System.out.println("Success Rate: " + format.format((RandomRestartWithSideways[1] * 100) / (float) numOfRuns) + "%");
                System.out.println("Average Number of Steps when Algorithm Succeeds: " + (RandomRestartWithSideways[0] / numOfRuns));
                if ((RandomRestartWithSideways[2] / (float) numOfRuns) > 0 && (RandomRestartWithSideways[2] / (float) numOfRuns) < 1)
                    System.out.println("Average Number of Restarts when Algorithm Succeeds: ~ 1");
                else
                    System.out.println("Average Number of Restarts when Algorithm Succeeds: " + (RandomRestartWithSideways[2] / (float) numOfRuns));
                break;
        }
    }
}

