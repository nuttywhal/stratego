package edu.asu.stratego.gui.board.setup;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceType;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.lang.MutableBoolean;

public class SetupPieces {
	private static HashMap<PieceType, MutableBoolean> pieceSelected = new HashMap<PieceType, MutableBoolean>(12);
	private static HashMap<PieceType, Integer> availability = new HashMap<PieceType, Integer>(12);
	
	private static HashMap<PieceType, ImageView> pieceImages = new HashMap<PieceType, ImageView>(12);
	private static HashMap<PieceType, Label> pieceCount = new HashMap<PieceType, Label>(12);
	
	private static PieceType selectedPieceType;

    public SetupPieces() {
        final double UNIT = ClientStage.getUnit();
        selectedPieceType = null;
        
        // Get player color.
        String playerColor = Game.getPlayer().getColor().toString();
        
        // Image constants suffixes.
        String[] pieceSuffix = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                              "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        // Temp count of each piece
        int[] tempCount = new int[] { 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1, 1 };
        
        // Set initial piece count.
        for (int i = 0; i < 12; ++i) {
        	PieceType tempEnum = PieceType.values()[i];
        	
        	// Add piece availability to Map
        	availability.put(tempEnum, tempCount[i]);

        	// Add label
            pieceCount.put(tempEnum, new Label(" x" + availability.get(tempEnum)));
            pieceCount.get(tempEnum).setFont(Font.font("Century Gothic", UNIT * 0.4));
            pieceCount.get(tempEnum).setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

            // Set piece images and register event handlers.
            pieceImages.put(tempEnum, new ImageView(ImageConstants.
                    PIECE_MAP.get(playerColor + "_" + pieceSuffix[i])));
            pieceImages.get(tempEnum).setFitHeight(UNIT * 0.8);
            pieceImages.get(tempEnum).setFitWidth(UNIT * 0.8);
            GridPane.setColumnIndex(pieceImages.get(tempEnum), i);
            
            pieceImages.get(tempEnum).addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectPiece());
            
            // Make unselected
            pieceSelected.put(tempEnum, new MutableBoolean(true));
        }
   }
    
    private class SelectPiece implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView pieceImage = (ImageView) e.getSource();
            
            for (int i = 0; i < 12; ++i) {
            	PieceType tempEnum = PieceType.values()[i];
            	
                if (pieceImages.get(tempEnum) != pieceImage) {
                    pieceImages.get(tempEnum).setEffect(new Glow(0.0));
                    pieceSelected.get(tempEnum).setFalse();
                }
                else {
                    if (!pieceSelected.get(tempEnum).getValue()) {
                    	selectedPieceType = tempEnum;
                        pieceImage.setEffect(new Glow(1.0));
                        pieceSelected.get(tempEnum).setTrue();
                    }
                    else {
                    	selectedPieceType = null;
                        pieceImage.setEffect(new Glow(0.0));
                        pieceSelected.get(tempEnum).setFalse();
                    }
                }
            }
        }
    }
    
    public static PieceType getSelectedPieceType() {
        return selectedPieceType;
    }
    
    public static int getSelectedPieceCount(PieceType inEnum) {
        return availability.get(inEnum);
    }
    
    public static void incrementSelectedPieceCount(PieceType inEnum) {
    	availability.put(inEnum, availability.get(inEnum)+1);
    }
    
    public static void decrementSelectedPieceCount(PieceType inEnum) {
    	availability.put(inEnum, availability.get(inEnum)-1);
    }
    
    public ImageView[] getPieceImages() {
    	ImageView[] tempPieceImages = new ImageView[12];
    	
        for (int i = 0; i < 12; ++i) {
        	PieceType tempEnum = PieceType.values()[i];
        	tempPieceImages[i] = pieceImages.get(tempEnum);
        }
        
        return tempPieceImages;
    }
    
    public Label[] getPieceCountText() {
    	Label[] tempPieceCount = new Label[12];
    	
        for (int i = 0; i < 12; ++i) {
        	PieceType tempEnum = PieceType.values()[i];
        	tempPieceCount[i] = pieceCount.get(tempEnum);
        }
        
        return tempPieceCount;
    }
    
}