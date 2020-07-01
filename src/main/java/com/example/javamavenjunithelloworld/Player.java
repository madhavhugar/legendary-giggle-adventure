package com.example.javamavenjunithelloworld;

public class Player {
    String name;
    private int health;
    private int experience;
    private boolean alive;
//    private powerUp

    // TODO: optionally use builder pattern here
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.alive = true;
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
}
