package com.example.javamavenjunithelloworld;

import com.example.javamavenjunithelloworld.map.Position;
import com.example.javamavenjunithelloworld.map.UniverseMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class AdventureTest {
    private static Adventure adventure;
    private static Player player;
    private static UniverseMap universeMap;

    private static Throwable exceptionOf(Callable callable) {
        try {
            callable.call();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }

    @BeforeEach
    public void beforeEach() {
        int [][] planetMap = {
                {1, 0},
                {2, 3},
        };
        String [][] planets = {
                {"Mercury", "Earth"},
                {"Gaspra", "Mars"},
        };
        player = new Player("testPlayer");
        universeMap = new UniverseMap(planetMap, planets);
        adventure = new Adventure(player, universeMap);
    }

    @Test
    public void shouldGetDescription() {
        String got = adventure.getDescription();
        String wanted = "testPlayer's adventure";
        assertThat(got, is(equalTo(wanted)));
    }

    @Test
    public void testGetStatistics() {
        String got = adventure.getStatistics();
        String wanted = player.getPlayerStats();
        assertThat(got, is(equalTo(wanted)));
    }

    @Test
    public void testIsAdventureOver() {
        boolean got = adventure.move(Direction.WEST);
        assertThat(got, is(equalTo(false)));

        player.setHealth(Player.MIN_HEALTH);
        got = adventure.move(Direction.WEST);
        assertThat(got, is(equalTo(true)));
    }

    @Test
    public void testMoveOnInvalidPosition() {
        int initialHealth = player.getHealth();
        int initialExperience = player.getExperience();
        Position initialPosition = player.getCurrentPosition();
        // At the beginning of the game, moving WEST would be an invalid position
        Direction invalidDirection = Direction.WEST;
        adventure.move(invalidDirection);
        assertThat("should not change health on invalid move",
                player.getHealth(),
                is(equalTo(initialHealth)));
        assertThat("should not experience health on invalid move",
                player.getExperience(),
                is(equalTo(initialExperience)));
        assertThat("should not change player position on invalid move",
                player.getCurrentPosition(),
                is(equalTo(initialPosition)));
    }

    @Test
    public void testMoveToEnemyPlanet() {
        int initialHealth = player.getHealth();
        int initialExperience = player.getExperience();
        Position initialPosition = player.getCurrentPosition();
        adventure.move(Direction.SOUTH);
        assertThat("should decrease health",
                player.getHealth() < initialHealth);
        assertThat("should increase experience",
                player.getExperience() > initialExperience);
        assertThat("should change the player position",
                player.getCurrentPosition(),
                is(not(equalTo(initialPosition))));
    }

    @Test
    public void testMoveToHomePlanet() {
        int initialHealth = 80;
        player.setHealth(initialHealth);
        int initialExperience = player.getExperience();
        Position initialPosition = player.getCurrentPosition();
        adventure.move(Direction.EAST);
        assertThat("should update health",
                player.getHealth(),
                is(not(equalTo(initialHealth))));
        assertThat("should increase health to MAX_HEALTH",
                player.getHealth(),
                is(equalTo(Player.MAX_HEALTH)));
        assertThat("should not update experience",
                player.getExperience(),
                is(equalTo(initialExperience)));
        assertThat("should change the player position",
                player.getCurrentPosition(),
                is(not(equalTo(initialPosition))));
    }

    @Test
    public void testMoveToAllyPlanet() {
        int [][] planetMap = {
                {3, 1},
                {1, 1},
        };
        String [][] planets = {
                {"Neutral", "Ally"},
                {"Ally", "Ally"},
        };
        universeMap = new UniverseMap(planetMap, planets);
        adventure = new Adventure(player, universeMap);

        int initialHealth = 50;
        int initialExperience = player.getExperience();
        Position initialPosition = player.getCurrentPosition();
        player.setHealth(initialHealth);
        adventure.move(Direction.EAST);
        assertThat("should increase health on ally planet",
                player.getHealth() > initialHealth);
        assertThat("should increase experience on ally planet",
                player.getExperience() > initialExperience);
        assertThat(player.getCurrentPosition(), is(not(equalTo(initialPosition))));
    }

    @Test
    public void testSaveSuccessful() {
        File file = new File(Config.SAVE_GAME_FILE);
        int initialHealth = 80;
        int initialExperience = 22;
        player.setHealth(initialHealth);
        player.setExperience(initialExperience);
        String initialStatistics = adventure.getStatistics();

        boolean isAdventureEnded = adventure.save(Config.SAVE_GAME_FILE);
        assertThat("should create a serialized file",
                file.exists());
        assertThat("should not exit the game on save",
                isAdventureEnded == false);

        // reload the saved object
        Adventure savedAdventure = adventure.load(Config.SAVE_GAME_FILE);
        adventure = savedAdventure;
        assertThat("should ensure player has same health after loading",
                (adventure.getStatistics()).equals(initialStatistics));
    }

    @Test
    public void testSaveUnsuccessful() {
        assertThat("should thow when invalid filename is passed to save",
                exceptionOf(() -> {
            boolean isAdventureEnded = adventure.save("");
            assertThat("should not exit the game on unsuccessful save",
                    isAdventureEnded == false);
            return isAdventureEnded;
        }), is(instanceOf(NullPointerException.class)));
    }

    @Test
    public void testLoadUnsuccessful() {
        assertThat("should return null when a non existent adventure is loaded",
                adventure.load("non-existent.ser"),
                is(equalTo(null)));
    }

    @AfterEach
    public void tearDown() {
        File file = new File(Config.SAVE_GAME_FILE);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }
}