package com.example.javamavenjunithelloworld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AdventureTest {
    private static Adventure adventure;

    @BeforeEach
    public void setUp() {
        int [][] planetMap = {
                {1, 0},
                {2, 3},
        };
        String [][] planets = {
                {"Mercury", "Earth"},
                {"Gaspra", "Mars"},
        };
        Player player = new Player("testPlayer");
        UniverseMap universeMap = new UniverseMap(planetMap, planets);
        adventure = new Adventure(player, universeMap);
    }

    @Test
    public void testGetDescription() {
        String got = adventure.getDescription();
        String wanted = "testPlayer's adventure";
        assertThat(got, is(equalTo(wanted)));
    }

    @AfterEach
    void tearDown() {
    }
}