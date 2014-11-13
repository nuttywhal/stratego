package edu.asu.stratego.gui.board.setup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;

public class SetupPanel extends GridPane {
    
    public SetupPanel() {
        final double UNIT = ClientStage.getUnit();
        
        // Panel background.
        String backgroundURL = "edu/asu/stratego/media/images/board/setup_panel.png";
        this.setStyle("-fx-background-image: url(" + backgroundURL + "); " + 
                      "-fx-background-size: " + UNIT * 10 + " " + UNIT * 5 + ";" +
                      "-fx-background-repeat: stretch;");
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                        C R E A T E   U I :   H E A D E R                      *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        // Stratego logo.
        ImageView logo = new ImageView(ImageConstants.stratego_logo);
        GridPane.setMargin(logo, new Insets(UNIT * 0.15, 0.0, 0.0, UNIT * 0.3));
        logo.setFitHeight(UNIT * 1.25);
        logo.setFitWidth(UNIT * 4.4);
        
        // Header line.
        Rectangle headerLine = new Rectangle(UNIT * 0.04, UNIT * 1.25);
        headerLine.setFill(new Color(0.4, 0.1, 0.0, 1.0));
        
        // Nickname Display.
        GridPane headerText = new GridPane();
        headerText.getRowConstraints().add(new RowConstraints(UNIT * 0.6));
        GridPane.setMargin(headerText, new Insets(UNIT * 0.2, 0, 0, UNIT * 0.2));
        
        String titleContent = Game.getPlayer().getNickname() + " vs. " + Game.getOpponent().getNickname();
        double fontScale = 1.0 / ((titleContent.length() - 7) / 8 + 2);
        
        Label nameDisplay = new Label(titleContent);
        nameDisplay.setFont(Font.font("Century Gothic", FontWeight.BOLD, UNIT * fontScale));
        nameDisplay.setTextFill(new Color(1.0, 0.7, 0.0, 1.0));
        nameDisplay.setAlignment(Pos.BOTTOM_LEFT);
        headerText.add(nameDisplay, 0, 0);
        
        // Setup Timer.
        Label setupTimer = new Label("Setup Time Left: 3:14");
        setupTimer.setFont(Font.font("Century Gothic", UNIT / 3));
        setupTimer.setTextFill(new Color(0.9, 0.5, 0.0, 1.0));
        setupTimer.setAlignment(Pos.TOP_LEFT);
        headerText.add(setupTimer, 0, 1);
        
        GridPane headerPane = new GridPane();
        headerPane.getColumnConstraints().
                add(new ColumnConstraints(UNIT * 5));
        headerPane.add(logo, 0, 0);
        headerPane.add(headerLine, 1, 0);
        headerPane.add(headerText, 2, 0);
        
        add(headerPane, 0, 0);
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                          C R E A T E   U I :   B O D Y                        *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        GridPane piecePane = new GridPane();
        // TODO Create class to encapsulate pieceImages and pieceCount.
        ImageView[] pieceImages = new ImageView[12];
        Label[] pieceCount = new Label[12];
        
        GridPane.setMargin(piecePane, new Insets(0.0, 0.0, 0.0, UNIT * 0.15));
        
        // Get player color.
        String playerColor = Game.getPlayer().getColor().toString();
        
        // Image constants suffixes.
        String[] tempValues = new String[] { "02",   "03",   "04",   "05",   "06",   "07", 
                                             "08",   "09",   "10", "BOMB",  "SPY", "FLAG" };
        
        for (int i = 0; i < 12; ++i) {
            // Piece images.
            pieceImages[i] = new ImageView(ImageConstants.
                    PIECE_MAP.get(playerColor + "_" + tempValues[i]));
            piecePane.add(pieceImages[i], i, 0);
            pieceImages[i].setFitHeight(UNIT * 0.8);
            pieceImages[i].setFitWidth(UNIT * 0.8);
            
            // Piece count.
            pieceCount[i] = new Label(" x" + (i % 9 + 1));
            pieceCount[i].setFont(Font.font("Century Gothic", UNIT * 0.4));
            pieceCount[i].setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
            piecePane.add(pieceCount[i], i, 1);
        }
        
        add(piecePane, 0, 1);
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                       C R E A T E   U I :   F O O T E R                       *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        GridPane instructionPane = new GridPane();
        GridPane.setMargin(instructionPane, new Insets(UNIT * 0.2, 0.0, 0.0, 0));
        
        // Add instructions.
        Label instructions = new Label("place a piece: select a piece above and click on the board\n" +
                                       "   remove a piece: click on an existing piece on the board");
        
        // Text properties.
        instructions.setFont(Font.font("Century Gothic", UNIT * 0.3));
        instructions.setTextFill(new Color(1.0, 0.7, 0.0, 1.0));
        
        instructionPane.add(instructions, 0, 0);
        instructionPane.setAlignment(Pos.CENTER);
        
        add(instructionPane, 0, 2);
    }
}