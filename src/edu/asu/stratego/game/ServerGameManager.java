package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import edu.asu.stratego.game.PieceColor;

/**
 * Server-sided Game Manager that regulates a Stratego session between two 
 * players/clients. The server mediates the players' moves, determining whose 
 * turn it is and allowing only valid moves. 
 */
public class ServerGameManager implements Runnable {
    private final String logPrefix;
    
    private Player player1 = new Player();
    private Player player2 = new Player();
    
    private Socket socketP1;
    private Socket socketP2;
    
    private ObjectInputStream fromPlayer1;
    private ObjectOutputStream toPlayer1;
    private ObjectInputStream fromPlayer2;
    private ObjectOutputStream toPlayer2;
    
    private final int sessionNumber;
    
    /**
     * Creates a new instance of ServerGameManager.
     * 
     * @param player1 socket connected to Player 1's client
     * @param player2 socket connected to Player 2's client
     * @param sessionNumber the nth session created by Server.
     * 
     * @see edu.asu.stratego.Server
     */
    public ServerGameManager(Socket player1, Socket player2, int sessionNumber) {
        this.sessionNumber = sessionNumber;
        this.socketP1 = player1;
        this.socketP2 = player2;
        
        logPrefix = "Session " + sessionNumber + " - ";
    }
    
    /**
     * See ClientGameManager's run() method to understand how the server 
     * interacts with the client. The sequence of I/O comment markers in 
     * ServerGameManger should correspond to the sequence of I/O comment 
     * markers in ClientGameManager.
     * 
     * @see edu.asu.stratego.game.ClientGameManager
     */
    @Override
    public void run() {
        /* Set up the game session. */
        establishIOStreams();     // Establish IO streams for communication.
        retrievePlayerObjects();  // I - Receive client player information.
        determinePlayerColors();  // === Flip a coin to determine player colors.
        updatePlayerObjects();    // O - Update client player information.
        sendOpponentObjects();    // O - Send opponent information to clients.
        
        System.out.println(new Date() + " - " + logPrefix + 
                           player1.getNickname() + " (" + player1.getColor() + ") and " + 
                           player2.getNickname() + " (" + player2.getColor() + ") are " + 
                           "preparing their armies for battle.");
        
        // TODO Implement the rest of ServerGameManager here.
    }
    
    /**
     * Establish IO object streams to facilitate communication between the 
     * client and server.
     */
    private void establishIOStreams() {
        try {
            // NOTE: ObjectOutputStreams must be constructed before the 
            //       ObjectInputStreams so as to prevent a remote deadlock.
            toPlayer1 = new ObjectOutputStream(socketP1.getOutputStream());
            fromPlayer1 = new ObjectInputStream(socketP1.getInputStream());
            toPlayer2 = new ObjectOutputStream(socketP2.getOutputStream());
            fromPlayer2 = new ObjectInputStream(socketP2.getInputStream());
        }
        catch(IOException | NullPointerException e) {
            System.err.println(new Date() + " - " + logPrefix + "Failed to establish " + 
                               "IO streams between client and server");
        }
    }
    
    /**
     * Updates the server-side player information with the client-side player 
     * information.
     */
    private void retrievePlayerObjects() {
        try {
            player1 = (Player) fromPlayer1.readObject();
            player2 = (Player) fromPlayer2.readObject();
        }
        catch (ClassNotFoundException | IOException | NullPointerException e) {
            System.err.println(new Date() + " - " + logPrefix + "Failed to " + 
                               "retrieve Player objects from the clients");
        }
    }
    
    /**
     * Updates the client-side player information with the server-side player
     * information.
     */
    private void updatePlayerObjects() {
        try {
            toPlayer1.writeObject(player1);
            toPlayer2.writeObject(player2);
        }
        catch (IOException | NullPointerException e) {
            System.err.println(new Date() + " - " + logPrefix + "Failed to send " + 
                               "Player objects to the clients");
        }
    }
    
    /**
     * Sends to clients information about their opponent.
     */
    private void sendOpponentObjects() {
        try {
            toPlayer1.writeObject(player2);
            toPlayer2.writeObject(player1);
        }
        catch (IOException | NullPointerException e) {
            System.err.println(new Date() + " - " + logPrefix + "Failed to send " + 
                               "Player objects to the opponent clients");
        }
    }
    
    /**
     * Randomly determines the color of each player. The players' colors are 
     * transmitted to their respective clients.
     */
    private void determinePlayerColors() {
        if (Math.random() < 0.5) {
            player1.setColor(PieceColor.RED);
            player2.setColor(PieceColor.BLUE);
        }
        else {
            player1.setColor(PieceColor.BLUE);
            player2.setColor(PieceColor.RED);
        }
    }
}