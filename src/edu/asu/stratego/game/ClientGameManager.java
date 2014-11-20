package edu.asu.stratego.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
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
    private static Object waitFade    = new Object();
    
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
        
        
        try {
			Game.setStatus((GameStatus) fromServer.readObject());
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Handle this somehow...
			e1.printStackTrace();
		}

        
        
        while (Game.getStatus() == GameStatus.IN_PROGRESS) {
            try {
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
                if (Game.getPlayer().getColor() == Game.getTurn() && Game.getMoveStatus() != MoveStatus.SERVER_VALIDATION) {
                    synchronized (sendMove) {
                    	sendMove.wait();
                    	toServer.writeObject(Game.getMove());
                    	Game.setMoveStatus(MoveStatus.SERVER_VALIDATION);
                    }
                }
                
                // Receive move from the server.
                if (true) {
                    Game.setMove((Move) fromServer.readObject());
                    Piece startPiece = Game.getMove().getStartPiece();
                    Piece endPiece = Game.getMove().getEndPiece();
                    
                    Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).setPiece(startPiece);
                    Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y).setPiece(endPiece);
                    
                    // Update GUI.
                    Platform.runLater(() -> {
                        ClientSquare startSquare = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
                        ClientSquare endSquare = Game.getBoard().getSquare(Game.getMove().getEnd().x, Game.getMove().getEnd().y);
                        
                        // Most likely (like, 99% sure) only in a draw
                        if(endPiece == null) 
                        	endSquare.getPiecePane().setPiece(null);
                        else{
                        	if(endPiece.getPieceColor() == Game.getPlayer().getColor()) {
                            	endSquare.getPiecePane().setPiece(HashTables.PIECE_MAP.get(endPiece.getPieceSpriteKey()));
                            }
                            else{
    	                        if (endPiece.getPieceColor() == PieceColor.BLUE)
    	                        	endSquare.getPiecePane().setPiece(ImageConstants.BLUE_BACK);
    	                        else
    	                        	endSquare.getPiecePane().setPiece(ImageConstants.RED_BACK);
                            }
                        }
                        
                        // Arrow
                        ClientSquare arrowSquare = Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y);
                        if(Game.getMove().getMoveColor() == PieceColor.RED)
                        	arrowSquare.getPiecePane().setPiece(ImageConstants.MOVEARROW_RED);
                        else
                        	arrowSquare.getPiecePane().setPiece(ImageConstants.MOVEARROW_BLUE);
                        
                        if(Game.getMove().getStart().x > Game.getMove().getEnd().x) 
                        	arrowSquare.getPiecePane().getPiece().setRotate(0);
                        else if(Game.getMove().getStart().y < Game.getMove().getEnd().y) 
                        	arrowSquare.getPiecePane().getPiece().setRotate(90);
                        else if(Game.getMove().getStart().x < Game.getMove().getEnd().x) 
                        	arrowSquare.getPiecePane().getPiece().setRotate(180);
                        else
                        	arrowSquare.getPiecePane().getPiece().setRotate(270);
                        
                        FadeTransition ft = new FadeTransition(Duration.millis(1500), arrowSquare.getPiecePane().getPiece());
                        ft.setFromValue(1.0);
                        ft.setToValue(0.0);
                        ft.play();
                        ft.setOnFinished(new ResetSquareImage());
                    });
                }
                
                // Wait for fade animation to complete before continuing.
                synchronized (waitFade) { waitFade.wait(); }
                
                // Get game status from server.
                Game.setStatus((GameStatus) fromServer.readObject());
            }
            catch (ClassNotFoundException | IOException | InterruptedException e) {
                // TODO Handle this exception somehow...
                e.printStackTrace();
            }
        }
        revealAll();
    }

	public static Object getSendMove() {
		return sendMove;
	}

    public static Object getReceiveMove() {
        return receiveMove;
    }
    
    private void revealAll() {
    	// End game, reveal all pieces
    	Platform.runLater(() -> {
    		for(int row = 0; row < 10; row++) {
    			for(int col = 0; col < 10; col++) {
    				if(Game.getBoard().getSquare(row, col).getPiece() != null && Game.getBoard().getSquare(row, col).getPiece().getPieceColor() != Game.getPlayer().getColor()) {
    					Game.getBoard().getSquare(row, col).getPiecePane().setPiece(HashTables.PIECE_MAP.get(Game.getBoard().getSquare(row, col).getPiece().getPieceSpriteKey()));
    				}
    			}
    		}
    	});
    }
    
    private class ResetSquareImage implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            synchronized (waitFade) {
                waitFade.notify();
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().getPiece().setOpacity(1.0);
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().getPiece().setRotate(0.0);
                Game.getBoard().getSquare(Game.getMove().getStart().x, Game.getMove().getStart().y).getPiecePane().setPiece(null);
            }
        }
    }
}