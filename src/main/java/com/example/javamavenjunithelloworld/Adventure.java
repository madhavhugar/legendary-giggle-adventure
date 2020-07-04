package com.example.javamavenjunithelloworld;


import java.io.*;


public class Adventure implements Serializable {

    private Player player;
    private UniverseMap universeMap;

    public Adventure(Player player, UniverseMap universeMap) {
        this.player = player;
        Position position = new Position(0,0);
        player.setCurrentPosition(position);
        this.universeMap = universeMap;
    }

    public boolean move(Direction direction) {
        // Update player position, health and experience
        updatePlayer(direction);

        if (!player.isAlive()) {
            return true;
        }
        return false;
    }

    public void showPlayerVision() {
        universeMap.showLocalVision(player.getCurrentPosition());
    }

    public String getDescription() {
        return player.getName() + "'s adventure";
    }

    public String getStatistics() {
        return player.getPlayerStats();
    }

    public boolean save() throws IOException {
        System.out.println("Saving adventure...\n");
        FileOutputStream file = new FileOutputStream(Utilities.SAVE_GAME_FILE);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
        file.close();
        System.out.println("Done.\n");
        return false;
    }

    private void updatePlayer(Direction direction) {
        boolean isPositionChanged = updatePlayerPosition(direction);
        if (!isPositionChanged) {
            return;
        }
        Position playerPosition = player.getCurrentPosition();
        int planetType = universeMap.getPlanetType(playerPosition);
        updateHealth(planetType);
        updateExperience(planetType);
    }

    private boolean updatePlayerPosition(Direction direction) {
        Position playerPosition = player.getCurrentPosition();
        Position updatedPlayerPosition = universeMap.getPositionByDirection(
                playerPosition,
                direction);
        player.setCurrentPosition(updatedPlayerPosition);
        boolean isPlayerPositionChanged = !playerPosition.equals(updatedPlayerPosition);
        return isPlayerPositionChanged;
    }

    private void updateHealth(int planetType) {
        int currentHealth, updatedHealth;
        switch (planetType) {
            case Planet.HOME:
                player.setHealth(100);
                break;
            case Planet.ALLY:
                currentHealth = player.getHealth();
                updatedHealth = (int) (currentHealth + (currentHealth * 0.1));
                player.setHealth(updatedHealth);
                break;
            case Planet.ENEMY:
                currentHealth = player.getHealth();
                updatedHealth = (int) (currentHealth - (currentHealth * 0.1) - 10);
                player.setHealth(updatedHealth);
                break;
            default:
                break;
        }
    }

    private void updateExperience(int planetType) {
        int currentExperience, updatedExperience;
        switch (planetType) {
            case Planet.HOME:
                player.setHealth(100);
                break;
            case Planet.ALLY:
                currentExperience = player.getExperience();
                updatedExperience = (int) (10 + currentExperience + (currentExperience * 0.1));
                player.setExperience(updatedExperience);
                break;
            case Planet.ENEMY:
                currentExperience = player.getExperience();
                updatedExperience = (int) (10 + currentExperience + 3 * (currentExperience * 0.1));
                player.setExperience(updatedExperience);
                break;
            default:
                break;
        }
    }
}
