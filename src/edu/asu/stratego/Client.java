package edu.asu.stratego;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.asu.stratego.game.ClientGameManager;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.gui.ClientStage;

public class Client extends Application {
    
    /**
     * The Main entry point for the Client application.
     */
    @Override
    public void start(Stage primaryStage) {
        // (MODEL) Start a new game.
        new Game();
        
        // (VIEW) Display client GUI on the JavaFX Application thread.
        ClientStage client = new ClientStage();
        
        // (CONTROLLER) Control the game on a separate thread.
        Thread manager = new Thread(new ClientGameManager(client));
        manager.setDaemon(true);
        manager.start();
    }
    
    /**
     * The main method is only needed for the IDE with limited JavaFX support. 
     * Not needed for running from the command line.
     * 
     * @param args the arguments entered from command line
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}