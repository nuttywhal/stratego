package edu.asu.stratego.gui.board;

import edu.asu.stratego.game.board.Board;
import javafx.scene.layout.GridPane;

public class BoardEventPane extends GridPane {
    
    /**
     * Creates a new instance of BoardPane.
     * @param board the Stratego board model
     */
    public BoardEventPane(Board board) {
        final int size = 10;
        
        // Initiate piece layer.
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                add(board.getSquare(row, col).getEventPane(), row, col);
            }
        }
    }
}