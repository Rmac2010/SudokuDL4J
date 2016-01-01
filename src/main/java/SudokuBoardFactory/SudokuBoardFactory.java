package SudokuBoardFactory;

/**
 * Created by ReidHansen on 12/28/15.
 */
public class SudokuBoardFactory {
    public final int SUDOKU_BOARD_9x9 = 0;

    private SudokuBoard getSudokuBoard(int board, String boardInputString, int[][] boardInputArray){

        switch (board){
            case 0:
                SudokuBoard newBoard;
                if (boardInputString != null){
                    newBoard = new SudokuBoard9x9(boardInputString);
                } else if (boardInputArray != null){
                    newBoard = new SudokuBoard9x9(boardInputArray);
                } else {
                    newBoard = new SudokuBoard9x9();
                }

                return newBoard;

            default:
                System.out.println("Incorrect Input for SudokuBoardFactory");
                return null;
        }
    }

    public SudokuBoard getSudokuBoard(int board, String boardInputString){
        return getSudokuBoard(board, boardInputString, null);
    }

    public SudokuBoard getSudokuBoard(int board, int[][] boardInputArray){
        return getSudokuBoard(board, null, boardInputArray);
    }

    public SudokuBoard getSudokuBoard(int board){
        return getSudokuBoard(board, null, null);
    }

}
