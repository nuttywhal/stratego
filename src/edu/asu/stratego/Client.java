package edu.asu.stratego;

import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.asu.stratego.game.ClientGameManager;
import edu.asu.stratego.gui.ClientStage;

public class Client extends Application {
    
    // Connection socket to the server.
    private static Socket socket;
    
    /**
     * The Main entry point for the Client application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Display client GUI.
        ClientStage client = new ClientStage(socket);
        client.setConnectionScene();
        
        // Control the game on a separate thread.
        ClientGameManager manager = new ClientGameManager(client);
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