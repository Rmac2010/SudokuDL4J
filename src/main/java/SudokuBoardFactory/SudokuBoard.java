package SudokuBoardFactory;

/**
 * Created by ReidHansen on 12/28/15.
 * Interface to be used for all Sudoku Board Types.
 */
    public interface SudokuBoard {

    boolean solve();
    int getNumSteps();

    boolean checkInput();
    boolean checkNumber(int row, int column);
    void printBoard();

}
