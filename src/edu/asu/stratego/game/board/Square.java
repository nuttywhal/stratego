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
     * Returns the Square's BoardSquarePane.
     * @return BoardSquarePane belonging to the Square
     */
    public BoardSquarePane getPiecePane() {
        return piecePane;
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

    /**
     * @return the eventPane
     */
    public BoardSquareEventPane getEventPane() {
        return eventPane;
    }
    
    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }
}