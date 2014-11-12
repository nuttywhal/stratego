package edu.asu.stratego.gui.board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.PieceColor;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;

public class SetupPanel extends GridPane {
    
    public SetupPanel() {
        final double UNIT = ClientStage.getUnit();
        
        // Panel background.
        this.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/setup_panel.png); " + 
                      "-fx-background-size: " + UNIT * 10 + " " + UNIT * 5 + ";" +
                      "-fx-background-repeat: stretch;");
        
        // Create header UI.
        ImageView logo = new ImageView(ImageConstants.stratego_logo);
        
        HBox header = new HBox();
        GridPane headerPane = new GridPane();
        headerPane.getColumnConstraints().add(new ColumnConstraints(UNIT * 5));
        
        // Stratego logo.
        GridPane.setMargin(logo, new Insets(UNIT * 0.15, 0, 0, UNIT * 0.3));
        logo.setFitHeight(UNIT * 1.25);
        logo.setFitWidth(UNIT * 4.4);
        headerPane.add(logo, 0, 0);
        
        // Header line.
        Rectangle headerLine = new Rectangle(UNIT * 0.04, UNIT * 1.25);
        headerLine.setFill(new Color(0.38, 0.11, 0.0, 1.0));
        headerPane.add(headerLine, 1, 0);
        
        // Nicknames + Setup Timer.
        GridPane headerTitle = new GridPane();
        GridPane.setMargin(headerTitle, new Insets(UNIT * 0.2, 0, 0, UNIT * 0.2));
        headerTitle.getRowConstraints().add(new RowConstraints(UNIT * 0.6));
        
        String titleContent = Game.getPlayer().getNickname() + " vs. " + Game.getOpponent().getNickname();
        double titleLen = (titleContent.length() - 7) / 8 + 2;
        System.out.println(titleContent.length());
        Label nameTitle = new Label(titleContent);
        nameTitle.setTextFill(new Color(1.0, 0.73, 0.0, 1.0));
        nameTitle.setFont(Font.font("Century Gothic", FontWeight.BOLD, UNIT / titleLen));
        nameTitle.setAlignment(Pos.BOTTOM_LEFT);
        
        // Timer label
        headerTitle.add(nameTitle, 0, 0);
        Label setupTimer = new Label("time to start: 2:34");
        setupTimer.setFont(Font.font("Century Gothic", UNIT / 3));
        setupTimer.setTextFill(new Color(0.9, 0.55, 0.0, 1.0));
        
        setupTimer.setAlignment(Pos.TOP_LEFT);
        headerTitle.add(setupTimer, 0, 1);
        headerPane.add(headerTitle, 2, 0);
        
        header.getChildren().add(headerPane);
        add(header, 0, 0);
        
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        
        ImageView scout = new ImageView();
        ImageView miner = new ImageView();
        ImageView sergeant = new ImageView();
        ImageView lieutenant = new ImageView();
        ImageView captain = new ImageView();
        ImageView major = new ImageView();
        ImageView colonel = new ImageView();
        ImageView general = new ImageView();
        ImageView marshal = new ImageView();
        ImageView bomb = new ImageView();
        ImageView spy = new ImageView();
        ImageView flag = new ImageView();
        
        GridPane piecePanel = new GridPane();
        if (Game.getPlayer().getColor() == PieceColor.RED) {
            scout.setImage(ImageConstants.red_02);
            miner.setImage(ImageConstants.red_03);
            sergeant.setImage(ImageConstants.red_04);
            lieutenant.setImage(ImageConstants.red_05);
            captain.setImage(ImageConstants.red_06);
            major.setImage(ImageConstants.red_07);
            colonel.setImage(ImageConstants.red_08);
            general.setImage(ImageConstants.red_09);
            marshal.setImage(ImageConstants.red_10);
            bomb.setImage(ImageConstants.red_bomb);
            spy.setImage(ImageConstants.red_spy);
            flag.setImage(ImageConstants.red_flag);
        }
        else {
            scout.setImage(ImageConstants.blue_02);
            miner.setImage(ImageConstants.blue_03);
            sergeant.setImage(ImageConstants.blue_04);
            lieutenant.setImage(ImageConstants.blue_05);
            captain.setImage(ImageConstants.blue_06);
            major.setImage(ImageConstants.blue_07);
            colonel.setImage(ImageConstants.blue_08);
            general.setImage(ImageConstants.blue_09);
            marshal.setImage(ImageConstants.blue_10);
            bomb.setImage(ImageConstants.blue_bomb);
            spy.setImage(ImageConstants.blue_spy);
            flag.setImage(ImageConstants.blue_flag);
        }
        
        piecePane1.add(scout, 1, 0)
    }
}