package com.ReidHansen;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by ReidHansen on 10/1/15.
 *
 */
public class SudokuSolver implements Runnable{
    SudokuBoard sudokuBoard;
    boolean validBoard = true;
    boolean isSolved = false;
    int numIter = 10;
    long count = 0;

    public SudokuSolver(SudokuBoard board) {
        sudokuBoard = board;
        if (!sudokuBoard.checkInput()) {
            System.out.println("Input Board Invalid");
            validBoard = false;
        }
    }

    public static void main(String[] args){
        long startTime = System.nanoTime();
        //Creating the board


        for (int i = 0; i < 1; i++) {
            SudokuBoard board = new SudokuBoard();
            //board.setBoardWithString("070004006002037010100200800300005107050400060006070009091040080800006500020900003");
            //board.setBoardWithString("050000030700000000400000000002000000000000093009300010000000720000052000500000000");
            //board.setBoardWithString("050400000000000000030000000003000000600000000200600000068700030900560000000000420");
            board.PrintBoard();
            System.out.println();

            SudokuSolver solver = new SudokuSolver(board);
            solver.solve();
            solver.sudokuBoard.PrintBoard();
        }


//        ArrayList<SudokuBoard> boards = SudokuBoard.getBoardsFromFile("boards.txt");
//        ArrayList<SudokuBoard> flippedBoards = SudokuBoard.flipTransformBoards(boards);
//        System.out.println("Solving " + flippedBoards.size() + " Sudoku Boards");
//        int count = 0;


//        for (SudokuBoard board : flippedBoards) {
//            long solveStartTime = System.nanoTime();
//            SudokuSolver solver = new SudokuSolver(board);
//            solver.solve();
//            long solveEndTime = System.nanoTime();
//            System.out.println("Solved Board #" + count + " in " + ((solveEndTime - solveStartTime) / 1000000000.0) + " seconds");
//            count += 1;
//        }


//        for (int i = 0; i < 6; i++) {
//            SudokuSolver solver = new SudokuSolver(new SudokuBoard());
//            Thread newThread = new Thread(solver, "My Thread " + i);
//            newThread.start();
//        }


        long endTime = System.nanoTime();
        long total = endTime - startTime;
        System.out.println("Running time was: " + (total / 1000000000.0) + " seconds");


    }


    public boolean solve(){
        //boolean isSolved = solve(0);
        boolean isSolved = solveSmart(0);
        if (!isSolved) System.out.println("Failed to find solution");
        return isSolved;
    }


    public boolean solve(int index) {
        if (isSolved) return true;

        int x = index % sudokuBoard.boardDim;
        int y = index / sudokuBoard.boardDim;
        int currentVal = sudokuBoard.board.get(y).get(x);

        boolean isValid = sudokuBoard.CheckNumber(x, y);

        if (isValid) {
            if (index + 1 < sudokuBoard.boardDim * sudokuBoard.boardDim){
                if (solve(index+ 1 )) return true;
            } else {
                isSolved = true;
                return true;
            }
        }
        else {
            for (int i = currentVal; i <= sudokuBoard.boardDim; i++) {
                sudokuBoard.board.get(y).set(x, i);
                count += 1;
                if (sudokuBoard.CheckNumber(x, y)) {
                    if (index + 1 < sudokuBoard.boardDim * sudokuBoard.boardDim) {
                        if (solve(index + 1)) return true;
                    } else {
                        isSolved = true;
                        return true;
                    }
                }
            }
            // Failed to find a number to fit here
            sudokuBoard.board.get(y).set(x, 0);
        }
        return false;
    }

    public boolean solveSmart(int index){
        if (!(index < sudokuBoard.boardDim * sudokuBoard.boardDim)) return false;
        if (isSolved) return true;



        int x = index % sudokuBoard.boardDim;
        int y = index / sudokuBoard.boardDim;



        LinkedList<Integer> values = updateValidPoints(new Point(x, y)).get(new Point(x,y));
        if (values.isEmpty()) return false;
        System.out.println(values + " " + index + "\n");
        for (Integer nextValue : values) {
            sudokuBoard.board.get(y).set(x, nextValue);
            count += 1;
            if (index + 1 < sudokuBoard.boardDim * sudokuBoard.boardDim) {
                if (solveSmart(index + 1)) return true;
            } else {
                isSolved = true;
                return true;
            }
        }
        // Failed to find a number to fit here
        sudokuBoard.board.get(y).set(x, 0);
        return false;
    }

    private HashMap<Point, LinkedList<Integer>> updateValidPoints(Point startingPoint){
        HashMap<Point, LinkedList<Integer>> validPoints = new HashMap<>();
        return updateValidPoints(startingPoint, validPoints);
    }

        private HashMap<Point, LinkedList<Integer>> updateValidPoints(Point startingPoint, HashMap<Point, LinkedList<Integer>> validPoints){
        boolean hasChanged = false;
        for (int y = startingPoint.y; y < sudokuBoard.boardDim; y++) {
            for (int x = startingPoint.x; x < sudokuBoard.boardDim; x++) {

                Point currentPoint = new Point(x,y);
                if (sudokuBoard.givenNumbers.get(currentPoint)){
                    int currentValue = sudokuBoard.board.get(y).get(x);
                    LinkedList<Integer> tempList = new LinkedList<>();
                    tempList.add(sudokuBoard.board.get(y).get(x));
                    if (validPoints.containsKey(currentPoint)){
                        if(validPoints.get(currentPoint).size() > 1) {
                            hasChanged = true;
                        }
                    }
                    validPoints.put(currentPoint, tempList);
                    continue; // Skip this number if it is correct.
                }

                if (validPoints.containsKey(currentPoint)){

                    // Now check all the points within the updatedPoints linked list to see if they are all still valid.
                    int currentValue = sudokuBoard.board.get(y).get(x);
                    LinkedList<Integer> currentPoints = new LinkedList<>();
                    currentPoints.addAll(validPoints.get(currentPoint));
                    for (Integer testValue : currentPoints) {
                        if (!containedInRow(currentPoint, testValue, validPoints) || !containedInColumn(currentPoint, testValue, validPoints)
                                || !containedInBlock(currentPoint, testValue, validPoints)){
                            LinkedList<Integer> singleItemList = new LinkedList<>();
                            singleItemList.add(testValue);
                            if (validPoints.size() > 1) hasChanged = true;
                            validPoints.replace(currentPoint, singleItemList);
                            continue;
                        } // End of contains if statement

                        sudokuBoard.board.get(y).set(x, testValue);
                        boolean isValid = sudokuBoard.CheckNumber(x,y);
                        // If there is a contradiction somewhere
                        if (!isValid) {
                            validPoints.get(currentPoint).remove(testValue);
                        }
                    }

                    // return the board to state before testing
                    sudokuBoard.board.get(y).set(x, currentValue);
                }else { // if the valid points have not been set yet for the current point.
                    LinkedList<Integer> tempList = new LinkedList<>();
                    for (int i = 1; i < 10; i++) {
                        tempList.add(i);
                    }
                    validPoints.put(currentPoint, tempList);
                    hasChanged = true;
                }
            }
        }
            //if (hasChanged) return updateValidPoints(startingPoint, validPoints);
            return validPoints;
    }

    private boolean containedInRow(Point testPoint, int testValue, HashMap<Point, LinkedList<Integer>> validPoints){
        // Test current row.
        int x = testPoint.x;
        int y = testPoint.y;

        for (int rowCounter = 0; rowCounter < sudokuBoard.boardDim; rowCounter++) {
            if (!validPoints.containsKey(new Point(rowCounter,y))) return true;
            if (validPoints.get(new Point(rowCounter, y)).contains(testValue) && rowCounter != x) return true;
        }
        return false;
    }

    private boolean containedInColumn(Point testPoint, int testValue, HashMap<Point, LinkedList<Integer>> validPoints){
        int x = testPoint.x;
        int y = testPoint.y;
        // Test current column.
        for (int columnCounter = 0; columnCounter < sudokuBoard.boardDim; columnCounter++){
            Point tempPoint = new Point(x, columnCounter);
            if (!validPoints.containsKey(tempPoint)) return true;
            if (validPoints.get(tempPoint).contains(testValue) && columnCounter != y) return true;
        }
        return false;
    }

    private boolean containedInBlock(Point testPoint, int testValue, HashMap<Point, LinkedList<Integer>> validPoints){
        int i = testPoint.x;
        int j = testPoint.y;

        for (int y = j - j % sudokuBoard.blockDim; y < j + sudokuBoard.blockDim - j % sudokuBoard.blockDim; y++){
            for(int x = i - i % sudokuBoard.blockDim; x < i + sudokuBoard.blockDim - i % sudokuBoard.blockDim; x++){
                Point tempPoint = new Point(x,y);
                if (!validPoints.containsKey(tempPoint)) return true;
                if(validPoints.get(tempPoint).contains(testValue) && x != i && y != j) return true;
            }
        }
        return false;
    }


    @Override
    public void run() {
        for (int i = 0; i < numIter; i++){
            Random randomGenerator = new Random();
            SudokuBoard board = new SudokuBoard();
            board.generateRandomBoard(10, randomGenerator);
        }
    }

}