package edu.asu.stratego.game;

/**
 * Represents the different Stratego pieces.
 */
public enum PieceType {
    SCOUT(2, 8),
    MINER(3, 5),
    SERGEANT(4, 4),
    LIEUTENANT(5, 4),
    CAPTAIN(6, 4),
    MAJOR(7, 3),
    COLONEL(8, 2),
    GENERAL(9, 1),
    MARSHAL(10, 1),
    BOMB(-1, 6),
    SPY(-1, 1),
    FLAG(-1, 1);
    
    private int value;
    private int count;
    
    /**
     * Creates a new instance of PieceType.
     * 
     * @param value the piece value
     * @param count number of pieces of this type a player has initially
     */
    PieceType(int value, int count) {
        this.value = value;
        this.count = count;
    }
    
    /**
     * @return initial count of piece type
     */
    public int getCount() {
        return count;
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

        // Defender is Marshal (10) and Attacker is Spy
        else if(defender == PieceType.MARSHAL && this == PieceType.SPY) {
        	return BattleOutcome.WIN;
        }
        
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