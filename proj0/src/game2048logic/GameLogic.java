package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int result = 0;//avoid double merging
        for(int i = r - 1;i >= 0;i--){
            if(board[i][c] != 0 ){       //for merging the tile
                if(board[i][c] == board[i + 1][c] && i >= minR && result == 0){
                    board[i][c] *= 2;
                    board[i + 1][c] = 0;
                    result = i + 1;
                }
            }
            if(board[i][c] == 0 && i >= minR){       //for moving up the tile
                board[i][c] = board[i + 1][c];
                board[i + 1][c] = 0;
            }
        }
        return result;

    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        int result = 0;
        for(int i = 0; i < board.length; i++){
            if(result == 0){
                result = moveTileUpAsFarAsPossible(board, i, c, 0);
            }else{
                moveTileUpAsFarAsPossible(board, i, c, result);
            }
            //result = moveTileUpAsFarAsPossible(board, i, c, result);
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        for(int i = 0; i < board.length; i++){
            tiltColumn(board, i);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        } else if (side == Side.SOUTH) {
            rotateRight(board);
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            rotateLeft(board);
        } else {
            tiltUp(board);
        }
    }
}
