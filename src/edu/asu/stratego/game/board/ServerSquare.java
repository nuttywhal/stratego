package edu.asu.stratego.game.board;

import edu.asu.stratego.game.Piece;

/**
 * Represents an individual square of a Stratego board.
 */
public class ServerSquare {

    private Piece piece = null;

    /**
     * @return the piece
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
}