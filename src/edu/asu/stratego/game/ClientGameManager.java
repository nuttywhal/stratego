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
    private String opponent;
    
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
        
        waitConnection();
        establishIOStreams();
            
        sendNickname();     // O - Send player information to server.
        receiveColor();     // I - Updates player information from server.
        getOpponentName();  // I - Updates opponent information from server.
        
        // TODO Implement the rest of ClientGameManager here.
    }
    
    /**
     * Waits for a successful connection between the client and the server.
     * Once a connection has been established, retrieve the socket and the 
     * player's name. Then, change the Stage scene.
     */
    private void waitConnection() {
        synchronized (isConnected) {
            try {
                isConnected.wait();
                 
                player.setNickname(((ConnectionStage) connectionStage).getNickname());
                socket = ((ConnectionStage) connectionStage).getSocket();
                
                Platform.runLater(() -> {
                    ((ConnectionStage) connectionStage).setLoadingScreen();
                    //connectionStage = null;
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Establish IO object streams to facilitate communication between the 
     * client and server.
     */
    private void establishIOStreams() {
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Send the player's nickname to the server.
     */
    private void sendNickname() {
        try {
            toServer.writeObject(player.getNickname());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Receive the player's color from the server.
     */
    private void receiveColor() {
        try {
            player.setColor((PieceColor) fromServer.readObject());
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Receive the opponent's nickname from the server.
     */
    private void getOpponentName() {
        try {
            opponent = (String) fromServer.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}