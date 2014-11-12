package edu.asu.stratego.game.board;

import edu.asu.stratego.game.Piece;
import edu.asu.stratego.gui.board.BoardSquarePane;
import edu.asu.stratego.gui.board.BoardSquareType;

/**
 * Represents an individual square of a Stratego board.
 */
public class Square {

    private Piece piece = null;
    private BoardSquarePane pane;
    
    /**
     * Creates a new instance of Square.
     * @param type the square background image
     */
    public Square(BoardSquareType type) {
        pane = new BoardSquarePane(type);
    }
    
    /**
     * Returns the Square's BoardSquarePane.
     * @return BoardSquarePane belonging to the Square
     */
    public BoardSquarePane getPane() {
        return pane;
    }

    /**
     * Returns the piece at this square.
     * @return the piece at this square.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets the piece at this square.
     * @param piece the piece to set
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}