package edu.asu.stratego.gui.board;

import edu.asu.stratego.media.ImageConstants;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardSquareEventPane extends GridPane {
    
    private ImageView hover;
    
    public BoardSquareEventPane() {
        hover = new ImageView(ImageConstants.HIGHLIGHT_NONE);
        hover.addEventHandler(MouseEvent.MOUSE_ENTERED, new OnHover());
        hover.addEventHandler(MouseEvent.MOUSE_EXITED, new OffHover());
    }
    
    private class OnHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            hover.setImage(ImageConstants.HIGHLIGHT_VALID);
        };
    }
    
    private class OffHover implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            ImageView hover = (ImageView) e.getSource();
            hover.setImage(ImageConstants.HIGHLIGHT_NONE);
        };
    }
}