package com.ReidHansen;

import SudokuBoardFactory.SudokuBoardFactory;
import SudokuBoardFactory.SudokuBoard;

/**
 * Created by ReidHansen on 12/29/15.
 */
public class SudokuTester {

    public static void main(String[] args){
        //String boardString = "070004006002037010100200800300005107050400060006070009091040080800006500020900003"; // Found in local newspaper - 1152 moves.
        String boardString = "800000000003600000070090200050007000000045700000100030001000068008500010090000400"; // Supposedly a really hard one - 49558 moves.
        //String boardString =   "100007090030020008009600500005300900010080002600004000300000010040000007007000300"; // Supposedly hard puzzle - 8969 moves.
        //String boardString = "000002750018090000000000000490000000030000008000700200000030009700000000500000080"; // Only 17 givens (The smallest proven needed) - 1,093,820 moves


        SudokuBoardFactory boardFactory = new SudokuBoardFactory();
        SudokuBoard sudokuBoard = boardFactory.getSudokuBoard(0,boardString);
        System.out.println("Original Board:");
        sudokuBoard.printBoard();
        long startTime = System.currentTimeMillis();
        boolean isSolved = sudokuBoard.solve();
        long endTime = System.currentTimeMillis();
        long solveTime = endTime - startTime;
        if (isSolved){
            System.out.println("\n\nSolved Board:");
            sudokuBoard.printBoard();
            System.out.println("\nNumbers were inserted into the board " + sudokuBoard.getNumSteps()+ " times");
            System.out.println("It took: " + solveTime * 0.001 + " seconds to solve");

        } else{
            System.out.println("Failed to find a solution!");
        }
    }

}
