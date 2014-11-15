package edu.asu.stratego.gui.board;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.GameStatus;
import edu.asu.stratego.game.Piece;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.game.PieceType;
import edu.asu.stratego.game.board.Square;
import edu.asu.stratego.gui.board.setup.SetupPieces;
import edu.asu.stratego.media.ImageConstants;

/**
 * A single square within the BoardEventPane.
 */
public class BoardSquareEventPane extends GridPane {
    
    private ImageView hover;
    
    /**
     * Creates a new instance of BoardSquareEventPane.
     */
    public BoardSquareEventPane() {
        hover = new ImageView(ImageConstants.HIGHLIGHT_NONE);
        
        // Event handlers for the square.
        hover.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new OnHover());
        hover.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new OffHover());
        hover.addEventHandler(MouseEvent.MOUSE_CLICKED, new SelectSquare());
        
        this.getChildren().add(hover);
    }
    
    /**
     * This event is triggered when the player's cursor is hovering over the 
     * BoardSquareEventPane. It changes the hover image to indicate to the user 
     * whether or not a square is valid.
     */
    private class OnHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            int row = GridPane.getRowIndex(hover);
            int col = GridPane.getColumnIndex(hover);
            
            if (isHoverValid(row, col))
                hover.setImage(ImageConstants.HIGHLIGHT_VALID);
            else
                hover.setImage(ImageConstants.HIGHLIGHT_INVALID);
        };
    }
    
    /**
     * This event is fired when the cursor leaves the square. It changes the 
     * hover image back to its default image: a blank image with a 1% fill.
     */
    private class OffHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            hover.setImage(ImageConstants.HIGHLIGHT_NONE);
        };
    }
    
    /**
     * This event is fired when the player clicks on the event square. 
     */
    private class SelectSquare implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            // Square position.
        	int row = GridPane.getRowIndex((Node) e.getSource());
            int col = GridPane.getColumnIndex((Node) e.getSource());

            // The square and the piece at this position.
            BoardSquarePane squarePane = Game.getBoard()
                    .getSquare(row, col)
                    .getPiecePane();
            
            Square square = Game.getBoard().getSquare(row, col);
            Piece squarePiece = square.getPiece();
            
            // Player color.
            PieceColor playerColor = Game.getPlayer().getColor();
            
            /* Game Setup Handler */
            if (Game.getStatus() == GameStatus.SETTING_UP && isHoverValid(row, col)) {
                
                // Get the selected piece (piece type and count) from the SetupPanel.
                PieceType selectedPiece = SetupPieces.getSelectedPieceType();
                int selectedPieceCount = 0;
                if (selectedPiece != null)
                    selectedPieceCount = SetupPieces.getPieceCount(selectedPiece);
                
            	// If the square contains a piece...
                if (squarePiece != null) {
                    
                	// Remove the existing piece if it is the same piece on board as the 
                    // selected piece (in SetupPanel) or if no piece is selected (in SetupPanel).
                    if (squarePiece.getPieceType() == selectedPiece || selectedPiece == null) {
                        if (squarePiece.getPieceType() != null)
                            SetupPieces.incrementPieceCount(squarePiece.getPieceType());
                        squarePane.setPiece(null);
                        square.setPiece(null);
                    }
                    
                    // Replace the existing piece with the selected piece (in SetupPanel).
                    else if (squarePiece.getPieceType() != selectedPiece && selectedPieceCount > 0) {
                    	SetupPieces.decrementPieceCount(selectedPiece);
                    	SetupPieces.incrementPieceCount(squarePiece.getPieceType());
                        square.setPiece(new Piece(selectedPiece, playerColor, false));
                        squarePane.setPiece(square.getPiece().getPieceSprite());
                    }
                }
                
                // Otherwise, if the square does not contain a piece...
                else {
                	// Place a new piece on the square.
                    if (selectedPiece != null && selectedPieceCount > 0) {
                        square.setPiece(new Piece(selectedPiece, playerColor, false));
                        squarePane.setPiece(square.getPiece().getPieceSprite());
                        SetupPieces.decrementPieceCount(selectedPiece);
                    }
                }
            }
        }
    }
    
    /**
     * Indicates whether or not a square is a valid square to click.
     * 
     * @param row row index of the square
     * @param col column index of the square
     * @return true if the square is valid, false otherwise
     */
    private boolean isHoverValid(int row, int col) {
        /* Initially assumes that the square is valid. */
        
        // Lakes are always invalid.
        if (col == 2 || col == 3 || col == 6 || col == 7) {
            if (row == 4 || row == 5)
                return false;
        }
        
        // The game is setting up and the square is outside of the setup area.
        if (Game.getStatus() == GameStatus.SETTING_UP && row <= 5)
            return false;
        
        return true;
    }
    
    /**
     * @return the ImageView object that displays the hover image.
     */
    public ImageView getHover() {
        return hover;
    }
}