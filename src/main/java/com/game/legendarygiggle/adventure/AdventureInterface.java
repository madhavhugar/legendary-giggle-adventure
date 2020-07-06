package com.game.legendarygiggle.adventure;

import com.game.legendarygiggle.adventure.map.Direction;

public interface AdventureInterface {
    boolean move(Direction direction);
    String getDescription();
    String showPlayerVision();
    String getStatistics();
    void endGame();
    boolean save(String filename);
}
