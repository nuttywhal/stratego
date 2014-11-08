package edu.asu.stratego;

import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.asu.stratego.gui.ClientStage;

public class Client extends Application {
    
    // Connection socket to the server.
    private static Socket socket;
    
    /**
     * The Main entry point for the Client application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Connect to the server.
        ClientStage client = new ClientStage(socket);
        client.setConnectionScene();
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