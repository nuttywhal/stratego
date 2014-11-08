package edu.asu.stratego.game;

import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.gui.ConnectionScene;

/**
 * Thread to handle the Stratego game on the client-side once the client 
 * has successfully connected to a Stratego server.
 */
public class ClientGameManager extends Thread {
    
    private ClientStage stage;
    
    /**
     * Creates a new instance of ClientGameController.
     * @param client the stage that the client is set in
     */
    public ClientGameManager(ClientStage stage) {
        this.stage = stage;
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
        connectToServer();
        System.out.println("Connection has been established!");
        
        // TODO Implement the rest of ClientGameManager here.
    }
    
    /**
     * Executes the ConnectToServer thread. Pauses this current thread until 
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
            // TODO Handle this error somehow...
            e.printStackTrace();
        }
    }
}