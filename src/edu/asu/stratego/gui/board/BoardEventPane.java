package edu.asu.stratego.gui.board;

import javafx.scene.layout.GridPane;

import edu.asu.stratego.game.board.ClientBoard;

/**
 * Layered directly on top of the BoardPane. Allows the player to interact with 
 * the board.
 */
public class BoardEventPane extends GridPane {
    
    /**
     * Creates a new instance of BoardPane.
     * @param board the Stratego board model
     */
    public BoardEventPane(ClientBoard board) {
        final int size = 10;
        
        // Initiate piece layer.
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                BoardSquareEventPane square = board.getSquare(row, col).getEventPane();
                
                add(square, col, row);
                GridPane.setRowIndex(square.getHover(), row);
                GridPane.setColumnIndex(square.getHover(), col);
            }
        }
    }
}