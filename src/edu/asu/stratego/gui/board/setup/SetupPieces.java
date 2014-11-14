package edu.asu.stratego.gui.board.setup;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SetupPieces {
    private ImageView[] pieceImages = new ImageView[12];
    private Label[] pieceCount = new Label[12];
    
    public SetupPieces() {
        final double UNIT = ClientStage.getUnit();
        
        // Set initial piece count.
        int[] availability = new int[] { 1, 1, 2, 3, 4, 4, 4, 5, 8, 6, 1, 1 };
        for (int i = 0; i < pieceCount.length; ++i) {
            pieceCount[i] = new Label(" x" + availability);
            pieceCount[i].setFont(Font.font("Century Gothic", UNIT * 0.4));
            pieceCount[i].setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        }
        
        // Get player color.
        String playerColor = Game.getPlayer().getColor().toString();
        
        // Image constants suffixes.
        String[] pieceSuffix = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                              "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        
        // Set piece images and event handlers.
        for (int i = 0; i < 12; ++i) {
            pieceImages[i] = new ImageView(ImageConstants.
                    PIECE_MAP.get(playerColor + "_" + pieceSuffix[i]));
            pieceImages[i].setFitHeight(UNIT * 0.8);
            pieceImages[i].setFitWidth(UNIT * 0.8);
            
            SelectPiece selectPiece = new SelectPiece();
            pieceImages[i].addEventHandler(MouseEvent.MOUSE_CLICKED, selectPiece);
        }
    }
    
    private class SelectPiece implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("Piece has been clicked.");
        }
    }
    
}