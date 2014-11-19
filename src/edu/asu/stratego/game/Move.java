package edu.asu.stratego.game;

import java.awt.Point;
import java.io.Serializable;

public class Move implements Serializable {

	private static final long serialVersionUID = -8315478849105334331L;
	
	private Point start = new Point(-1, -1);
	private Point end   = new Point(-1, -1);
	
	private int rowStart = -1;
    private int colStart = -1;
    private int rowEnd = -1;
    private int colEnd = -1;
    
    public boolean isPieceSelected() {
    	return (start.x != -1 && start.y != -1);
    }
    
    public int getRowStart() {
    	return start.x;
    }
    
    public int getColStart() {
    	return start.y;
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
    
    public void setStart(int rowStart, int colStart) {
    	start = new Point(rowStart, colStart);
    }
    
    public void setEnd(int rowEnd, int colEnd) {
    	end = new Point(rowEnd, colEnd);
    }
}