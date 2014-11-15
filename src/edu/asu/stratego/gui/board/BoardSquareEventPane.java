package edu.asu.stratego.gui.board;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
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

public class BoardSquareEventPane extends GridPane {
    
    private ImageView hover;
    
    private int row;
    private int col;
    
    public BoardSquareEventPane() {
        hover = new ImageView(ImageConstants.HIGHLIGHT_NONE);
        hover.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new OnHover());
        hover.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new OffHover());
        hover.addEventHandler(MouseEvent.MOUSE_CLICKED, new SelectSquare());
        
        this.getChildren().add(hover);
    }
    
    private class OnHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            int hoverRow = GridPane.getRowIndex(hover);
            int hoverCol = GridPane.getColumnIndex(hover);
            
            System.out.println(hoverRow + " " + hoverCol);
            
            if (isHoverValid(hoverRow, hoverCol))
                hover.setImage(ImageConstants.HIGHLIGHT_VALID);
            else
                hover.setImage(ImageConstants.HIGHLIGHT_INVALID);
        };
    }
    
    private class OffHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            hover.setImage(ImageConstants.HIGHLIGHT_NONE);
        };
    }
    
    private class SelectSquare implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            // Mouse position
        	int selectRow = GridPane.getRowIndex((Node) e.getSource());
            int selectCol = GridPane.getColumnIndex((Node) e.getSource());

            // Hover Piece
            BoardSquarePane squarePane = Game.getBoard().getSquare(selectRow, selectCol).getPiecePane();
            Square square = Game.getBoard().getSquare(selectRow, selectCol);
            Piece hoverPiece = square.getPiece();
            
            // Selected Piece
            PieceType selectedPieceType = SetupPieces.getSelectedPieceType();
            int selectedPieceCount = SetupPieces.getSelectedPieceCount(selectedPieceType);

            PieceColor playerColor = Game.getPlayer().getColor();
            
            if (Game.getStatus() == GameStatus.SETTING_UP && isHoverValid(selectRow, selectCol)) {
            	// If the square has a piece
                if (hoverPiece != null) {
                	// Removing existing piece (same piece on board as selected)
                    if (hoverPiece.getPieceType() == selectedPieceType) {
                        square.setPiece(null);
                        squarePane.setPiece(null);
                        SetupPieces.incrementSelectedPieceCount(selectedPieceType);
                        System.out.println("Removed existing piece (" + selectedPieceType + ")");
                    }
                    // Replacing existing piece with a new piece
                    else if (hoverPiece.getPieceType() != selectedPieceType && selectedPieceCount > 0) {
                    	SetupPieces.decrementSelectedPieceCount(selectedPieceType);
                    	SetupPieces.incrementSelectedPieceCount(hoverPiece.getPieceType());
                    	System.out.println("Replaced existing piece (" + hoverPiece.getPieceType() + " -> " + selectedPieceType + ")");
                        square.setPiece(new Piece(selectedPieceType, playerColor, false));
                        squarePane.setPiece(getImageFromPieceType(selectedPieceType));
                    }
                }
                // If the square does not have a piece
                else {
                	// Placing a new piece (no existing piece)
                    if (selectedPieceType != null && selectedPieceCount > 0) {
                        square.setPiece(new Piece(selectedPieceType, playerColor, false));
                        squarePane.setPiece(getImageFromPieceType(selectedPieceType));
                        SetupPieces.decrementSelectedPieceCount(selectedPieceType);
                        System.out.println("Placed new piece on empty square (" + selectedPieceType + ")");
                    }
                }
            }
        }
    }
    
    private boolean isHoverValid(int row, int col) {
        // Lakes are always invalid.
        if (col == 2 || col == 3 || col == 6 || col == 7) {
            if (row == 4 || row == 5) {
                return false;
            }
        }
        
        // If game is setting up and outside initial setup area.
        if (Game.getStatus() == GameStatus.SETTING_UP && row <= 5) {
            return false;
        }
        
        return true;
    }
    
    public ImageView getHover() {
        return hover;
    }
    
    public void setPiece(Image p) {
        hover.setImage(p);
    }
    
    private Image getImageFromPieceType(PieceType type) {
        String playerColor = Game.getPlayer().getColor().toString();
        String[] pieceSuffix = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                              "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        
        Image tempImage = null;
        
        for (int i = 0; i < 12; i++) {
            if (type == PieceType.values()[i]) {
                tempImage = ImageConstants.PIECE_MAP.get(playerColor + "_" + pieceSuffix[i]);
            }
        }

        return tempImage;
    }
    
    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }
}