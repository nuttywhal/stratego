package edu.asu.stratego.gui.board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.gui.ClientStage;

/**
 * JavaFX rectangle that is layered behind the semi-transparent board border to 
 * indicate to the players whose turn it is. Depending on which player's turn 
 * it is, the rectangle will be colored either red or blue.
 */
public class BoardTurnIndicator {
    
    Rectangle turnIndicator;
    
    /**
     * Creates a new instance of BoardTurnIndicator.
     */
    public BoardTurnIndicator() {
        final int SIDE = ClientStage.getSide();
        turnIndicator = new Rectangle(0, 0, SIDE, SIDE);
        
        // Set the setup game turn color.
        if (Game.getPlayer().getColor() == PieceColor.RED)
            turnIndicator.setFill(new Color(0.48, 0.13, 0.13, 1.0));
        else
            turnIndicator.setFill(new Color(0.22, 0.24, 0.55, 1.0));
    }
    
    /**
     * @return the turn indicator (JavaFX rectangle)
     */
    public Rectangle getTurnIndicator() {
        return turnIndicator;
    }
}