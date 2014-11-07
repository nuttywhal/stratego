package edu.asu.stratego;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.asu.stratego.stages.ConnectionStage;
import edu.asu.stratego.game.ClientGameManager;

public class Client extends Application {
    
    /**
     * Used for inter-thread communication between the ClientGameController 
     * thread and the ConnectToServer thread.
     * 
     * @see edu.asu.stratego.game.ClientGameManager
     * @see edu.asu.stratego.stages.ConnectionStage.ConnectToServer
     */
    private final Object isConnected = new Object();
    
    /**
     * The Main entry point for the Client application.
     * 
     * @param primaryStage the primary stage for this application, onto which 
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Get nickname and server IP address from the user.
        primaryStage = new ConnectionStage(isConnected);
        primaryStage.show();
        
        // Control the game on a separate thread.
        new ClientGameManager(primaryStage, isConnected).start();
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