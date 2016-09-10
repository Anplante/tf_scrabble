package ca.qc.bdeb.p56.scrabble.model;

/**
 * Created by TheFrenchOne on 9/7/2016.
 */
public class Board {

    public static final int BOARD_SIZE = 15;

    Tile[][] board;


    public Board() {
        InitBoard();
    }


    private void InitBoard() {

        board = new Tile[BOARD_SIZE][BOARD_SIZE];


        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                board[row][column] = new Tile(Character.MIN_VALUE, row, column);
                Tile square = board[row][column];
            }
        }
    }


    public Tile getTile(int row, int column) {
        return board[row][column];
    }


}
