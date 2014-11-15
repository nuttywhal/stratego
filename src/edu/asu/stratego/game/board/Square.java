package edu.asu.stratego.game.board;

import edu.asu.stratego.game.Piece;
import edu.asu.stratego.gui.board.BoardSquareEventPane;
import edu.asu.stratego.gui.board.BoardSquarePane;
import edu.asu.stratego.gui.board.BoardSquareType;

/**
 * Represents an individual square of a Stratego board.
 */
public class Square {

    private Piece piece = null;
    private BoardSquarePane piecePane;
    private BoardSquareEventPane eventPane;
    
    private int row;
    private int col;
    
    /**
     * Creates a new instance of Square.
     * @param type the square background image
     */
    public Square(BoardSquareType type) {
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
    
    /**
     * @return the row index of this Square (indexing starts at 0)
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row index of this Square (indexing starts at 0)
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column index of this Square (indexing starts at 0)
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the column index of this Square (indexing starts at 0)
     */
    public void setCol(int col) {
        this.col = col;
    }
}