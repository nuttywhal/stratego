package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.asu.stratego.game.PieceColor;

/**
 * Server-sided Game Manager that regulates a Stratego session between two 
 * players/clients. The server mediates the players' moves, determining whose 
 * turn it is and allowing only valid moves. 
 */
public class ServerGameManager implements Runnable {
    
    private final int sessionNumber;
    private final String session;
    
    private Socket socketP1;
    private Socket socketP2;
    
    private Player player1 = new Player();
    private Player player2 = new Player();
    
    private ObjectInputStream fromPlayer1;
    private ObjectOutputStream toPlayer1;
    private ObjectInputStream fromPlayer2;
    private ObjectOutputStream toPlayer2;
    
    /**
     * Creates a new instance of ServerGameManager.
     * 
     * @param player1 socket connected to Player 1's client
     * @param player2 socket connected to Player 2's client
     * @param sessionNumber the nth game session created by Server.
     * 
     * @see edu.asu.stratego.Server
     */
    public ServerGameManager(Socket player1, Socket player2, int sessionNumber) {
        this.sessionNumber = sessionNumber;
        this.socketP1 = player1;
        this.socketP2 = player2;
        
        session = "Session " + sessionNumber + ": ";
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
        
        establishIOStreams();
        getPlayerColors();
        
        receiveNames();       // I - Receive player names from clients.
        sendColors();         // O - Send player colors to clients.
        sendOpponentNames();  // O - Send opponent names to clients.
        
        System.out.println(session + player1.getNickname() + " (" + player1.getColor() + ") vs. " + 
                                     player2.getNickname() + " (" + player2.getColor() + "), START!");
        
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
            System.err.println(session + "Failed to establish client/server IO streams");
        }
    }
    
    /**
     * Receive player nicknames from the clients.
     */
    private void receiveNames() {
        try {
            player1.setNickname((String) fromPlayer1.readObject());
            player2.setNickname((String) fromPlayer2.readObject());
        }
        catch (ClassNotFoundException | IOException | NullPointerException e) {
            System.err.println(session + "Failed to retrieve Player nicknames");
        }
    }
    
    /**
     * Send player colors to the corresponding clients.
     */
    private void sendColors() {
        try {
            toPlayer1.writeObject(player1.getColor());
            toPlayer2.writeObject(player2.getColor());
        }
        catch (IOException | NullPointerException e) {
            System.err.println(session + "Failed to send Player colors");
        }
    }
    
    /**
     * Send nicknames of opponents to the corresponding clients.
     */
    private void sendOpponentNames() {
        try {
            toPlayer1.writeObject(player2.getNickname());
            toPlayer2.writeObject(player1.getNickname());
        }
        catch (IOException | NullPointerException e) {
            System.err.println(session + "Failed to send Player nicknames");
        }
    }
    
    /**
     * Randomly determine the color of each player.
     */
    private void getPlayerColors() {
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