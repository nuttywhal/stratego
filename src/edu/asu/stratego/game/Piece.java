package edu.asu.stratego.game;

import edu.asu.stratego.media.ImageConstants;
import javafx.scene.image.Image;

class Piece {
    private PieceColor color;
    private PieceType  type;
    
    private boolean isOpponentPiece;
    private Image image;
    
    private int row;
    private int col;
    
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
                case SCOUT:      this.image = ImageConstants.red_02;   break;
                case MINER:      this.image = ImageConstants.red_03;   break;
                case SERGEANT:   this.image = ImageConstants.red_04;   break;
                case LIEUTENANT: this.image = ImageConstants.red_05;   break;
                case CAPTAIN:    this.image = ImageConstants.red_06;   break;
                case MAJOR:      this.image = ImageConstants.red_07;   break;
                case COLONEL:    this.image = ImageConstants.red_08;   break;
                case GENERAL:    this.image = ImageConstants.red_09;   break;
                case MARSHAL:    this.image = ImageConstants.red_10;   break;
                case BOMB:       this.image = ImageConstants.red_bomb; break;
                case FLAG:       this.image = ImageConstants.red_flag; break;
                case SPY:        this.image = ImageConstants.red_spy;  break;
                default:                                               break;
            }
            
            if (this.isOpponentPiece)
                this.image = ImageConstants.red_back;
        }
        
        else {
            switch (type) {
                case SCOUT:      this.image = ImageConstants.blue_02;   break;
                case MINER:      this.image = ImageConstants.blue_03;   break;
                case SERGEANT:   this.image = ImageConstants.blue_04;   break;
                case LIEUTENANT: this.image = ImageConstants.blue_05;   break;
                case CAPTAIN:    this.image = ImageConstants.blue_06;   break;
                case MAJOR:      this.image = ImageConstants.blue_07;   break;
                case COLONEL:    this.image = ImageConstants.blue_08;   break;
                case GENERAL:    this.image = ImageConstants.blue_09;   break;
                case MARSHAL:    this.image = ImageConstants.blue_10;   break;
                case BOMB:       this.image = ImageConstants.blue_bomb; break;
                case FLAG:       this.image = ImageConstants.blue_flag; break;
                case SPY:        this.image = ImageConstants.blue_spy;  break;
                default:                                                break;
            }
            
            if (this.isOpponentPiece)
                this.image = ImageConstants.blue_back;
        }
    }
}