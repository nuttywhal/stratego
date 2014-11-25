package edu.asu.stratego.gui.board.setup;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import edu.asu.stratego.game.ClientGameManager;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.media.PlaySound;

/**
 * The panel that is shown during the SETTING_UP phase of a Stratego game.
 * Players interact with this panel to set up their pieces to their starting 
 * positions.
 */
public class SetupPanel {
    
    private static GridPane  setupPanel        = new GridPane();
    private static GridPane  piecePane         = new GridPane();
    private static Object    updateReadyStatus = new Object();
    private static StackPane instructionPane   = new StackPane();
    private static Label     instructions      = new Label();
    private static Label     readyLabel        = new Label();
    private static ImageView readyButton       = new ImageView();
    
    /**
     * Creates a new instance of SetupPanel.
     */
    public SetupPanel() {
        final double UNIT = ClientStage.getUnit();
        
        setupPanel.setMaxHeight(UNIT * 4);
        setupPanel.setMaxWidth(UNIT * 10);
        
        // Panel background.
        String backgroundURL = "edu/asu/stratego/media/images/board/setup_panel.png";
        setupPanel.setStyle("-fx-background-image: url(" + backgroundURL + "); " + 
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
        Label setupTimer = new Label("Setup Time Left: ");
        setupTimer.setFont(Font.font("Century Gothic", UNIT / 3));
        setupTimer.setTextFill(new Color(0.9, 0.5, 0.0, 1.0));
        setupTimer.setAlignment(Pos.TOP_LEFT);
        
        SetupTimer timer = new SetupTimer();
        timer.startTimer();
        
        GridPane timerPane = new GridPane();
        timerPane.add(setupTimer, 0, 1);
        timerPane.add(timer.getLabel(), 1, 1);
        
        headerText.add(timerPane, 0, 1);
        
        GridPane headerPane = new GridPane();
        headerPane.getColumnConstraints().
                add(new ColumnConstraints(UNIT * 5));
        headerPane.add(logo, 0, 0);
        headerPane.add(headerLine, 1, 0);
        headerPane.add(headerText, 2, 0);
        
        setupPanel.add(headerPane, 0, 0);
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                          C R E A T E   U I :   B O D Y                        *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        SetupPieces pieces = new SetupPieces();
        ImageView[] pieceImages = pieces.getPieceImages();
        Label[] pieceCount = pieces.getPieceCountLabels();
        
        GridPane.setMargin(piecePane, new Insets(UNIT * 0.15, 0.0, 0.0, UNIT * 0.15));
        
        for (int i = 0; i < 12; ++i) {
            piecePane.add(pieceImages[i], i, 0);
            piecePane.add(pieceCount[i], i, 1);
        }
        
        setupPanel.add(piecePane, 0, 1);
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                       C R E A T E   U I :   F O O T E R                       *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        GridPane.setMargin(instructionPane, new Insets(UNIT * 0.15, 0.0, 0.0, 0.0));
        
        // Add instructions.
        instructions.setText("place a piece: select a piece above and click on the board\n" +
                             "   remove a piece: click on an existing piece on the board");
        
        // Ready button + event handlers.
        readyButton.setImage(ImageConstants.READY_IDLE);
        readyButton.setFitHeight(UNIT * 0.75);
        readyButton.setFitWidth(UNIT * 2.25);
        
        readyButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            readyButton.setImage(ImageConstants.READY_HOVER);
        });
        
        readyButton.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, e -> {
            readyButton.setImage(ImageConstants.READY_IDLE);
        });
        
        readyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> { finishSetup(); } );
        });
        
        // Text properties.
        instructions.setFont(Font.font("Century Gothic", UNIT * 0.3));
        instructions.setTextFill(new Color(1.0, 0.7, 0.0, 1.0));
        
        // Worker thread to update the ready button when all of the pieces 
        // have been placed.
        Thread updateButton = new Thread(new UpdateReadyButton());
        updateButton.setDaemon(true);
        updateButton.start();
        
        instructionPane.getChildren().add(instructions);
        instructionPane.setAlignment(Pos.CENTER);
        
        setupPanel.add(instructionPane, 0, 2);
        
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *                                                                               *
         *                        C R E A T E   U I :   R E A D Y                        *
         *                                                                               *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        
        GridPane.setMargin(readyLabel, new Insets(UNIT * 0.8, 0.0, 0.0, UNIT * 1.5));
        readyLabel.setText("Waiting for opponent...");
        readyLabel.setFont(Font.font("Century Gothic", UNIT * 0.6));
        readyLabel.setTextFill(new Color(1.0, 0.7, 0.0, 1.0));
    }
    
    /**
     * @return the object to communicate the status of the setup pieces between 
     * SetupPieces and the SetupPanel so that UpdateReadyButton can decide 
     * whether or not to display the ready button.
     * 
     * @see edu.asu.stratego.gui.board.setup.SetupPanel
     * @see edu.asu.stratego.gui.board.setup.SetupPieces
     * @see edu.asu.stratego.gui.board.setup.SetupPanel.UpdateReadyButton
     */
    public static Object getUpdateReadyStatus() {
        return updateReadyStatus;
    }
    
    /**
     * @return the SetupPanel (JavaFX GridPane)
     */
    public static GridPane getSetupPanel() {
        return setupPanel;
    }
    
    /**
     * When the player has all of their pieces placed on the board and is ready 
     * to start playing, this method is called. Notifies the ClientGameManager 
     * to send the initial piece positions to the server and receive the 
     * opponent's initial piece positions.
     */
    public static void finishSetup() {
        Object setupPieces = ClientGameManager.getSetupPieces();
        
        synchronized (setupPieces) {
            setupPanel.getChildren().remove(instructionPane);
            setupPanel.getChildren().remove(piecePane);
            setupPanel.add(readyLabel, 0, 1);
            
            setupPieces.notify();
        }
    }

    /**
     * Worker task that waits for a Setup Piece to be incremented or 
     * decremented. If all of the pieces have been placed, this task removes 
     * the instructions from the panel and adds the ready button to the panel. 
     * If the ready button is 
     */
    private class UpdateReadyButton implements Runnable {
        @Override
        public void run() {
            // instructionPane should update only when the state is changed.
            boolean readyState = false;
            
            synchronized (updateReadyStatus) {
                while (true) {
                    try {
                        // Wait for piece type to increment / decrement.
                        updateReadyStatus.wait();
                    
                        // Remove instructions, add ready button.
                        if (SetupPieces.getAllPiecesPlaced() && !readyState) {
                            Platform.runLater(() -> {
                                instructionPane.getChildren().remove(instructions);
                                instructionPane.getChildren().add(readyButton);
                            });
                            
                            readyState = true;
                        }
                        
                        // Remove ready button, add instructions.
                        else if (!SetupPieces.getAllPiecesPlaced() && readyState) {
                            Platform.runLater(() -> {
                                instructionPane.getChildren().remove(readyButton);
                                instructionPane.getChildren().add(instructions);
                            });
                            
                            readyState = false;
                        }
                    }
                    catch (InterruptedException e) {
                        // TODO Handle this exception somehow...
                    }
                }
            }
        }
    }
}