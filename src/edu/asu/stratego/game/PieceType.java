package edu.asu.stratego.game;

/**
 * Represents the different Stratego pieces.
 */
public enum PieceType {
    MARSHAL(10),
    GENERAL(9),
    COLONEL(8),
    MAJOR(7),
    CAPTAIN(6),
    LIEUTENANT(5),
    SERGEANT(4),
    MINER(3),
    SCOUT(2),
    SPY(-1),
    BOMB(-1),
    FLAG(-1);
    
    private int value;
    
    /**
     * Creates a new instance of PieceType.
     * @param value the piece value
     */
    PieceType(int value) {
        this.value = value;
    }
    
    /**
     * Returns the result of a battle when one piece type attacks another
     * piece type.
     * 
     * @param defender the defending piece type
     * @return the battle outcome
     */
    public BattleOutcome attack(PieceType defender) {
        // Defender is a flag.
        if (defender == PieceType.FLAG)
            return BattleOutcome.WIN;
        
        // Attacking piece and defending piece are the same piece type.
        else if (this.value == defender.value)
            return BattleOutcome.DRAW;
        
        // Defender is a spy.
        else if (defender == PieceType.SPY)
            return BattleOutcome.WIN;
        
        // Defender is a bomb.
        else if (defender == PieceType.BOMB) {
            if (this == PieceType.MINER)
                return BattleOutcome.WIN;
            else
                return BattleOutcome.LOSE;
        }
        
        // Otherwise, compare piece values.
        else if (this.value > defender.value)
            return BattleOutcome.WIN;
        else
            return BattleOutcome.LOSE;
    }
}