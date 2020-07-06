package com.game.legendarygiggle.adventure.map;

import com.game.legendarygiggle.utilities.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class UniverseMapTest {
    private static UniverseMap universeMap;
    private static int [][] planetMap;
    private static String [][] planets;
    private static String mapBoundary = String.format("\n" +
            Screen.ANSI_YELLOW +
            "--------------------------------------" +
            Screen.ANSI_RESET + "\n");

    @BeforeEach
    public void beforeEach() {
        planetMap = new int[][]{
                {1, 2, 0},
                {0, 2, 1},
                {2, 3, 3},
        };
        planets = new String[][]{
                {"Mercury", "Venus", "Gaspra"},
                {"Earth", "Mars", "Eros"},
                {"Jupiter", "Pandia", "Valetudo"},
        };
        universeMap = new UniverseMap(planetMap, planets);
    }

    @Test
    public void testGetLocalVision() {
        Position position = new Position(1,1);
        String got = universeMap.getLocalVision(position);
        String emptySpace = universeMap.printBasedOnPlanetType(
                Planet.UNIVERSE_VOID,
                "");
        String wanted = mapBoundary + "\n" + emptySpace +
                universeMap.printBasedOnPlanetType(Planet.ENEMY, "Venus")
                + emptySpace + "\n" +
                universeMap.printBasedOnPlanetType(Planet.HOME, "Earth") +
                universeMap.printBasedOnPlanetType(
                        Planet.ENEMY, Screen.ANSI_BLACK_BACKGROUND +
                        "Mars" +
                        Screen.ANSI_RESET
                ) +
                universeMap.printBasedOnPlanetType(Planet.ALLY, "Eros") +
                "\n" + emptySpace +
                universeMap.printBasedOnPlanetType(Planet.NEUTRAL, "Pandia") +
                emptySpace + "\n" + mapBoundary + "\n";

        assertThat("should get the local vision of a given Position",
                (got).equals(wanted));
    }

    @Test
    public void testGetPositionByDirection() {
        Position currentPosition = new Position(1,1);
        Position south = new Position(2,1);
        Position got = universeMap.getPositionByDirection(currentPosition, Direction.SOUTH);
        assertThat("should move south",
                got.equals(south));

        Position north = new Position(0,1);
        got = universeMap.getPositionByDirection(currentPosition, Direction.NORTH);
        assertThat("should move north",
                got.equals(north));

        Position west = new Position(1,0);
        got = universeMap.getPositionByDirection(currentPosition, Direction.WEST);
        assertThat("should move west",
                got.equals(west));

        Position east = new Position(1,2);
        got = universeMap.getPositionByDirection(currentPosition, Direction.EAST);
        assertThat("should move east",
                got.equals(east));
    }

    @Test
    public void testGetPositionByDirectionMapBoundary() {
        Position currentPosition = new Position(2,2);
        Position stationary = new Position(2,2);
        Position got = universeMap.getPositionByDirection(currentPosition, Direction.SOUTH);
        assertThat("should not move beyond the map boundary (SOUTH)",
                got.equals(stationary));

        currentPosition = new Position(0,0);
        stationary = new Position(0,0);
        got = universeMap.getPositionByDirection(currentPosition, Direction.NORTH);
        assertThat("should not move beyond map boundary (NORTH)",
                got.equals(stationary));

        currentPosition = new Position(0,0);
        stationary = new Position(0,0);
        got = universeMap.getPositionByDirection(currentPosition, Direction.WEST);
        assertThat("should not move beyond map boundary (WEST)",
                got.equals(stationary));

        currentPosition = new Position(0,2);
        stationary = new Position(0,2);
        got = universeMap.getPositionByDirection(currentPosition, Direction.EAST);
        assertThat("should not move beyond map boundary (EAST)",
                got.equals(stationary));
    }

    @Test
    public void testPrintBasedOnPlanetType() {
        String planetName = "RandomPlanet";
        String got = universeMap.printBasedOnPlanetType(999, planetName);
        String wanted = String.format("?%s?", planetName);
        assertThat("should return default planet string",
                got.equals(wanted));

        got = universeMap.printBasedOnPlanetType(Planet.HOME, planetName);
        wanted = String.format("[%10s]", Screen.ANSI_GREEN + planetName + Screen.ANSI_RESET);
        assertThat("should return Home planet string",
                got.equals(wanted));

        got = universeMap.printBasedOnPlanetType(Planet.ALLY, planetName);
        wanted = String.format("[%10s]", Screen.ANSI_BLUE + planetName + Screen.ANSI_RESET);
        assertThat("should return Ally planet string",
                got.equals(wanted));

        got = universeMap.printBasedOnPlanetType(Planet.ENEMY, planetName);
        wanted = String.format("[%10s]", Screen.ANSI_RED + planetName + Screen.ANSI_RESET);
        assertThat("should return Enemy planet string",
                got.equals(wanted));

        got = universeMap.printBasedOnPlanetType(Planet.NEUTRAL, planetName);
        wanted = String.format("[%10s]", Screen.ANSI_WHITE + planetName + Screen.ANSI_RESET);
        assertThat("should return Neutral planet string",
                got.equals(wanted));
    }
}
