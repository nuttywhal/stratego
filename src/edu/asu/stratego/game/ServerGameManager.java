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
    
    private PieceColor turn;
    private Move move;
    
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
        
        this.turn = PieceColor.RED;
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
        
        playGame();
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
            
            // screw you Nelson
            //if (Math.random() < 0.5) {
            if(Math.random() < 1) {
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
            
            // Register pieces on the server board.
            for (int row = 0; row < 4; ++row) {
                for (int col = 0; col < 10; ++col) {
                    board.getSquare(row, col).setPiece(setupBoardOne.getPiece(3 - row, 9 - col));
                    board.getSquare(row + 6, col).setPiece(setupBoardTwo.getPiece(row, col));
                }
            }
            
            // Rotate pieces by 180 degrees.
            for (int row = 0; row < 2; ++row) {
                for (int col = 0; col < 10; ++col) {
                    // Player One
                    Piece temp = setupBoardOne.getPiece(row, col);
                    setupBoardOne.setPiece(setupBoardOne.getPiece(3 - row, 9 - col), row, col);
                    setupBoardOne.setPiece(temp, 3 - row, 9 - col);
                    
                    // Player Two
                    temp = setupBoardTwo.getPiece(row, col);
                    setupBoardTwo.setPiece(setupBoardTwo.getPiece(3 - row, 9 - col), row, col);
                    setupBoardTwo.setPiece(temp, 3 - row, 9 - col);
                }
            }
            
            /* Halley's comment */
            
            toPlayerOne.writeObject(setupBoardTwo);
            toPlayerTwo.writeObject(setupBoardOne);
        }
        catch (ClassNotFoundException | IOException e) {
            // TODO Handle this exception somehow...
            e.printStackTrace();
        }
        
    }
    
    private void playGame() {
        // TODO Implement method to check for legal moves.
        
        while (true) {
            try {
                // Send player turn color to clients.
                toPlayerOne.writeObject(turn);
                toPlayerTwo.writeObject(turn);
                
                // Get turn from client.
                if (playerOne.getColor() == turn)
                	move = (Move) fromPlayerOne.readObject();
                else
                	move = (Move) fromPlayerTwo.readObject();
                
                // Check valid move.
                // TODO Implement valid move.
                
                // Register move on the board.
                if (playerOne.getColor() == turn) {
                    board.getSquare(9 - move.getEnd().x, 9 - move.getEnd().y).setPiece(board.getSquare(move.getStart().x, move.getStart().y).getPiece());
                    board.getSquare(9 - move.getStart().x, 9 - move.getStart().y).setPiece(null);
                }
                else {
                    board.getSquare(move.getEnd().x, move.getEnd().y).setPiece(board.getSquare(move.getStart().x, move.getStart().y).getPiece());
                    board.getSquare(move.getStart().x, move.getStart().y).setPiece(null);
                }
                
                // Rotate the move 180 degrees before sending.
                move.getStart().x = 9 - move.getStart().x;
                move.getStart().y = 9 - move.getStart().y;
                move.getEnd().x   = 9 - move.getEnd().x;
                move.getEnd().y   = 9 - move.getEnd().y;
                
                // Send move to other client.
                if (playerOne.getColor() == turn)
                    toPlayerTwo.writeObject(move);
                else
                    toPlayerOne.writeObject(move);
                
                // Change turn color.
                if (turn == PieceColor.RED)
                    turn = PieceColor.BLUE;
                else
                    turn = PieceColor.RED;
                
                // Check win conditions.
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println(session + "Error occured during network I/O");
                return;
            }
        }
    }
}