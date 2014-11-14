package edu.asu.stratego.game.board;

import edu.asu.stratego.gui.board.BoardEventPane;
import edu.asu.stratego.gui.board.BoardPane;
import edu.asu.stratego.gui.board.BoardSquareType;

/**
 * Representation of a Stratego board.
 */
public class Board {

    private final BoardPane piecePane;
    private final BoardEventPane eventPane;
    private final int size = 10;
    private Square[][] squares;
    
    /**
     * Creates a new instance of Board.
     */
    public Board() {
        // Initialize the board GUI.
        squares = new Square[size][size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if ((row + col) % 2 == 0)
                    squares[row][col] = new Square(BoardSquareType.DARK);
                else
                    squares[row][col] = new Square(BoardSquareType.LIGHT);
            }
        }
        
        piecePane = new BoardPane(this);
        eventPane = new BoardEventPane(this);
    }
    
    /**
     * Returns the board square located at (row, col).
     * 
     * @param row board square row
     * @param col board square column
     * @return the square located at (row, col)
     */
    public Square getSquare(int row, int col) {
        return squares[row][col];
    }
    
    /**
     * Returns the JavaFX node that graphically represents the board.
     * @return JavaFX BoardPane node of the board
     */
    public BoardPane getPiecePane() {
        return piecePane;
    }
    
    public BoardEventPane getEventPane() {
        return eventPane;
    }
}