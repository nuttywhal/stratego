package edu.asu.stratego.gui.board.setup;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import edu.asu.stratego.gui.ClientStage;

public class SetupTimer {
    private static final int START_TIME = 300;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty secondsLeft = 
            new SimpleIntegerProperty(START_TIME);
    
    public SetupTimer() {
        final double UNIT = ClientStage.getUnit();
        
        timerLabel.textProperty().bind(secondsLeft.asString());
        timerLabel.setFont(Font.font("Century Gothic", UNIT / 3));
        timerLabel.setTextFill(new Color(0.9, 0.5, 0.0, 1.0));
        timerLabel.setAlignment(Pos.TOP_LEFT);
    }
    
    private class StartTimer implements Runnable {
        @Override
        public void run() {
            secondsLeft.set(START_TIME);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(START_TIME + 1),
                    new KeyValue(secondsLeft, 0)));
            timeline.playFromStart();
        }
    }
    
    public void startTimer() {
        Thread startTimer = new Thread(new StartTimer());
        startTimer.setDaemon(true);
        startTimer.start();
    }
    
    public Label getLabel() {
        return timerLabel;
    }
}