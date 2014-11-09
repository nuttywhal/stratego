package edu.asu.stratego.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import edu.asu.stratego.media.ImageConstants;

/**
 * Wrapper class for a JavaFX scene. Contains a scene UI and its associated 
 * event handlers for the initial setup of the board game. Players will arrange 
 * their pieces in this scene before the game starts.
 */
public class BoardScene {
    
    private final double UNIT;
    private final int WIDTH;
    private final int HEIGHT;
    
    Scene scene;
    
    /**
     * Creates a new instance of ConnectionScene.
     */
    public BoardScene() {
        
        /* ============== Board Design ==============
         * 
         * The scene is divided into a 12 x 12 grid.
         * Each unit represents a 1 x 1 area.
         * 
         * The scene should be about roughly 85% of the 
         * square of the width of the player's screen 
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
        
        // Calculate the Scene dimensions from screen resolution.
        // Side = # of times 12 divides into 85% of the Resolution Width evenly.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = HEIGHT = (int) (0.85 * screenSize.getHeight()) / 12 * 12;
        UNIT = HEIGHT / 12;
        
        // Set the background color (turn indicator).
        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        background.setFill(new Color(1.0, 1.0, 1.0, 1.0));
        
        // Create the board.
        StackPane[][] squares = new StackPane[10][10];
        ImageView[][] imageGrid = new ImageView[10][10];
        GridPane boardGrid = new GridPane();
        final int size = 10;
        
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                squares[row][col] = new StackPane();
                
                if ((row + col) % 2 == 0) {
                    imageGrid[row][col] = new ImageView(ImageConstants.darkGrass);
                    squares[row][col].getChildren().add(imageGrid[row][col]);
                }
                else {
                    imageGrid[row][col] = new ImageView(ImageConstants.lightGrass);
                    squares[row][col].getChildren().add(imageGrid[row][col]);
                }
                
                // Resize each square.
                imageGrid[row][col].setFitHeight(UNIT);
                imageGrid[row][col].setFitWidth(UNIT);
                
                boardGrid.add(squares[row][col], col, row);
            }
        }
        
        // Create the border.
        ImageView border = new ImageView(ImageConstants.border);
        border.setFitHeight(HEIGHT);
        border.setFitWidth(WIDTH);
        
        StackPane root = new StackPane(background, boardGrid, border);
        boardGrid.setAlignment(Pos.CENTER);
        
        scene = new Scene(root, WIDTH, HEIGHT);
    }
}