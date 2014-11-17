package edu.asu.stratego.game;

import java.io.Serializable;

import edu.asu.stratego.game.board.ClientBoard;

public class SetupBoard implements Serializable {
    
    private static final long serialVersionUID = 1854992492401962054L;
    private PieceType[][] positions = new PieceType[4][10];
    
    /**
     * Store the player's initial piece positions in positions[][].
     */
    public void getPiecePositions() {
        ClientBoard board = Game.getBoard();
        for (int row = 6; row < 10; ++row) {
            for (int col = 0; col < 10; ++col)
                positions[row - 6][col] = board.getSquare(row, col)
                                               .getPiece()
                                               .getPieceType();
        }
    }
    
    /**
     * @return initial positions
     */
    public PieceType[][] positions() {
        return positions;
    }
    
    /**
     * @param row row index of positions[][]
     * @param col column index of positions[][]
     * @return the PieceType at positions[row][col]
     */
    public PieceType getPiece(int row, int col) {
        return positions[row][col];
    }
}