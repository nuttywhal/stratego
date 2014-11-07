package edu.asu.stratego.game;

import java.io.Serializable;

import edu.asu.stratego.game.PieceColor;

public class Player implements Serializable {
    
    private static final long serialVersionUID = 649459794036226272L;
    private String nickname;
    private PieceColor color;
    
    /**
     * Sets the player's nickname.
     * @param nickname the player's name
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    /**
     * Returns the player's nickname.
     * @return a String containing the player's name
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * Sets the player's (army) color.
     * @param color the player's piece (army) color
     */
    public void setColor(PieceColor color) {
        this.color = color;
    }
    
    /**
     * Returns the player's (army) color.
     * @return a PieceColor representing the player (army) color
     */
    public PieceColor getColor() {
        return color;
    }
}