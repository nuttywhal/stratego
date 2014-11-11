package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.board.Board;
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
    }
}