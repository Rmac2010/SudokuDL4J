package com.ReidHansen;

import SudokuBoardFactory.SudokuBoardFactory;
import SudokuBoardFactory.SudokuBoard;

/**
 * Created by ReidHansen on 12/29/15.
 */
public class SudokuTester {

    public static void main(String[] args){
        String boardString = "070004006002037010100200800300005107050400060006070009091040080800006500020900003";
        //String boardString = "050000030700000000400000000002000000000000093009300010000000720000052000500000000";

        SudokuBoardFactory boardFactory = new SudokuBoardFactory();
        SudokuBoard sudokuBoard = boardFactory.getSudokuBoard(0,boardString);
        System.out.println("Original Board:");
        sudokuBoard.printBoard();
        boolean isSolved = sudokuBoard.solve();
        if (isSolved){
            System.out.println("\n\nSolved Board:");
            sudokuBoard.printBoard();
            System.out.println("\nNumbers were inserted into the board " + sudokuBoard.getNumSteps()+ " times");

        } else{
            System.out.println("Failed to find a solution!");
        }
    }

}
