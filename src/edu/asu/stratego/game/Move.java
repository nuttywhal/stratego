package edu.asu.stratego.game;

import java.io.Serializable;

public class Move implements Serializable {

	private static final long serialVersionUID = -8315478849105334331L;
	
	private int rowStart = -1;
    private int colStart = -1;
    private int rowEnd = -1;
    private int colEnd = -1;
    
    public boolean isPieceSelected() {
    	return (rowStart != -1 && colStart != -1);
    }
    
    public int getRowStart() {
    	return rowStart;
    }
    
    public int getColStart() {
    	return colStart;
    }
    
    public void setStart(int rowStart, int colStart) {
    	this.rowStart = rowStart;
    	this.colStart = colStart;
    }
    
    public void setEnd(int rowEnd, int colEnd) {
    	this.rowEnd = rowEnd;
    	this.colEnd = colEnd;
    }
}