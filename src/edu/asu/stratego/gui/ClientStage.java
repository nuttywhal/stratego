package edu.asu.stratego.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.stage.Stage;

/**
 * The ConnectionStage class, which inherits from the JavaFX Stage class, is a 
 * preset Stage for facilitating easy navigation between scenes in the Client 
 * application.
 */
public class ClientStage extends Stage {
    
    private ConnectionScene connection;
    private WaitingScene    waiting;
    private BoardScene      board;
    
    private static double UNIT;
    private static int    SIDE;
    
    /**
     * Creates a new instance of ClientStage.
     */
    public ClientStage() {
        setConnectionScene();
        this.setTitle("ASU Stratego");
        this.setResizable(false);
        this.show();
    }
    
    /**
     * Switch to the Connection Scene.
     * @see edu.asu.stratego.gui.ConnectionScene
     */
    public void setConnectionScene() {
        connection = new ConnectionScene();
        this.setScene(getConnection().scene);
    }
    
    /**
     * Switch to the Waiting Scene.
     * @see edu.asu.stratego.gui.WaitingScene
     */
    public void setWaitingScene() {
        waiting = new WaitingScene();
        this.setScene(waiting.scene);
    }
    
    /**
     * Switch to the Board Scene.
     * @see edu.asu.stratego.gui.BoardScene
     */
    public void setBoardScene() {
        board = new BoardScene();
        this.setScene(board.scene);
    }

    /**
     * Returns the ConnectionScene created in the ClientStage instance.
     * @return ConnectionScene object
     */
    public ConnectionScene getConnection() {
        return connection;
    }

    /**
     * Returns the length/width divided by 12.
     * @return the unit
     */
    public static double getUnit() {
        return UNIT;
    }
    
    /**
     * Returns the length/width of the Board Scene.
     * @return the side length of the scene
     */
    public static int getSide() {
        return SIDE;
    }
}