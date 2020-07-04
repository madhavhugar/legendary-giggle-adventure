package com.example.javamavenjunithelloworld;


import java.io.Serializable;

public class Player implements Serializable {
    String name;
    private int health;
    private int experience;
    private boolean alive;
    private Position currentPosition;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            this.alive = false;
            this.health = 0;
        } else if (health > 100) {
            this.health = 100;
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
        return "Health: " + health + "%\tExperience: " + experience + " XP";
    }
}
