//package com.ReidHansen;
//
///**
// * Created by ReidHansen on 10/1/15.
// *
// */
//import java.awt.*;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class SudokuBoard {
//    public ArrayList<ArrayList<Integer>> board;
//    public int boardDim = 9;
//    public int blockDim = 3;
//    public Map<Point, Boolean> givenNumbers;
//
//
//
//    public SudokuBoard() {
//        givenNumbers = new HashMap<>();
//        board = new ArrayList<>();
//        for (int j = 0; j < boardDim; j++){
//            ArrayList<Integer> row = new ArrayList<>();
//            for (int i = 0; i < boardDim; i++) {
//                row.add(0);
//                givenNumbers.put(new Point(i,j), false);
//            }
//            board.add(row);
//        }
//    }
//
//    public SudokuBoard clone(SudokuBoard sourceBoard){
//        SudokuBoard destBoard = new SudokuBoard();
//
//        for (Point givenPoint : sourceBoard.givenNumbers.keySet()) {
//            destBoard.addGivenNumber(givenPoint.x, givenPoint.y, sourceBoard.board.get(givenPoint.y).get(givenPoint.x));
//        }
//
//        return destBoard;
//    }
//
//
//
//    public boolean checkInput(){
//        for (int j = 0; j < boardDim; j++){
//            for (int i = 0; i < boardDim; i++) {
//                if (!checkInputNumber(i, j)) return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean checkInputNumber(int i, int j){
//        int testNum = board.get(j).get(i);
//        if (testNum == 0) return true;
//        // Test current row.
//        for (int rowCounter = 0; rowCounter < boardDim; rowCounter++) {
//            if (board.get(j).get(rowCounter) == testNum && rowCounter != i) return false;
//        }
//
//        // Test current column.
//        for (int columnCounter = 0; columnCounter < boardDim; columnCounter++){
//            if (board.get(columnCounter).get(i) == testNum && columnCounter != j) return false;
//        }
//
//        // Test current block.
//        for (int y = j - j % blockDim; y < j + blockDim - j % blockDim; y++){
//            for(int x = i - i % blockDim; x < i + blockDim - i % blockDim; x++){
//                if(board.get(y).get(x) == testNum && x != i && y != j) return false;
//            }
//        }
//
//        return true;
//
//    }
//
//    public void setBoardWithString(String input){
//        String[] splitString = input.split("");
//        for (int i = 0; i < splitString.length; i++){
//            if (!splitString[i].equalsIgnoreCase("0")){
//                addGivenNumber(i % boardDim, i / boardDim, Integer.parseInt(splitString[i]));
//            }
//        }
//
//    }
//
//    public static ArrayList<SudokuBoard> getBoardsFromFile(String fileName){
//        ArrayList<SudokuBoard> boards = new ArrayList<>();
//
//        try {
//            for (String line : Files.readAllLines(Paths.get(fileName))){
//                SudokuBoard tempBoard = new SudokuBoard();
//                tempBoard.setBoardWithString(line);
//                boards.add(tempBoard);
//            }
//            return boards;
//        } catch (IOException e) {
//            System.out.println("Failed to read from file: " + fileName + " exception of: " + e);
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public void addGivenNumber(int x, int y, int value){
//        board.get(y).set(x, value);
//        givenNumbers.put(new Point(x,y), true);
//    }
//
//    public void addNonGivenNumber(int x, int y, int value){
//        board.get(y).add(x, value);
//        givenNumbers.put(new Point(x,y), false);
//    }
//
//    boolean CheckBoard(){
//        for (int j = 0; j < boardDim; j++){
//            for (int i = 0; i < boardDim; i++) {
//                if (!CheckNumber(i,j)) return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean CheckNumber(int i, int j){
//        int testNum = board.get(j).get(i);
//        if (givenNumbers.get(new Point(i,j))) return true;
//        if (testNum == 0) return false;
//        if (testNum > boardDim) return false;
//
//        // Test current row.
//        for (int rowCounter = 0; rowCounter < boardDim; rowCounter++) {
//            if (board.get(j).get(rowCounter) == testNum && rowCounter != i) return false;
//        }
//
//        // Test current column.
//        for (int columnCounter = 0; columnCounter < boardDim; columnCounter++){
//            if (board.get(columnCounter).get(i) == testNum && columnCounter != j) return false;
//        }
//
//        // Test current block.
//        for (int y = j - j % blockDim; y < j + blockDim - j % blockDim; y++){
//            for(int x = i - i % blockDim; x < i + blockDim - i % blockDim; x++){
//                if(board.get(y).get(x) == testNum && x != i && y != j) return false;
//            }
//        }
//
//        return true;
//    }
//
//    public void PrintBoard(){
//        for (int j = 0; j < boardDim; j++) {
//            System.out.println(board.get(j));
//        }
//    }
//
//    public void printBoardString(){
//        for (int j = 0; j < boardDim; j++){
//            for (int i = 0; i < boardDim; i++) {
//                System.out.print(Integer.toString(board.get(j).get(i)));
//            }
//        }
//        System.out.println();
//    }
//
//
//    public void generateRandomBoard(int numBoards, Random randGenerator){
//        //Random randGenerator = new Random();
//        int randNumGiven = randGenerator.nextInt(17) + 17; //Generates random numbers between 17 and 33.
//        int numBoardsFound = 0;
//        int countBoards = 0;
//        while (numBoardsFound < numBoards){
//            int count = 0;
//            SudokuBoard tempBoard = new SudokuBoard();
//
//            int numFound = 0;
//            while (numFound < randNumGiven) {
//                int randLocationX = randGenerator.nextInt(9);
//                int randLocationY = randGenerator.nextInt(9);
//                int randValue = randGenerator.nextInt(9) + 1;
//
//                tempBoard.addGivenNumber(randLocationX, randLocationY, randValue);
//                tempBoard.givenNumbers.replace(new Point(randLocationX, randLocationY), false);
//                if (!tempBoard.CheckNumber(randLocationX,randLocationY)){
//                    tempBoard.board.get(randLocationY).set(randLocationX, 0);
//                } else{
//                    tempBoard.givenNumbers.replace(new Point(randLocationX, randLocationY), true);
//                    numFound += 1;
//                }
//                count += 1;
//                if (count > 1000) return;
//            }
//            //tempBoard.PrintBoard();
//            SudokuBoard returnBoard = clone(tempBoard);
//            SudokuSolver tempSolver = new SudokuSolver(tempBoard);
//            if (tempSolver.solve()){
//                returnBoard.printBoardString();
//                numBoardsFound += 1;
//            }
//            countBoards += 1;
//            if (countBoards > 10 * numBoards) break;
//
//        }
//    }
//
//    public static ArrayList<SudokuBoard> flipTransformBoards(ArrayList<SudokuBoard> boards){
//        ArrayList<SudokuBoard> flippedBoards = new ArrayList<>();
//        for (SudokuBoard sudokuBoard : boards) {
//            SudokuBoard tempBoard = new SudokuBoard();
//            for (int x = 0; x < tempBoard.boardDim; x++){
//                for (int y = 0; y < tempBoard.boardDim; y++){
//                    int value = sudokuBoard.board.get(y).get(x);
//                    if (value != 0)tempBoard.board.get(x).set(y, value);
//                }
//            }
//            flippedBoards.add(tempBoard);
//        }
//        return flippedBoards;
//    }
//}
