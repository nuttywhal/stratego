package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.asu.stratego.game.board.ServerBoard;

/**
 * Task to manage a Stratego game between two clients.
 */
public class ServerGameManager implements Runnable {

    private final String session;
    
    private ServerBoard board = new ServerBoard();
    
    private ObjectOutputStream toPlayerOne;
    private ObjectOutputStream toPlayerTwo;
    private ObjectInputStream  fromPlayerOne;
    private ObjectInputStream  fromPlayerTwo;
    
    private Player playerOne = new Player();
    private Player playerTwo = new Player();
    
    private Socket socketOne;
    private Socket socketTwo;
    
    /**
     * Creates a new instance of ServerGameManager.
     * 
     * @param sockOne socket connected to Player 1's client
     * @param sockTwo socket connected to Player 2's client
     * @param sessionNum the nth game session created by Server.
     * 
     * @see edu.asu.stratego.Server
     */
    public ServerGameManager(Socket sockOne, Socket sockTwo, int sessionNum) {
        this.session = "Session " + sessionNum + ": ";
        this.socketOne = sockOne;
        this.socketTwo = sockTwo;
    }
    
    /**
     * See ClientGameManager's run() method to understand how the server 
     * interacts with the client.
     * 
     * @see edu.asu.stratego.game.ClientGameManager
     */
    @Override
    public void run() {
        createIOStreams();
        exchangePlayers();
        exchangeSetup();
        
        // TODO Implement the rest of ServerGameManager here.
    }

    /**
     * Establish IO object streams to facilitate communication between the 
     * client and server.
     */
    private void createIOStreams() {
        try {
            // NOTE: ObjectOutputStreams must be constructed before the 
            //       ObjectInputStreams so as to prevent a remote deadlock.
            toPlayerOne   = new ObjectOutputStream(socketOne.getOutputStream());
            fromPlayerOne = new ObjectInputStream(socketOne.getInputStream());
            toPlayerTwo   = new ObjectOutputStream(socketTwo.getOutputStream());
            fromPlayerTwo = new ObjectInputStream(socketTwo.getInputStream());
        }
        catch(IOException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
    }
    
    /**
     * Receive player information from the clients. Determines the players' 
     * colors, and sends the player information of the opponents back to the 
     * clients.
     */
    private void exchangePlayers() {
        try {
            playerOne = (Player) fromPlayerOne.readObject();
            playerTwo = (Player) fromPlayerTwo.readObject();
            
            if (Math.random() < 0.5) {
                playerOne.setColor(PieceColor.RED);
                playerTwo.setColor(PieceColor.BLUE);
            }
            else {
                playerOne.setColor(PieceColor.BLUE);
                playerTwo.setColor(PieceColor.RED);
            }
            
            toPlayerOne.writeObject(playerTwo);
            toPlayerTwo.writeObject(playerOne);
        }
        catch(ClassNotFoundException | IOException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
    }
    
    private void exchangeSetup() {
        try {
            SetupBoard setupBoardOne = (SetupBoard) fromPlayerOne.readObject();
            SetupBoard setupBoardTwo = (SetupBoard) fromPlayerTwo.readObject();
            
            System.out.println("Boards successfully received.");
        }
        catch (ClassNotFoundException | IOException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
        
    }
}