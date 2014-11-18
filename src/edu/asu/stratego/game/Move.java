package edu.asu.stratego.game;

public class Move {
    
    private int rowStart;
    private int colStart;
    private int rowEnd;
    private int colEnd;
    
    /**
     * Creates a new instance of Move.
     * 
     * @param rowStart
     * @param colStart
     * @param rowEnd
     * @param colEnd
     */
    public Move(int rowStart, int colStart, int rowEnd, int colEnd) {
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowEnd   = rowEnd;
        this.colEnd   = colEnd;
    }
}