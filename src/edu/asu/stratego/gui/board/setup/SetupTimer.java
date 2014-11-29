package edu.asu.stratego.gui.board.setup;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.gui.board.BoardSquareEventPane;

/**
 * A setup timer. This timer counts down from a start time.
 */
public class SetupTimer {
    private static final int START_TIME = 300;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty secondsLeft = 
            new SimpleIntegerProperty(START_TIME);
    
    /**
     * Creates a new instance of SetupTimer.
     */
    public SetupTimer() {
        final double UNIT = ClientStage.getUnit();
        
        timerLabel.textProperty().bind(secondsLeft.asString());
        timerLabel.setFont(Font.font("Century Gothic", UNIT / 3));
        timerLabel.setTextFill(new Color(0.9, 0.5, 0.0, 1.0));
        timerLabel.setAlignment(Pos.TOP_LEFT);
    }
    
    /**
     * Task to start the timer and count down from the start time.
     */
    private class StartTimer implements Runnable {
        @Override
        public void run() {
            secondsLeft.set(START_TIME);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(START_TIME + 1),
                    new KeyValue(secondsLeft, 0)));
            timeline.playFromStart();
            timeline.setOnFinished(new TimerFinished());
        }
    }
    
    /**
     * Creates a new thread to start the timer task.
     */
    public void startTimer() {
        Thread startTimer = new Thread(new StartTimer());
        startTimer.setDaemon(true);
        startTimer.start();
    }
    
    /**
     * @return JavaFX label that displays how many seconds are left in the 
     * timer.
     */
    public Label getLabel() {
        return timerLabel;
    }
    
    /**
     * Executes when the timer finishes counting down to zero.
     */
    private class TimerFinished implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BoardSquareEventPane.randomSetup();
        }
    }
}