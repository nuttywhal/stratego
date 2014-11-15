package edu.asu.stratego.game;

import java.io.Serializable;

import edu.asu.stratego.game.PieceColor;

/**
 * Contains information about a player.
 */
public class Player implements Serializable {
    
    private static final long serialVersionUID = 649459794036226272L;
    private String nickname;
    private PieceColor color;
    
    /**
     * @param nickname the player's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    /**
     * @return a String containing the player's name
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * @param color the player's color
     */
    public void setColor(PieceColor color) {
        this.color = color;
    }
    
    /**
     * @return the player's color
     */
    public PieceColor getColor() {
        return color;
    }
}