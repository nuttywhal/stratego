package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.board.Board;
import edu.asu.stratego.media.ImageConstants;
import javafx.scene.layout.GridPane;

/**
 * JavaFX GridPane to graphically represent the Stratego board.
 */
public class BoardPane extends GridPane {
    
    /**
     * Creates a new instance of BoardPane.
     * @param board the Stratego board model
     */
    public BoardPane(Board board) {
        final int size = 10;
        
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                add(board.getSquare(row, col).getPane(), row, col);
            }
        }
        
        // Add the two lakes.
        board.getSquare(2, 4).getPane().setPiece(ImageConstants.lake1_1);
        board.getSquare(3, 4).getPane().setPiece(ImageConstants.lake1_2);
        board.getSquare(2, 5).getPane().setPiece(ImageConstants.lake1_3);
        board.getSquare(3, 5).getPane().setPiece(ImageConstants.lake1_4);
        
        board.getSquare(6, 4).getPane().setPiece(ImageConstants.lake2_1);
        board.getSquare(7, 4).getPane().setPiece(ImageConstants.lake2_2);
        board.getSquare(6, 5).getPane().setPiece(ImageConstants.lake2_3);
        board.getSquare(7, 5).getPane().setPiece(ImageConstants.lake2_4);
    }
}