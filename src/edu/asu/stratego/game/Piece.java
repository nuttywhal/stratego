package edu.asu.stratego.game;

import javafx.scene.image.Image;

import edu.asu.stratego.media.ImageConstants;

/**
 * Represents a single game piece.
 */
public class Piece {
    private PieceColor color;
    private PieceType  type;
    
    private boolean isOpponentPiece;
    private Image   sprite;
    
    /**
     * Creates a new instance of Piece.
     * 
     * @param type PieceType of the piece
     * @param color color of the piece
     * @param isOpponentPiece whether or not the piece belongs to the opponent
     */
    public Piece(PieceType type, PieceColor color, boolean isOpponentPiece) {
        this.isOpponentPiece = isOpponentPiece;
        this.color = color;
        this.type  = type;
        setPieceImage();
    }
    
    /**
     * Sets the Piece's image sprite according to the type of the piece, the 
     * player's color, and whether or not the piece belongs to the opponent.
     */
    private void setPieceImage() {
        if (this.color == PieceColor.RED) {
            switch (type) {
                case SCOUT:      this.sprite = ImageConstants.RED_02;   break;
                case MINER:      this.sprite = ImageConstants.RED_03;   break;
                case SERGEANT:   this.sprite = ImageConstants.RED_04;   break;
                case LIEUTENANT: this.sprite = ImageConstants.RED_05;   break;
                case CAPTAIN:    this.sprite = ImageConstants.RED_06;   break;
                case MAJOR:      this.sprite = ImageConstants.RED_07;   break;
                case COLONEL:    this.sprite = ImageConstants.RED_08;   break;
                case GENERAL:    this.sprite = ImageConstants.RED_09;   break;
                case MARSHAL:    this.sprite = ImageConstants.RED_10;   break;
                case BOMB:       this.sprite = ImageConstants.RED_BOMB; break;
                case FLAG:       this.sprite = ImageConstants.RED_FLAG; break;
                case SPY:        this.sprite = ImageConstants.RED_SPY;  break;
                default:                                               break;
            }
            
            if (this.isOpponentPiece)
                this.sprite = ImageConstants.RED_BACK;
        }
        
        else {
            switch (type) {
                case SCOUT:      this.sprite = ImageConstants.BLUE_02;   break;
                case MINER:      this.sprite = ImageConstants.BLUE_03;   break;
                case SERGEANT:   this.sprite = ImageConstants.BLUE_04;   break;
                case LIEUTENANT: this.sprite = ImageConstants.BLUE_05;   break;
                case CAPTAIN:    this.sprite = ImageConstants.BLUE_06;   break;
                case MAJOR:      this.sprite = ImageConstants.BLUE_07;   break;
                case COLONEL:    this.sprite = ImageConstants.BLUE_08;   break;
                case GENERAL:    this.sprite = ImageConstants.BLUE_09;   break;
                case MARSHAL:    this.sprite = ImageConstants.BLUE_10;   break;
                case BOMB:       this.sprite = ImageConstants.BLUE_BOMB; break;
                case FLAG:       this.sprite = ImageConstants.BLUE_FLAG; break;
                case SPY:        this.sprite = ImageConstants.BLUE_SPY;  break;
                default:                                                break;
            }
            
            if (this.isOpponentPiece)
                this.sprite = ImageConstants.BLUE_BACK;
        }
    }
    
    /**
     * @return the piece type of the piece.
     */
    public PieceType getPieceType() {
        return type;
    }
    
    /**
     * @return the sprite associated with the type of the piece.
     */
    public Image getPieceSprite() {
        return sprite;
    }
}