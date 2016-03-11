package SudokuBoardFactory;

import java.util.LinkedList;

/**
 * Created by ReidHansen on 12/28/15.
 */
class SudokuBoard9x9 implements SudokuBoard {

    private final int boardSize = 9;
    private final int blockSize = 3;
    private int board[][] = new int[boardSize][boardSize];

    // numSteps keep tracks of the number of times a number is put into a square. It is incremented everytime a number is popped off of the
    // linkedlist containing valid numbers for a specific square.
    private int numSteps = 0;

    public int getNumSteps() {
        return numSteps;
    }

    @Override
    public int[][] getBoard() {
        return board;
    }

    public boolean createdSuccessfully = false;

    SudokuBoard9x9() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = 0;
            }
        }
    }

    SudokuBoard9x9(String input) {
        if (setBoardWithString(input)){
            createdSuccessfully = true;

        } else createdSuccessfully = false;
    }

    SudokuBoard9x9(int[][] board) {
        if (board.length != boardSize) {
            System.out.println("Incorrect Input Size for Sudoku Board Type");
            return;
        }
        for (int[] ints : board) {
            if (ints.length != boardSize) {
                System.out.println("Incorrect Input Size for Sudoku Board Type");
                return;
            }
        }
        this.board = board;
        createdSuccessfully = true;
    }

    private LinkedList<Integer> getValidNumbersForCell(int row, int column) {
        LinkedList<Integer> validNumbers = new LinkedList<>();

        for (int i = 1; i < 10; i++) {
            board[row][column] = i;
            if (checkNumber(row, column)) {
                //System.out.println("Found a valid number: " + i);
                validNumbers.add(i);
            }
        }
        board[row][column] = 0;
        return validNumbers;
    }

    @Override
    public boolean solve() {
        return solve(0);
    }

    private boolean solve(int index) {
        if (index > boardSize * boardSize - 1)
            return true;   // Stepped outside of the range which means the last cell is
        // valid and the whole board is valid.
        int column = index % boardSize;
        int row = index / boardSize;

        if (board[row][column] == 0) {
            // The current space has a value of 0 -> This space was not a given
            LinkedList<Integer> validNumbers = getValidNumbersForCell(row, column);
            while (!validNumbers.isEmpty()) {              // While there is still something in the list -- The list starts with at most 9 items
                board[row][column] = validNumbers.pop();  // Set the value of board[row][column] to one of the valid numbers
                numSteps++;
                if (solve(index + 1)) {
                    return true;                          // Return true if solve(index + 1) returns true
                }
            }                                             // Otherwise keep popping items off of the list

            board[row][column] = 0;                       // Reset the value of board[row][column] to zero since we did not find a solution
            return false;                                 // Return false is none of the items in validNumbers resulted in a solution

        }                                                 // End of: if (board[row][column] == 0)

        /* The following code is only reachable if the value of board[row][column] did not start out as zero -- It therefore was a given value */
        else return solve(index + 1);
    }

    @Override
    public boolean checkInput() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (!checkNumber(row, column)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkNumber(int row, int column) {
        int testNum = board[row][column];

        // Test current row.
        for (int rowCounter = 0; rowCounter < boardSize; rowCounter++) {
            if (board[rowCounter][column] == testNum && rowCounter != row) {
                //System.out.println("The following number is already in the row: " + board[rowCounter][column]);
                return false;
            }
        }

        // Test current column.
        for (int columnCounter = 0; columnCounter < boardSize; columnCounter++) {
            if (board[row][columnCounter] == testNum && columnCounter != column) {
                //System.out.println("The following number is already in the column: " + board[row][columnCounter]);
                return false;
            }
        }

        // Test current block.
        for (int y = row - row % blockSize; y < row + blockSize - row % blockSize; y++) {
            for (int x = column - column % blockSize; x < column + blockSize - column % blockSize; x++) {
                if (x == column && y == row) continue; // We don't want to compare testNum against itself
                if (board[y][x] == testNum) {
                    //System.out.println("The following number is already in the block: " + board[row][column]);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean createdSuccessfully() {
        return createdSuccessfully;
    }


    private boolean setBoardWithString(String input) {
        if (input.length() < boardSize * boardSize || input.length() > boardSize * boardSize) {
            System.out.println("Error: Inappropriate board input for 9x9 Sudoku Board");
            return false;
        }
        String[] splitString = input.split("");
        for (int i = 0; i < splitString.length; i++) {
            try {
                this.board[i / boardSize][i % boardSize] = Integer.parseInt(splitString[i]);
            } catch (Exception e){
                System.out.println("Error while parsing board string: " + e);
                return false;
            }
        }
        return true;
    }


    @Override
    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (j % blockSize == 0 && j != 0) System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            if ((i + 1) % blockSize == 0 && i != 8) {
                System.out.println();
                for (int c = 0; c < boardSize - 2; c++) {
                    System.out.print("- -");
                }

            }
            System.out.println();
        }

    }

}
