package ca.qc.bdeb.p56.scrabble.model;

/**
 * Classe qui contient les informations sur le plateau de jeu.
 *
 * Created by Louis Luu Lim on 9/7/2016.
 */
public class Board {

    private Square[][] board;

    public Board(int size) {
        board = new Square[size][size];
        initBoard();
    }

    private void initBoard() {

        for (int row = 0; row < board.length ;row++) {
            for (int column = 0; column <board.length; column++) {
                board[row][column] = new Square(row, column);
            }
        }
        setNeighbours();
    }

    private void setNeighbours()
    {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {

                Square currentSquare = board[row][column];

                Square adjacentUp = (row <= 0) ? null : board[row - 1][column];
                Square adjacentDown = (row >=board.length - 1) ? null : board[row + 1][column];
                Square adjacenLeft = (column <= 0) ? null : board[row][column - 1];
                Square adjacentRight = (column >= board.length- 1) ? null : board[row][column + 1];
                currentSquare.setNeighbours(adjacentUp,adjacentDown, adjacenLeft, adjacentRight);
            }
        }
    }

    public Square getSquare(int row, int column) {
        return board[row][column];
    }
}
