package edu.asu.stratego.game;

import edu.asu.stratego.game.board.Board;

/**
 * Contains information about the Stratego game shared between the JavaFX GUI 
 * and the ClientGameManager.
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
     * Sets up a new game.
     */
    public Game() {
        player   = new Player();
        opponent = new Player();
        
        status = GameStatus.SETTING_UP;
        turn   = PieceColor.RED;
        
        board = new Board();
    }

    /**
     * Returns the Player object of the player.
     * @return Player object containing information about the player.
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Sets the Player object of the player.
     * @param player Player object containing information about the player.
     */
    public static void setPlayer(Player player) {
        Game.player = player;
    }

    /**
     * Returns the Player object of the opponent.
     * @return Player object containing information about the opponent.
     */
    public static Player getOpponent() {
        return opponent;
    }

    /**
     * Sets the Player object of the opponent.
     * @param opponent Player object containing information about the opponent.
     */
    public static void setOpponent(Player opponent) {
        Game.opponent = opponent;
    }

    /**
     * Returns the status of the game.
     * @return value representing the status of the current game.
     */
    public static GameStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the game.
     * @param status the status of the game
     */
    public static void setStatus(GameStatus status) {
        Game.status = status;
    }

    /**
     * Returns the color of the player's turn.
     * @return value representing the color of the current player's turn
     */
    public static PieceColor getTurn() {
        return turn;
    }

    /**
     * Set the color of the player's turn
     * @param turn PieceColor of the player's turn
     */
    public static void setTurn(PieceColor turn) {
        Game.turn = turn;
    }

    /**
     * Returns the game board.
     * @return game board.
     */
    public static Board getBoard() {
        return board;
    }

    /**
     * Sets the game board
     * @param board game board
     */
    public static void setBoard(Board board) {
        Game.board = board;
    }
}