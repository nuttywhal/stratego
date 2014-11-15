package edu.asu.stratego.game;

import edu.asu.stratego.game.board.Board;

/**
 * Contains information about the Stratego game, which is shared between the 
 * JavaFX GUI and the ClientGameManager.
 * 
 * @see edu.asu.stratego.gui.ClientStage
 * @see edu.asu.stratego.game.ClientGameManager
 */
public class Game {
    private static Player player;
    private static Player opponent;
    
    private static GameStatus status;
    private static PieceColor turn;
    private static Board board;
    
    /**
     * Initializes data fields for a new game.
     */
    public Game() {
        player   = new Player();
        opponent = new Player();
        
        status = GameStatus.SETTING_UP;
        turn   = PieceColor.RED;
        
        board = new Board();
    }

    /**
     * @return Player object containing information about the player.
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * @param player Player object containing information about the player.
     */
    public static void setPlayer(Player player) {
        Game.player = player;
    }

    /**
     * @return Player object containing information about the opponent.
     */
    public static Player getOpponent() {
        return opponent;
    }

    /**
     * @param opponent Player object containing information about the opponent.
     */
    public static void setOpponent(Player opponent) {
        Game.opponent = opponent;
    }

    /**
     * @return value the status of the game.
     */
    public static GameStatus getStatus() {
        return status;
    }

    /**
     * @param status the status of the game
     */
    public static void setStatus(GameStatus status) {
        Game.status = status;
    }

    /**
     * @return value the color of the current player's turn
     */
    public static PieceColor getTurn() {
        return turn;
    }

    /**
     * @param turn the color of the current player's turn
     */
    public static void setTurn(PieceColor turn) {
        Game.turn = turn;
    }

    /**
     * @return the game board.
     */
    public static Board getBoard() {
        return board;
    }

    /**
     * @param board the game board
     */
    public static void setBoard(Board board) {
        Game.board = board;
    }
}