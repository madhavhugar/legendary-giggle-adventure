package com.example.javamavenjunithelloworld;


import com.example.javamavenjunithelloworld.map.Position;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 102L;
    private String name;
    private int health;
    private int experience;
    private boolean alive;
    private Position currentPosition;
    public static final int MIN_HEALTH = 0;
    public static final int MAX_HEALTH = 100;

    public Player(String name) {
        this.name = name;
        this.health = MAX_HEALTH;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= MIN_HEALTH) {
            this.alive = false;
            this.health = MIN_HEALTH;
        } else if (health > MAX_HEALTH) {
            this.health = MAX_HEALTH;
        } else {
            this.health = health;
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position position) {
        this.currentPosition = position;
    }

    public String getPlayerStats() {
        return String.format("Health: %d%%\tExperience: %d XP", health, experience);
    }
}
