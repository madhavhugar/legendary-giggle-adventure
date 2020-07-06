package com.game.legendarygiggle.adventure;

import com.game.legendarygiggle.adventure.map.Direction;

/**
 * AdventureInterface to include basic operations of a move based Adventure
 */
public interface AdventureInterface {
    /**
     * Get the description of the current game
     */
    String getDescription();
    /**
     * Get the current vision the player can see in game
     */
    String showPlayerVision();
    /**
     * Get the current game statistics
     */
    String getStatistics();
    /**
     * Move the player in a certain direction
     * @param direction Direction object to move the player in a specified direction
     */
    boolean move(Direction direction);
    /**
     * Save adventure which can be resumed later
     * @param filename
     */
    boolean save(String filename);
    /**
     * Render end game graphics
     */
    void endGame();
}
