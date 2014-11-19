package edu.asu.stratego.game;

import java.io.Serializable;

/**
 * Represents a single game piece.
 */
public class Piece implements Serializable {

    private static final long serialVersionUID = 7193334048398155856L;
    
    private PieceColor color;
    private PieceType  type;
    
    private boolean isOpponentPiece;
    private String   spriteKey;
    
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
                case SCOUT:      this.spriteKey = "RED_02";   break;
                case MINER:      this.spriteKey = "RED_03";   break;
                case SERGEANT:   this.spriteKey = "RED_04";   break;
                case LIEUTENANT: this.spriteKey = "RED_05";   break;
                case CAPTAIN:    this.spriteKey = "RED_06";   break;
                case MAJOR:      this.spriteKey = "RED_07";   break;
                case COLONEL:    this.spriteKey = "RED_08";   break;
                case GENERAL:    this.spriteKey = "RED_09";   break;
                case MARSHAL:    this.spriteKey = "RED_10";   break;
                case BOMB:       this.spriteKey = "RED_BOMB"; break;
                case FLAG:       this.spriteKey = "RED_FLAG"; break;
                case SPY:        this.spriteKey = "RED_SPY";  break;
                default:                                      break;
            }
            
            if (this.isOpponentPiece)
                this.spriteKey = "RED_BACK";
        }
        
        else {
            switch (type) {
                case SCOUT:      this.spriteKey = "BLUE_02";   break;
                case MINER:      this.spriteKey = "BLUE_03";   break;
                case SERGEANT:   this.spriteKey = "BLUE_04";   break;
                case LIEUTENANT: this.spriteKey = "BLUE_05";   break;
                case CAPTAIN:    this.spriteKey = "BLUE_06";   break;
                case MAJOR:      this.spriteKey = "BLUE_07";   break;
                case COLONEL:    this.spriteKey = "BLUE_08";   break;
                case GENERAL:    this.spriteKey = "BLUE_09";   break;
                case MARSHAL:    this.spriteKey = "BLUE_10";   break;
                case BOMB:       this.spriteKey = "BLUE_BOMB"; break;
                case FLAG:       this.spriteKey = "BLUE_FLAG"; break;
                case SPY:        this.spriteKey = "BLUE_SPY";  break;
                default:                                       break;
            }
            
            if (this.isOpponentPiece)
                this.spriteKey = "BLUE_BACK";
        }
    }
    
    /**
     * @return the piece type of the piece.
     */
    public PieceType getPieceType() {
        return type;
    }
    
    public PieceColor getPieceColor() {
        return color;
    }
    
    /**
     * @return the sprite associated with the type of the piece.
     */
    public String getPieceSpriteKey() {
        return spriteKey;
    }
}