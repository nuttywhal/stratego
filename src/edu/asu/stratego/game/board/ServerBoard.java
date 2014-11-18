package edu.asu.stratego.game.board;

/**
 * Representation of a Stratego board.
 */
public class ServerBoard {

    private final int SIZE = 10;
    private ServerSquare[][] squares;
    
    /**
     * Creates a new instance of Board.
     */
    public ServerBoard() {
        squares = new ServerSquare[SIZE][SIZE];
        
        for (int row = 0; row < SIZE; ++row) {
            for (int col = 0; col < SIZE; ++col) {
                squares[row][col] = new ServerSquare();
            }
        }
    }
    
    /**
     * Returns the board square located at (row, col).
     * 
     * @param row board square row
     * @param col board square column
     * @return the square located at (row, col)
     */
    public ServerSquare getSquare(int row, int col) {
        return squares[row][col];
    }
}