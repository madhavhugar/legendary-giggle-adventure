package com.example.javamavenjunithelloworld;


import com.example.javamavenjunithelloworld.map.Position;
import com.example.javamavenjunithelloworld.map.UniverseMap;
import com.example.javamavenjunithelloworld.utilities.Screen;

import java.io.*;


public class Adventure implements Serializable, AdventureInterface {
    private static final long serialVersionUID = 101L;
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

        return isAdventureOver();
    }

    public String showPlayerVision() {
        return universeMap.getLocalVision(player.getCurrentPosition());
    }

    public String getDescription() {
        return String.format("%s's adventure", player.getName());
    }

    public String getStatistics() {
        return player.getPlayerStats();
    }

    public boolean save(String filename) {
        FileOutputStream file = null;
        ObjectOutputStream out = null;
        try {
            System.out.println("Saving adventure...\n");
            file = new FileOutputStream(filename);
            out = new ObjectOutputStream(file);
            out.writeObject(this);
            System.out.println("Done.\n");
        } catch (IOException | NullPointerException e) {
            System.out.println("Could not save the game! " + e);
        } finally {
            try {
                out.close();
                file.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            return false;
        }
    }

    public static Adventure load(String filename) {
        FileInputStream file = null;
        ObjectInputStream in = null;
        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("Could not find any saved adventure to resume... ");
            return  null;
        }
        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            Adventure savedAdventure = (Adventure)in.readObject();
            return savedAdventure;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
                file.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void endGame() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\n\nthat was a quick ending to your adventure, perhaps you pushed it a little too far...");
        System.out.println("\n\n \t\t\t xxx Game Over xxx\n\nPress any key to continue");
        Screen.clearScreen(2);
    }

    private boolean isAdventureOver() {
        if (!player.isAlive()) {
            return true;
        }
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
