package edu.asu.stratego.gui.board.setup;

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

public class SetupPieces {
    private static boolean[] pieceSelected = new boolean[12];
    private static int[]     availability  = new int[12];
    
    private ImageView[] pieceImages = new ImageView[12];
    private Label[] pieceCount = new Label[12];
    
    public SetupPieces() {
        final double UNIT = ClientStage.getUnit();
        
        // Set initial piece count.
        availability = new int[] { 8, 5, 4, 4, 4, 3, 2, 1, 1, 6, 1, 1 };
        for (int i = 0; i < pieceCount.length; ++i) {
            pieceCount[i] = new Label(" x" + availability[i]);
            pieceCount[i].setFont(Font.font("Century Gothic", UNIT * 0.4));
            pieceCount[i].setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        }
        
        // Get player color.
        String playerColor = Game.getPlayer().getColor().toString();
        
        // Image constants suffixes.
        String[] pieceSuffix = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                              "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        
        // Set piece images and register event handlers.
        for (int i = 0; i < 12; ++i) {
            pieceImages[i] = new ImageView(ImageConstants.
                    PIECE_MAP.get(playerColor + "_" + pieceSuffix[i]));
            pieceImages[i].setFitHeight(UNIT * 0.8);
            pieceImages[i].setFitWidth(UNIT * 0.8);
            GridPane.setColumnIndex(pieceImages[i], i);
            
            pieceImages[i].addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectPiece());
        } 
    }
    
    private class SelectPiece implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView pieceImage = (ImageView) e.getSource();
            int pieceNumber = GridPane.getColumnIndex(pieceImage);
            
            for (int i = 0; i < 12; ++i) {
                if (pieceImages[i] != pieceImage) {
                    pieceImages[i].setEffect(new Glow(0.0));
                    pieceSelected[i] = false;
                }
                else {
                    if (!pieceSelected[pieceNumber]) {
                        pieceImage.setEffect(new Glow(1.0));
                        pieceSelected[pieceNumber] = true;
                    }
                    else {
                        pieceImage.setEffect(new Glow(0.0));
                        pieceSelected[pieceNumber] = false;
                    }
                }
            }
        }
    }
    
    public static PieceType getSelectedPieceType() {
        for (int i = 0; i < 12; ++i) {
            if (pieceSelected[i] == true)
                return PieceType.values()[i];
        }
        
        return null;
    }
    
    public static int getSelectedPieceCount() {
        for (int i = 0; i < 12; ++i) {
            if (pieceSelected[i] == true)
                return availability[i];
        }
        
        return -1;
    }
    
    public static void incrementSelectedPieceCount() {
        for (int i = 0; i < 12; ++i) {
            if (pieceSelected[i] == true)
                ++availability[i];
        }
    }
    
    public static void decrementSelectedPieceCount() {
        for (int i = 0; i < 12; ++i) {
            if (pieceSelected[i] == true)
                --availability[i];
        }
    }
    
    public ImageView[] getPieceImages() {
        return pieceImages;
    }
    
    public Label[] getPieceCountText() {
        return pieceCount;
    }
    
}