package com.example.javamavenjunithelloworld;

public interface AdventureInterface {
    boolean move(Direction direction);
    String getDescription();
    String showPlayerVision();
    String getStatistics();
    void endGame();
    boolean save(String filename);
}
