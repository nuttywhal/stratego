package edu.asu.stratego;

import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.asu.stratego.game.ClientGameManager;
import edu.asu.stratego.gui.ClientStage;

public class Client extends Application {
    
    private static Socket socket;
    
    /**
     * The Main entry point for the Client application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Display client GUI on the JavaFX Application thread.
        ClientStage client = new ClientStage(socket);
        
        // Control the game on a separate thread.
        Thread manager = new Thread(new ClientGameManager(client));
        manager.setDaemon(true);
        manager.start();
    }

    /**
     * Returns the client socket used to connect to a server.
     * @return client socket
     */
    public static Socket getSocket() {
        return socket;
    }
    
    /**
     * Sets the value of value of the socket.
     * @param socket
     */
    public static void setSocket(Socket socket) {
        Client.socket = socket;
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