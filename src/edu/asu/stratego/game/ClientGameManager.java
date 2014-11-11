package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Platform;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.gui.ConnectionScene;

/**
 * Task to handle the Stratego game on the client-side.
 */
public class ClientGameManager implements Runnable {
    
    private ObjectOutputStream toServer;
    private ObjectInputStream  fromServer;
    
    private ClientStage stage;
    
    /**
     * Creates a new instance of ClientGameController.
     * 
     * @param client the stage that the client is set in
     * @param game shared between the GUI and ClientGameManager
     * 
     * @see edu.asu.stratego.Game.ClientGameManager
     */
    public ClientGameManager(ClientStage stage) {
        this.stage = stage;
    }

    /**
     * See ServerGameManager's run() method to understand how the client 
     * interacts with the server.
     * 
     * @see edu.asu.stratego.Game.ServerGameManager
     */
    @Override
    public void run() {
        connectToServer();
        waitForOpponent();

        setupBoard();
        // playGame();
    }
    
    /**
     * Executes the ConnectToServer thread. Blocks the current thread until 
     * the ConnectToServer thread terminates.
     * 
     * @see edu.asu.stratego.gui.ConnectionScene.ConnectToServer
     */
    private void connectToServer() {
        try {
            ConnectionScene.ConnectToServer connectToServer = 
                    new ConnectionScene.ConnectToServer();
            Thread serverConnect = new Thread(connectToServer);
            serverConnect.setDaemon(true);
            serverConnect.start();
            serverConnect.join();
        }
        catch(InterruptedException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
    }
    
    /**
     * Establish I/O streams between the client and the server. Send player 
     * information to the server. Then, wait until an object containing player 
     * information about the opponent is received from the server.
     * 
     * <p>
     * After the player information has been sent and opponent information has 
     * been received, the method terminates indicating that it is time to set up
     * the game.
     * </p>
     */
    private void waitForOpponent() {
        Platform.runLater(() -> { stage.setWaitingScene(); });
        
        try {
            // I/O Streams.
            toServer = new ObjectOutputStream(ClientSocket.getInstance().getOutputStream());
            fromServer = new ObjectInputStream(ClientSocket.getInstance().getInputStream());
     
            // Exchange player information.
            toServer.writeObject(Game.getPlayer());
            Game.setOpponent((Player) fromServer.readObject());
            
            // Infer player color from opponent color.
            if (Game.getOpponent().getColor() == PieceColor.RED)
                Game.getPlayer().setColor(PieceColor.BLUE);
            else
                Game.getPlayer().setColor(PieceColor.RED);
        }
        catch (IOException | ClassNotFoundException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
    }
    
    /**
     * The game setup. Players will place their pieces in their starting 
     * positions.
     */
    private void setupBoard() {
        Platform.runLater(() -> { stage.setBoardScene(); });
    }
}