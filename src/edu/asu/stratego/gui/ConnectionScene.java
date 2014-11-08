package edu.asu.stratego.gui;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Wrapper class for a JavaFX scene. Contains a scene UI and its associated 
 * event handlers for retrieving network connection information from the player 
 * and connecting to the network.
 */
public class ConnectionScene {
    
    private static final Object playerLogin = new Object();
    
    private final int WINDOW_WIDTH  = 300;
    private final int WINDOW_HEIGHT = 150;
    
    private Button    submitFields  = new Button("Enter Battlefield");
    private TextField nicknameField = new TextField();
    private TextField serverIPField = new TextField();
    static  Label     statusLabel   = new Label();
    
    static String nickname;
    static String serverIP;
    
    Scene scene;
    
    /**
     * Creates a new instance of ConnectionScene.
     */
    ConnectionScene() {
        // Create UI.
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Nickname: "), 0, 0);
        gridPane.add(new Label("Server IP: "), 0, 1);
        gridPane.add(nicknameField, 1, 0);
        gridPane.add(serverIPField, 1, 1);
        gridPane.add(submitFields, 1, 3);
        
        BorderPane borderPane = new BorderPane();
        BorderPane.setMargin(statusLabel, new Insets(0, 0, 10, 0));
        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        borderPane.setBottom(statusLabel);
        borderPane.setCenter(gridPane);
        
        // UI Properties.
        GridPane.setHalignment(submitFields, HPos.RIGHT);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        // Event Handler.
        submitFields.setOnAction(e -> Platform.runLater(new ProcessFields()));
        
        scene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    /**
     * Event handler task for submitFields button events. Notifies the 
     * connectToServer thread that connection information has been received 
     * from the user.
     * 
     * <p>
     * The method call to wait() will cause the event to hang until it is woken 
     * up by another thread signaling that a connection attempt has been made. 
     * Until the thread running this task is notified, the form fields will 
     * be disabled preventing the user from firing another event.
     * </p>
     * 
     * @see edu.asu.stratego.gui.ConnectionScene.ConnectToServer
     */
    private class ProcessFields implements Runnable {
        @Override
        public void run() {
            Platform.runLater(() -> {
               statusLabel.setText("Connecting to the server..."); 
            });

            nickname = nicknameField.getText();
            serverIP = serverIPField.getText();
            
            nicknameField.setEditable(false);
            serverIPField.setEditable(false);
            submitFields.setDisable(true);
            
            synchronized (playerLogin) {
                try {
                    playerLogin.notify();  // Signal submitFields button event.
                    playerLogin.wait();    // Wait for connection attempt.
                }
                catch (InterruptedException e) {
                    // TODO Handle this error somehow...
                    e.printStackTrace();
                }
            }
            
            nicknameField.setEditable(true);
            serverIPField.setEditable(true);
            submitFields.setDisable(false);
        }
    }
    
    /**
     * A Runnable task for establishing a connection to a Stratego server.
     * The task will continue running until a successful connection has
     * been made. The connection attempt loop is structured like so:
     * 
     * <ol><li>
     * Wait for the player to invoke button event in the ConnectionScene.
     * </li><li>
     * Attempt to connect to a Stratego server using the information retrieved 
     * from the UI and wake up the button event thread.
     * </li><li>
     * If connection succeeds, signal the isConnected condition to indicate to 
     * other threads a successful connection attempt and then terminate the 
     * task. Otherwise, output error message to GUI, and go to #1.
     * </li></ol>
     * 
     * @see edu.asu.stratego.gui.ConnectionScene.ProcessFields
     */
    public static class ConnectToServer implements Runnable {
        @Override
        public void run() {
            while (ClientStage.socket == null) {
                synchronized (playerLogin) {
                    try {
                        // Wait for submitFields button event.
                        playerLogin.wait();
                        
                        // Attempt connection to server.
                        ClientStage.socket = new Socket(serverIP, 4212);
                    }
                    catch (IOException | InterruptedException e) {
                        Platform.runLater(() -> {
                            statusLabel.setText("Cannot connect to the Server");
                        });
                    }
                    finally {
                        // Wake up button event thread.
                        playerLogin.notify();
                    }
                }
            }
        }
    }
}