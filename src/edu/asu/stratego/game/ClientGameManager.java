package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;
import javafx.stage.Stage;

import edu.asu.stratego.stages.ConnectionStage;

/**
 * Thread to handle the Stratego game on the client-side once the client 
 * has successfully connected to a Stratego server.
 */
public class ClientGameManager extends Thread {
    private Player player = new Player();
    private Player opponent = new Player();
    private final Object isConnected;
    private Stage connectionStage;
    private Socket socket;
    
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    
    /**
     * Creates a new instance of ClientGameController.
     * 
     * @param connectionStage - ConnectionStage window that was used to 
     * establish a connection between the client and the server.
     * 
     * @param isConnected - facilitates inter-thread communication between the 
     * ClientGameController task and the ConnectToServer task.
     * 
     * @see edu.asu.stratego.game.ClientGameManager
     * @see edu.asu.stratego.stages.ConnectionStage.ConnectToServer
     */
    public ClientGameManager(Stage connectionStage, Object isConnected) {
        this.connectionStage = connectionStage;
        this.isConnected = isConnected;
    }
    
    /**
     * See ServerGameManager's run() method to understand how the client 
     * interacts with the server. The sequence of I/O comment markers in 
     * ClientGameManger should correspond to the sequence of I/O comment 
     * markers in ServerGameManager.
     * 
     * @see edu.asu.stratego.game.ServerGameManager
     */
    @Override
    public void run() {
        synchronized (isConnected) {
            try {
                // Wait for client to successfully connect to server.
                isConnected.wait();
                
                // Retrieve socket and player nickname. 
                player.setNickname(((ConnectionStage) connectionStage).getNickname());
                socket = ((ConnectionStage) connectionStage).getSocket();
                
                // Close and remove the ConnectionStage window.
                Platform.runLater(() -> {
                    connectionStage.close();
                    connectionStage = null;
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
            
            /* Set up the game session. */
            toServer.writeObject(player);  // O - Send player information to server.
            updatePlayer();                // I - Updates player information from server.
            updateOpponent();              // I - Updates opponent information from server.
            
            // TODO Implement the rest of ClientGameManager here.
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the client-side player information with the server-side player 
     * information.
     */
    private void updatePlayer() {
        try {
            player = (Player) fromServer.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the client-side opponent information with the server-side 
     * opponent information.
     */
    private void updateOpponent() {
        try {
            opponent = (Player) fromServer.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}