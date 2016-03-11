package com.ReidHansen;

import SudokuBoardFactory.SudokuBoardFactory;
import SudokuBoardFactory.SudokuBoard;
import scala.util.parsing.combinator.testing.Str;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ReidHansen on 12/29/15.
 */
public class SudokuTester {

    public static void main(String[] args){
        //String boardString = "070004006002037010100200800300005107050400060006070009091040080800006500020900003"; // Found in local newspaper - 1152 moves.
        //String boardString = "800000000003600000070090200050007000000045700000100030001000068008500010090000400"; // Supposedly a really hard one - 49558 moves.
        //String boardString =   "100007090030020008009600500005300900010080002600004000300000010040000007007000300"; // Supposedly hard puzzle - 8969 moves.
        //String boardString = "000002750018090000000000000490000000030000008000700200000030009700000000500000080"; // Only 17 givens (The smallest proven needed) - 1,093,820 moves

        ArrayList<String> minimumBoards = readBoardsFromFile("minimumBoards.txt");
        HashMap<String, int[][]> solvedBoardsHash = new HashMap<>();
        HashMap<String, Integer> boardStepsHash = new HashMap<>();

        int boardLimit = -1; // Set to -1 if you want to solve all boards
        int count = 0;
        
        for (String boardString : minimumBoards) {
            if (count >= boardLimit && boardLimit != -1) break;
            SudokuBoardFactory boardFactory = new SudokuBoardFactory();
            SudokuBoard sudokuBoard = boardFactory.getSudokuBoard(0, boardString);
            if (sudokuBoard.createdSuccessfully()) {
                long startTime = System.currentTimeMillis();

                boolean isSolved = sudokuBoard.solve();

                long endTime = System.currentTimeMillis();
                long solveTime = endTime - startTime;
                if (isSolved) {
                    count += 1;
                    System.out.println("Board " + count + " Solved: " + solveTime * 0.001);
                    solvedBoardsHash.put(boardString, sudokuBoard.getBoard());
                    boardStepsHash.put(boardString, sudokuBoard.getNumSteps());
                } else {
                    System.out.println("Failed to find a solution!");
                }
            }
        }

        int largestSteps = 0;
        String hardestBoard = "";

        int shortestSteps = -1;
        String easiestBoard = "";

        double totalSteps = 0;

        for (String board : boardStepsHash.keySet()){
            int boardSteps = boardStepsHash.get(board);
            totalSteps += boardSteps;

            if (boardSteps > largestSteps) {
                largestSteps = boardSteps;
                hardestBoard = board;
            }

            if (boardSteps < shortestSteps || shortestSteps == -1){
                shortestSteps = boardSteps;
                easiestBoard = board;
            }
        }

        System.out.println("-----------------------------------------------------------\n");
        System.out.println("Number of boards solved: " + solvedBoardsHash.keySet().size());
        System.out.println("Average number of steps: " + totalSteps / solvedBoardsHash.keySet().size());


        SudokuBoardFactory factory = new SudokuBoardFactory();
        SudokuBoard easiestBoardSolution = factory.getSudokuBoard(0, solvedBoardsHash.get(easiestBoard));
        SudokuBoard hardestBoardSolution = factory.getSudokuBoard(0, solvedBoardsHash.get(hardestBoard));

        System.out.println("\nEasiest board to solve (" + shortestSteps + " Steps):\n" + easiestBoard + "\n");
        System.out.println("Solution to easiest board:\n");
        easiestBoardSolution.printBoard();


        System.out.println("\nHardest board to solve (" + largestSteps + " Steps):\n" + hardestBoard + "\n");
        System.out.println("Solution to hardest board:\n");
        hardestBoardSolution.printBoard();
    }


    private static ArrayList<String> readBoardsFromFile(String fileName){
        ArrayList<String> boardsArray = new ArrayList<>();

        try {
            for (String line: Files.readAllLines(Paths.get(fileName))){
                boardsArray.add(line);
            }
        } catch (IOException e){
            System.out.println("Failure to read boards from file: " + fileName + " IOException: " + e);
        }


        return boardsArray;

    }

}
