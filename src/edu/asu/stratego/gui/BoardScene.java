package edu.asu.stratego.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.gui.board.setup.SetupPanel;
import edu.asu.stratego.media.ImageConstants;

/**
 * Wrapper class for a JavaFX scene. Contains a scene UI and its associated 
 * event handlers for playing a game of Stratego.
 */
public class BoardScene {
    
    private final double UNIT = ClientStage.getUnit();
    private final int    SIDE = ClientStage.getSide();
    
    Scene scene;
    
    /**
     * Creates a new instance of BoardScene.
     */
    public BoardScene() {
        
        Game.getPlayer().setNickname("Player 1");
        Game.getOpponent().setNickname("Player 2");
        Game.getPlayer().setColor(PieceColor.RED);
        Game.getOpponent().setColor(PieceColor.BLUE);
        
        /* ================ Board Design ================
         * 
         * The scene is divided into a 12 x 12 grid.
         * Each unit represents a 1 x 1 area.
         * 
         * The scene should be about roughly 85% of the 
         * square of the height of the player's screen 
         * resolution.
         * 
         *          = = = = = = = = = = = =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = + + + + + + + + + + =
         *          = = = = = = = = = = = =
         * 
         * Each '=' indicates part of the board border.
         * Each '+' indicates an individual board square.
         * 
         * Part of the border image is semi-transparent so
         * that the scene background color can come through 
         * to indicate which player's turn it is.
         */
        
        // Set the background color (turn indicator).
        Rectangle background = new Rectangle(0, 0, SIDE, SIDE);
        if (Game.getPlayer().getColor() == PieceColor.RED) {
            background.setFill(new Color(0.48, 0.13, 0.13, 1.0));
        }
        else {
            background.setFill(new Color(0.22, 0.24, 0.55, 1.0));
        }
        
        // Resize the board.
        final int size = 10;
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                Game.getBoard().getSquare(row, col).getPiecePane().getPiece().setFitHeight(UNIT);
                Game.getBoard().getSquare(row, col).getPiecePane().getPiece().setFitWidth(UNIT);
                Game.getBoard().getSquare(row, col).getEventPane().getHover().setFitHeight(UNIT);
                Game.getBoard().getSquare(row, col).getEventPane().getHover().setFitWidth(UNIT);
            }
        }
        
        // Set the setup panel.
        SetupPanel panel = new SetupPanel();
        StackPane.setMargin(panel, new Insets(UNIT, 0, 0, 0));
        StackPane.setAlignment(panel, Pos.TOP_CENTER);
        
        // Create the border.
        ImageView border = new ImageView(ImageConstants.BORDER);
        StackPane.setAlignment(border, Pos.CENTER);
        border.setFitHeight(SIDE);
        border.setFitWidth(SIDE);
        
        StackPane root = new StackPane(background, Game.getBoard().getPiecePane(), 
                                       Game.getBoard().getEventPane(), panel, border);
        root.setMaxSize(SIDE, SIDE);
        Game.getBoard().getPiecePane().setAlignment(Pos.CENTER);
        Game.getBoard().getEventPane().setAlignment(Pos.CENTER);
        
        scene = new Scene(root, SIDE, SIDE);
    }
}