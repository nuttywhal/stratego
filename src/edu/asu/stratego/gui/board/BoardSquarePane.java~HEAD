package edu.asu.stratego.gui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import edu.asu.stratego.gui.board.BoardSquareType;

/**
 * JavaFX StackPane to graphically represent the individual squares of the 
 * Stratego board.
 */
public class BoardSquarePane extends StackPane {
    
    private ImageView piece = new ImageView();
    
    /**
     * Creates a new instance of BoardSquare.
     * @param type the BoardSquareType of the BoardSquare
     */
    public BoardSquarePane(BoardSquareType type) {
        // Background image.
        if (type == BoardSquareType.LIGHT)
            this.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/grass1.png)");
        else if (type == BoardSquareType.DARK)
            this.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/grass2.png)");
        
        this.getChildren().add(piece);
    }
    
    /**
     * Returns the ImageView representing the piece.
     * @return ImageView of the piece at this Square.
     */
    public ImageView getPiece() {
        return piece;
    }
    
    /**
     * Sets the ImageView representing the piece.
     * @param ImageView of the piece at this Square.
     */
    public void setPiece(Image p) {
        piece.setImage(p);
    }
}