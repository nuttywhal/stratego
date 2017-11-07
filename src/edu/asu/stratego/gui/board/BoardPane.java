package edu.asu.stratego.gui.board;

import javafx.scene.layout.GridPane;

import edu.asu.stratego.game.board.ClientBoard;
import edu.asu.stratego.media.ImageConstants;

/**
 * A graphical representation of the Stratego board.
 */
public class BoardPane extends GridPane {
    
    /**
     * Creates a new instance of BoardPane.
     * @param board the Stratego board model
     */
    public BoardPane(ClientBoard board) {
        final int size = 10;
        
        // Initiate board pane.
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                add(board.getSquare(row, col).getPiecePane(), col, row);
            }
        }
        
        // Add the two lakes.
        board.getSquare(4, 2).getPiecePane().setPiece(ImageConstants.LAKE_1_1);
        board.getSquare(4, 3).getPiecePane().setPiece(ImageConstants.LAKE_1_2);
        board.getSquare(5, 2).getPiecePane().setPiece(ImageConstants.LAKE_1_3);
        board.getSquare(5, 3).getPiecePane().setPiece(ImageConstants.LAKE_1_4);
        
        board.getSquare(4, 6).getPiecePane().setPiece(ImageConstants.LAKE_2_1);
        board.getSquare(4, 7).getPiecePane().setPiece(ImageConstants.LAKE_2_2);
        board.getSquare(5, 6).getPiecePane().setPiece(ImageConstants.LAKE_2_3);
        board.getSquare(5, 7).getPiecePane().setPiece(ImageConstants.LAKE_2_4);
    }
}