package edu.asu.stratego.stages;

import java.net.Socket;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The ConnectionStage class, which inherits from the JavaFX Stage
 * class, is a preset Stage for retrieving network connection
 * information from the player.
 */
public class ConnectionStage extends Stage {
    
    /**
     * Used for inter-thread communication between the button handler 
     * and the ConnectToServer task.
     * 
     * @see edu.asu.stratego.stages.ConnectionStage.ProcessInfo
     * @see edu.asu.stratego.stages.ConnectionStage.ConnectToServer
     */
    private final Object playerLogin = new Object();
    private Object isConnected = null;
    
    private String nickname;
    private String serverIP;
    private Socket socket;
    
    private Button submitInfo = new Button("Enter Battlefield");
    private TextField nicknameField = new TextField();
    private TextField serverIPField = new TextField();
    private Label statusLabel = new Label();
    
    /**
     * Creates a new instance of ConnectionStage.
     * 
     * @param isConnected used for inter-thread communication between the 
     * outside threads and the ConnectToServer task.
     */
    public ConnectionStage(Object isConnected) {
        this.isConnected = isConnected;
        
        // Initiate task to connect to Stratego server.
        Thread serverConnect = new Thread(new ConnectToServer());
        serverConnect.setDaemon(true);
        serverConnect.start();
        
        // Create UI.
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nickname: "), 0, 0);
        gridPane.add(new Label("Server IP: "), 0, 1);
        gridPane.add(nicknameField, 1, 0);
        gridPane.add(serverIPField, 1, 1);
        gridPane.add(submitInfo, 1, 3);
        
        BorderPane borderPane = new BorderPane();
        BorderPane.setMargin(statusLabel, new Insets(0, 0, 10, 0));
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(statusLabel);
        
        // UI Properties.
        GridPane.setHalignment(submitInfo, HPos.RIGHT);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        Scene scene = new Scene(borderPane, 300, 150);
        this.setTitle("Stratego");
        this.setResizable(false);
        this.setScene(scene);
        
        // Event handler.
        submitInfo.setOnAction(e -> Platform.runLater(new ProcessInfo()));
    }
    
    /**
     * Event handler task for submitInfo button events. Notifies the 
     * ConnectToServer thread that connection information has been received 
     * from the user.
     */
    private class ProcessInfo implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> {
               statusLabel.setText("Connecting to the server..."); 
            });

            nickname = nicknameField.getText();
            serverIP = serverIPField.getText();
            
            nicknameField.setEditable(false);
            serverIPField.setEditable(false);
            submitInfo.setDisable(true);
            
            synchronized (playerLogin) {
                try {
                    playerLogin.notify();  // Signal submitInfo button event.
                    playerLogin.wait();    // Wait for connection attempt.
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            nicknameField.setEditable(true);
            serverIPField.setEditable(true);
            submitInfo.setDisable(false);
        }
    }
    
    /**
     * A Runnable task for establishing a connection to a Stratego server.
     * The task will continue running until a successful connection has
     * been made. The connection attempt loop is structured like so:
     * 
     * <p><ol><li>
     * Wait for the player to invoke submitInfo button event.
     * </li><li>
     * Attempt to connect to a Stratego server using the information retrieved 
     * from the UI and wake up the button event thread.
     * </li><li>
     * If connection succeeds, signal the isConnected condition to indicate to 
     * other threads a successful connection attempt and then terminate the 
     * task. Otherwise, goto #1.
     * </li></ol>
     */
    public class ConnectToServer implements Runnable {
        @Override
        public void run() {
            while (socket == null) {
                synchronized (playerLogin) {
                    try {
                        // Wait for submitInfo button event.
                        playerLogin.wait();
                        
                        // Attempt connection to server.
                        socket = new Socket(serverIP, 4212);
                    }
                    catch (Exception e) {
                        Platform.runLater(() -> {
                            statusLabel.setText("Error connecting to the server..."); 
                        });
                    }
                    finally {
                        // Wake up button event thread.
                        playerLogin.notify();
                    }
                }
            }
            
            // Notify other threads that a connection attempt has
            // been successful.
            synchronized (isConnected) {
                isConnected.notifyAll();
            }
        }
    }
    
    /**
     * Returns the socket used to attempt to establish a connection between 
     * the client and the server.
     * 
     * @return a network client socket that may or may not have an established 
     * connection to a server.
     */
    public Socket getSocket() {
        return socket;
    }
    
    /**
     * Returns the nickname retrieved from the player.
     * @return a (possibly empty) String that contains the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }
}