spackage edu.asu.stratego.gui.board;

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
        
        // Initiate piece layer.
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                add(board.getSquare(row, col).getPiecePane(), row, col);
            }
        }
        
        // Add the two lakes.
        board.getSquare(2, 4).getPiecePane().setPiece(ImageConstants.LAKE_1_1);
        board.getSquare(3, 4).getPiecePane().setPiece(ImageConstants.LAKE_1_2);
        board.getSquare(2, 5).getPiecePane().setPiece(ImageConstants.LAKE_1_3);
        board.getSquare(3, 5).getPiecePane().setPiece(ImageConstants.LAKE_1_4);
        
        board.getSquare(6, 4).getPiecePane().setPiece(ImageConstants.LAKE_2_1);
        board.getSquare(7, 4).getPiecePane().setPiece(ImageConstants.LAKE_2_2);
        board.getSquare(6, 5).getPiecePane().setPiece(ImageConstants.LAKE_2_3);
        board.getSquare(7, 5).getPiecePane().setPiece(ImageConstants.LAKE_2_4);
    }
}