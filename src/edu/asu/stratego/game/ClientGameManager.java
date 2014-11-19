package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Platform;
import edu.asu.stratego.game.board.ClientSquare;
import edu.asu.stratego.gui.BoardScene;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.gui.ConnectionScene;
import edu.asu.stratego.gui.board.BoardTurnIndicator;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.util.HashTables;

/**
 * Task to handle the Stratego game on the client-side.
 */
public class ClientGameManager implements Runnable {
    
    private static Object setupPieces = new Object();
    private static Object sendMove    = new Object();
    private static Object receiveMove = new Object();
    
    private ObjectOutputStream toServer;
    private ObjectInputStream  fromServer;
    
    private ClientStage stage;
    
    /**
     * Creates a new instance of ClientGameManager.
     * 
     * @param stage the stage that the client is set in
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
        playGame();
    }
    
    /**
     * @return Object used for communication between the Setup Board GUI and 
     * the ClientGameManager to indicate when the player has finished setting 
     * up their pieces.
     */
    public static Object getSetupPieces() {
        return setupPieces;
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
     * Switches to the game setup scene. Players will place their pieces to 
     * their initial starting positions. Once the pieces are placed, their 
     * positions are sent to the server.
     */
    private void setupBoard() {
        Platform.runLater(() -> { stage.setBoardScene(); });
        
        synchronized (setupPieces) {
            try {
                // Wait for the player to set up their pieces.
                setupPieces.wait();
                Game.setStatus(GameStatus.WAITING_OPP);
                
                // Send initial piece positions to server.
                SetupBoard initial = new SetupBoard();
                initial.getPiecePositions();
                toServer.writeObject(initial);
                
                // Receive opponent's initial piece positions from server.
                final SetupBoard opponentInitial = (SetupBoard) fromServer.readObject();
                
                // Place the opponent's pieces on the board.
                Platform.runLater(() -> {
                    for (int row = 0; row < 4; ++row) {
                        for (int col = 0; col < 10; ++col) {
                            ClientSquare square = Game.getBoard().getSquare(row, col);
                            square.setPiece(opponentInitial.getPiece(row, col));
                            
                            if (Game.getPlayer().getColor() == PieceColor.RED)
                                square.getPiecePane().setPiece(ImageConstants.BLUE_BACK);
                            else
                                square.getPiecePane().setPiece(ImageConstants.RED_BACK);
                        }
                    }
                });
            }
            catch (InterruptedException | IOException | ClassNotFoundException e) {
                // TODO Handle this exception somehow...
            }
        }
    }
    
    private void playGame() {
        Platform.runLater(() -> {
            BoardScene.getRootPane().getChildren().remove(BoardScene.getSetupPanel());
        });
        
        Game.setStatus(GameStatus.IN_PROGRESS);
        
        while (Game.getStatus() == GameStatus.IN_PROGRESS) {
            try {
                System.out.println("New Turn");
                
                // Get turn color from server.
                Game.setTurn((PieceColor) fromServer.readObject());
                
                // DAVID: If the turn is the client's, set move status to none selected
            	if(Game.getPlayer().getColor() == Game.getTurn())
            		Game.setMoveStatus(MoveStatus.NONE_SELECTED);
            	else
            		Game.setMoveStatus(MoveStatus.OPP_TURN);
            		
                // Notify turn indicator.
                synchronized (BoardTurnIndicator.getTurnIndicatorTrigger()) {
                    BoardTurnIndicator.getTurnIndicatorTrigger().notify();
                }
                
                // Send move to the server.
                if (Game.getPlayer().getColor() == Game.getTurn()) {
                    synchronized (sendMove) {
                    	sendMove.wait();
                    	toServer.writeObject(Game.getMove());
                    }
                    
                    Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).setPiece(Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiece());
                    Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).setPiece(null);
                    
                    // Update GUI.
                    Platform.runLater(() -> {
                        ClientSquare square = Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y);
                        square.getPiecePane().setPiece(HashTables.PIECE_MAP.get(square.getPiece().getPieceSpriteKey()));
                        
                        square = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
                        square.getPiecePane().setPiece(null);
                    });
                }
                
                // Receive move from the server.
                else {
                    Game.setMove((Move) fromServer.readObject());
                    Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).setPiece(Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiece());
                    Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).setPiece(null);
                    
                    // Update GUI.
                    Platform.runLater(() -> {
                        ClientSquare square = Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y);
                        if (Game.getPlayer().getColor() == PieceColor.RED)
                            square.getPiecePane().setPiece(ImageConstants.BLUE_BACK);
                        else
                            square.getPiecePane().setPiece(ImageConstants.RED_BACK);
                        
                        square = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
                        square.getPiecePane().setPiece(null);
                    });
                }
                
                // Get game status from server.
            } 
            catch (ClassNotFoundException | IOException | InterruptedException e) {
                // TODO Handle this exception somehow...
                e.printStackTrace();
            }
        }
    }

	public static Object getSendMove() {
		return sendMove;
	}

    public static Object getReceiveMove() {
        return receiveMove;
    }
}