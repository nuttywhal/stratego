package edu.asu.stratego.game;

public class Move {
    
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