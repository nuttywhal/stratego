package edu.asu.stratego.game.board;

import edu.asu.stratego.game.Piece;
import edu.asu.stratego.gui.board.BoardSquareEventPane;
import edu.asu.stratego.gui.board.BoardSquarePane;
import edu.asu.stratego.gui.board.BoardSquareType;

/**
 * Represents an individual square of a Stratego board.
 */
public class ClientSquare {

    private Piece piece = null;
    private BoardSquarePane piecePane;
    private BoardSquareEventPane eventPane;
    
    /**
     * Creates a new instance of Square.
     * @param type the square background image
     */
    public ClientSquare(BoardSquareType type) {
        piecePane = new BoardSquarePane(type);
        eventPane = new BoardSquareEventPane();
    }
    
    /**
     * @return the BoardSquarePane associated with this Square
     */
    public BoardSquarePane getPiecePane() {
        return piecePane;
    }

    /**
     * @return the piece at this square.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * @return the BoardSquareEventPane associated with this Square
     */
    public BoardSquareEventPane getEventPane() {
        return eventPane;
    }
}