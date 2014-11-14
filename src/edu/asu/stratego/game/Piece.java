package edu.asu.stratego.game;

import edu.asu.stratego.media.ImageConstants;
import javafx.scene.image.Image;

public class Piece {
    private PieceColor color;
    private PieceType  type;
    
    private boolean isOpponentPiece;
    private Image   image;
    
    /**
     * Creates a new instance of Piece.
     */
    public Piece(PieceType type, PieceColor color, boolean isOpponentPiece) {
        this.isOpponentPiece = isOpponentPiece;
        this.color = color;
        this.type  = type;
        setPieceImage();
    }
    
    /**
     * Set Piece's image sprite according to the type of the piece and whether 
     * or not the piece belongs to the opponent.
     */
    private void setPieceImage() {
        if (this.color == PieceColor.RED) {
            switch (type) {
                case SCOUT:      this.image = ImageConstants.RED_02;   break;
                case MINER:      this.image = ImageConstants.RED_03;   break;
                case SERGEANT:   this.image = ImageConstants.RED_04;   break;
                case LIEUTENANT: this.image = ImageConstants.RED_05;   break;
                case CAPTAIN:    this.image = ImageConstants.RED_06;   break;
                case MAJOR:      this.image = ImageConstants.RED_07;   break;
                case COLONEL:    this.image = ImageConstants.RED_08;   break;
                case GENERAL:    this.image = ImageConstants.RED_09;   break;
                case MARSHAL:    this.image = ImageConstants.RED_10;   break;
                case BOMB:       this.image = ImageConstants.RED_BOMB; break;
                case FLAG:       this.image = ImageConstants.RED_FLAG; break;
                case SPY:        this.image = ImageConstants.RED_SPY;  break;
                default:                                               break;
            }
            
            if (this.isOpponentPiece)
                this.image = ImageConstants.RED_BACK;
        }
        
        else {
            switch (type) {
                case SCOUT:      this.image = ImageConstants.BLUE_02;   break;
                case MINER:      this.image = ImageConstants.BLUE_03;   break;
                case SERGEANT:   this.image = ImageConstants.BLUE_04;   break;
                case LIEUTENANT: this.image = ImageConstants.BLUE_05;   break;
                case CAPTAIN:    this.image = ImageConstants.BLUE_06;   break;
                case MAJOR:      this.image = ImageConstants.BLUE_07;   break;
                case COLONEL:    this.image = ImageConstants.BLUE_08;   break;
                case GENERAL:    this.image = ImageConstants.BLUE_09;   break;
                case MARSHAL:    this.image = ImageConstants.BLUE_10;   break;
                case BOMB:       this.image = ImageConstants.BLUE_BOMB; break;
                case FLAG:       this.image = ImageConstants.BLUE_FLAG; break;
                case SPY:        this.image = ImageConstants.BLUE_SPY;  break;
                default:                                                break;
            }
            
            if (this.isOpponentPiece)
                this.image = ImageConstants.BLUE_BACK;
        }
    }
    
    public PieceType getPieceType() {
        return type;
    }
}