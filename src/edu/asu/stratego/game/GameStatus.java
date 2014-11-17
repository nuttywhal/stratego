package edu.asu.stratego.game;

/**
 * Represents the status of a Stratego game.
 */
public enum GameStatus {
    SETTING_UP,
    WAITING_OPP,
    IN_PROGRESS,
    RED_CAPTURED,
    BLUE_CAPTURED,
    RED_NO_MOVES,
    BLUE_NO_MOVES,
    RED_DISCONNECTED,
    BLUE_DISCONNECTED,
    RED_FLAG_UNREACHABLE,
    BLUE_FLAG_UNREACHABLE;
}