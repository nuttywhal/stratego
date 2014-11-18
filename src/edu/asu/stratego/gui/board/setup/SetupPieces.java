package edu.asu.stratego.gui.board.setup;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceType;
import edu.asu.stratego.gui.ClientStage;

import edu.asu.stratego.util.HashTables;
import edu.asu.stratego.util.MutableBoolean;

/**
 * Pieces in the SetupPanel that the player can select when setting up the game.
 * @see edu.asu.stratego.gui.board.setup.SetupPanel
 */
public class SetupPieces {
	private static HashMap<PieceType, MutableBoolean> pieceSelected = 
	        new HashMap<PieceType, MutableBoolean>(12);
	
	private static HashMap<PieceType, Integer> availability = 
	        new HashMap<PieceType, Integer>(12);
	
	private static HashMap<PieceType, ImageView> pieceImages = 
	        new HashMap<PieceType, ImageView>(12);
	
	private static HashMap<PieceType, Label> pieceCount = 
	        new HashMap<PieceType, Label>(12);
	
	private static PieceType selectedPieceType;
	private static ColorAdjust zeroPieces = new ColorAdjust();
	private static boolean allPiecesPlaced;
	
	/**
	 * Creates a new instance of SetupPieces.
	 */
    public SetupPieces() {
        final double UNIT = ClientStage.getUnit();
        zeroPieces.setSaturation(-1.0);
        selectedPieceType = null;
        
        // Get the player color.
        String playerColor = Game.getPlayer().getColor().toString();
        
        // ImageConstants suffixes.
        String[] pieceSuffix = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                              "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        
        // Number of pieces of each type a player has at the start of the game.
        int[] pieceTypeCount = new int[] { 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1, 1 };
        
        for (int i = 0; i < 12; ++i) {
            // Enumeration values of PieceType.
        	PieceType pieceType = PieceType.values()[i];
        	
        	// Map the piece type to the number of pieces a player can have of that type
        	// at the start of the game.
        	availability.put(pieceType, pieceTypeCount[i]);

        	// Map the piece type to a label that displays the number of pieces that have 
        	// not yet been set on the board.
            pieceCount.put(pieceType, new Label(" x" + availability.get(pieceType)));
            pieceCount.get(pieceType).setFont(Font.font("Century Gothic", UNIT * 0.4));
            pieceCount.get(pieceType).setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

            // Map the piece type to its corresponding image.
            pieceImages.put(pieceType, new ImageView(HashTables.
                    PIECE_MAP.get(playerColor + "_" + pieceSuffix[i])));
            pieceImages.get(pieceType).setFitHeight(UNIT * 0.8);
            pieceImages.get(pieceType).setFitWidth(UNIT * 0.8);
            GridPane.setColumnIndex(pieceImages.get(pieceType), i);
            
            // Register event handlers.
            pieceImages.get(pieceType).addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectPiece());
            
            // Map the piece type to a boolean value that denotes whether or not the 
            // SetupPiece is selected. Initially, none of the pieces are selected.
            pieceSelected.put(pieceType, new MutableBoolean(false));
        }
   }
    
    /**
     * This event is triggered when one of the piece type images are clicked 
     * on. It updates the HashMap that keeps track of which piece type is 
     * selected and adds a glow to the selected piece type's image.
     */
    private class SelectPiece implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView pieceImage = (ImageView) e.getSource();
            
            // Iterate through the PieceType enumerated values.
            for (int i = 0; i < 12; ++i) {
            	PieceType pieceType = PieceType.values()[i];
            	
            	// If the piece type does not match the piece type selected...
                if (pieceImages.get(pieceType) != pieceImage) {
                    // Un-select the piece type.
                    if (availability.get(pieceType) != 0)
                        pieceImages.get(pieceType).setEffect(new Glow(0.0));
                    pieceSelected.get(pieceType).setFalse();
                }
                
                // Otherwise...
                else {
                    // Select the piece type if not already selected.
                    if (!pieceSelected.get(pieceType).getValue() &&
                        availability.get(pieceType) != 0) {
                    	selectedPieceType = pieceType;
                        pieceImage.setEffect(new Glow(1.0));
                        pieceSelected.get(pieceType).setTrue();
                    }
                    
                    // Un-select piece type if already selected.
                    else {
                    	selectedPieceType = null;
                    	if (availability.get(pieceType) != 0)
                    	    pieceImage.setEffect(new Glow(0.0));
                        pieceSelected.get(pieceType).setFalse();
                    }
                }
            }
        }
    }
    
    /**
     * @return the type of the selected piece
     */
    public static PieceType getSelectedPieceType() {
        return selectedPieceType;
    }
    
    /**
     * @param type PieceType
     * @return the number of pieces of the PieceType have not been set on the
     * board yet
     */
    public static int getPieceCount(PieceType type) {
        return availability.get(type);
    }
    
    /**
     * Increments the piece type count by 1 and updates the piece type label.
     * Signals SetupPanel to update the ready button if all of the pieces are 
     * placed.
     * 
     * @param type PieceType to increment
     */
    public static void incrementPieceCount(PieceType type) {
    	availability.put(type, availability.get(type) + 1);
    	pieceCount.get(type).setText(" x" + availability.get(type));
    	
    	if (availability.get(type) == 1)
    	    pieceImages.get(type).setEffect(new Glow(0.0));
    	allPiecesPlaced = false;
    	
    	Object updateReadyStatus = SetupPanel.getUpdateReadyStatus();
    	synchronized (updateReadyStatus) {
    	    updateReadyStatus.notify();
    	}
    }
    
    /**
     * Decrements the piece type count by 1 and updates the piece type label. 
     * Runs a check to see if all the pieces have been placed. Signals 
     * SetupPanel to update the ready button if all of the pieces are placed.
     * 
     * @param type PieceType to decrement
     */
    public static void decrementPieceCount(PieceType type) {
    	availability.put(type, availability.get(type) - 1);
    	pieceCount.get(type).setText(" x" + availability.get(type));
    	
    	if (availability.get(type) == 0) {
    	    pieceImages.get(type).setEffect(zeroPieces);
    	    pieceSelected.get(type).setFalse();
    	    selectedPieceType = null;
    	}
    	
    	allPiecesPlaced = true;
    	for (PieceType pieceType : PieceType.values()) {
    	    if (availability.get(pieceType) > 0)
    	        allPiecesPlaced = false;
    	}
    	
    	Object updateReadyStatus = SetupPanel.getUpdateReadyStatus();
        synchronized (updateReadyStatus) {
            updateReadyStatus.notify();
        }
    }
    
    /**
     * @return true if all the pieces have been placed, false otherwise
     */
    public static boolean getAllPiecesPlaced() {
        return allPiecesPlaced;
    }
    
    /**
     * @return an array of ImageView objects that display images corresponding 
     * to the piece type.
     */
    public ImageView[] getPieceImages() {
    	ImageView[] images = new ImageView[12];
    	
        for (int i = 0; i < 12; ++i) {
        	PieceType pieceType = PieceType.values()[i];
        	images[i] = pieceImages.get(pieceType);
        }
        
        return images;
    }
    
    /**
     * @return an array of JavaFX labels that display the number of pieces of 
     * each piece type that still need to be placed.
     */
    public Label[] getPieceCountLabels() {
    	Label[] pieceCountLabels = new Label[12];
    	
        for (int i = 0; i < 12; ++i) {
        	PieceType pieceType = PieceType.values()[i];
        	pieceCountLabels[i] = pieceCount.get(pieceType);
        }
        
        return pieceCountLabels;
    }
}