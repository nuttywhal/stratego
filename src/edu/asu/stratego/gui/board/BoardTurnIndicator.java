package edu.asu.stratego.gui.board;

import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.gui.ClientStage;

/**
 * JavaFX rectangle that is layered behind the semi-transparent board border to 
 * indicate to the players whose turn it is. Depending on which player's turn 
 * it is, the rectangle will be colored either red or blue.
 */
public class BoardTurnIndicator {
    
    private static Color red  = new Color(0.48, 0.13, 0.13, 1.0);
    private static Color blue = new Color(0.22, 0.24, 0.55, 1.0);
    
    private static Object turnIndicatorTrigger = new Object();
    private static Rectangle turnIndicator;
    
    /**
     * Creates a new instance of BoardTurnIndicator.
     */
    public BoardTurnIndicator() {
        final int SIDE = ClientStage.getSide();
        turnIndicator = new Rectangle(0, 0, SIDE, SIDE);
        
        // Set the setup game turn color.
        if (Game.getPlayer().getColor() == PieceColor.RED)
            turnIndicator.setFill(red);
        else
            turnIndicator.setFill(blue);
        
        // Start thread to automatically update turn color.
        Thread updateColor = new Thread(new UpdateColor());
        updateColor.setDaemon(true);
        updateColor.start();
    }
    
    /**
     * @return the turn indicator (JavaFX rectangle)
     */
    public static Rectangle getTurnIndicator() {
        return turnIndicator;
    }
    
    /**
     * @return Object used to communicate between the ClientGameManager and the 
     * BoardTurnIndicator to indicate when the player turn color has changed.
     */
    public static Object getTurnIndicatorTrigger() {
        return turnIndicatorTrigger;
    }
    
    private class UpdateColor implements Runnable {
        @Override
        public void run() {
            synchronized (turnIndicatorTrigger) {
                try {
                    while (true) {
                        // Wait for player turn color to change.
                        turnIndicatorTrigger.wait();
                        
                        Platform.runLater(() -> {
                            // Blue -> Red.
                            if (Game.getTurn() == PieceColor.RED && 
                                    BoardTurnIndicator.getTurnIndicator().getFill() != red) {
                                FillTransition ft = new FillTransition(Duration.millis(2000), 
                                        BoardTurnIndicator.getTurnIndicator(), blue, red);
                                ft.play();
                            }
                            
                            // Red -> Blue.
                            else if (Game.getTurn() == PieceColor.BLUE && 
                                    BoardTurnIndicator.getTurnIndicator().getFill() != blue) {
                                FillTransition ft = new FillTransition(Duration.millis(3000), 
                                        BoardTurnIndicator.getTurnIndicator(), red, blue);
                                ft.play();
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                    // TODO Handle this exception somehow...
                    e.printStackTrace();
                }
            }
        }
    }
}