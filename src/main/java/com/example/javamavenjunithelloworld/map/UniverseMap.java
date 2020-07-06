package com.example.javamavenjunithelloworld;


import com.example.javamavenjunithelloworld.utilities.Matrix;
import com.example.javamavenjunithelloworld.utilities.Screen;

import java.io.Serializable;

public class UniverseMap implements Serializable {
    private static final long serialVersionUID = 103L;
    private int [][] galaxyMatrix;
    private String [][] planetNames;

    private final String EMPTY_SPACE = "            ";

    public UniverseMap(int [][] galaxyMatrix, String [][] planetNames) {
        this.galaxyMatrix = galaxyMatrix;
        this.planetNames = planetNames;
    }

    public int getPlanetType(Position position) {
        if (isValidMapPosition(position)) {
            return this.galaxyMatrix[position.x][position.y];
        }
        return Planet.UNIVERSE_VOID;
    }

    public String getLocalVision(Position playerPosition) {
        int i = playerPosition.x;
        int j = playerPosition.y;
        Position invalidPosition = new Position(-1, -1);

        String mapBoundary = String.format("\n" +
                Screen.ANSI_YELLOW +
                "--------------------------------------" +
                Screen.ANSI_RESET + "\n");
        String emptySpace = printBasedOnPlanetType(
                getPlanetType(invalidPosition),
                getPlanetName(invalidPosition));

        Position north = new Position(i-1, j);
        String northPlanet = printBasedOnPlanetType(
                getPlanetType(north),
                getPlanetName(north));

        Position midEast = new Position(i,j-1);
        String midEastPlanet = printBasedOnPlanetType(
                getPlanetType(midEast),
                getPlanetName(midEast));

        Position mid = new Position(i, j);
        String midPlanet =  printBasedOnPlanetType(
                getPlanetType(mid),
                Screen.ANSI_BLACK_BACKGROUND +
                        getPlanetName(mid) +
                        Screen.ANSI_RESET);

        Position midWest = new Position(i,j+1);
        String midWestPlanet = printBasedOnPlanetType(
                getPlanetType(midWest),
                getPlanetName(midWest));

        Position south = new Position(i+1, j);
        String southPlanet = printBasedOnPlanetType(
                getPlanetType(south),
                getPlanetName(south));

        return mapBoundary + "\n" +
                emptySpace + northPlanet + emptySpace + "\n" +
                midEastPlanet + midPlanet + midWestPlanet + "\n" +
                emptySpace + southPlanet + emptySpace + "\n" +
                mapBoundary + "\n";
    }

    public String printBasedOnPlanetType(int planetType, String planetName) {
        switch (planetType) {
            case Planet.HOME:
                return printHomePlanet(planetName);
            case Planet.ALLY:
                return printAllyPlanet(planetName);
            case Planet.NEUTRAL:
                return printNeutralPlanet(planetName);
            case Planet.ENEMY:
                return printEnemyPlanet(planetName);
            case Planet.UNIVERSE_VOID:
                return printUniverseVoid();
            default:
                return String.format("?%s?", planetName);
        }
    }

    public Position getPositionByDirection(Position currentPosition, Direction direction) {
        Position updatedCoordinate;

        switch (direction) {
            case WEST:
                updatedCoordinate = new Position(currentPosition.x, currentPosition.y - 1);
                if (isValidMapPosition(updatedCoordinate)) {
                    return updatedCoordinate;
                }
                return currentPosition;
            case SOUTH:
                updatedCoordinate = new Position(currentPosition.x + 1, currentPosition.y);
                if (isValidMapPosition(updatedCoordinate)) {
                    return updatedCoordinate;
                }
                return currentPosition;
            case NORTH:
                updatedCoordinate = new Position(currentPosition.x - 1, currentPosition.y);
                if (isValidMapPosition(updatedCoordinate)) {
                    return updatedCoordinate;
                }
                return currentPosition;
            case EAST:
                updatedCoordinate = new Position(currentPosition.x, currentPosition.y + 1);
                if (isValidMapPosition(updatedCoordinate)) {
                    return updatedCoordinate;
                }
                return currentPosition;
            default:
                return currentPosition;
        }
    }

    private boolean isValidMapPosition(Position position) {
        return Matrix.isValidSquareMatrixIndices(
                position.x,
                position.y,
                this.galaxyMatrix.length);
    }

    private String getPlanetName(Position position) {
        if (isValidMapPosition(position)) {
            return this.planetNames[position.x][position.y];
        }
        return EMPTY_SPACE;
    }

    private String printAllyPlanet(String planetName) {
        return String.format("[%10s]", Screen.ANSI_BLUE + planetName + Screen.ANSI_RESET);
    }

    private String printNeutralPlanet(String planetName) {
        return String.format("[%10s]", Screen.ANSI_WHITE + planetName + Screen.ANSI_RESET);
    }

    private String printEnemyPlanet(String planetName) {
        return String.format("[%10s]", Screen.ANSI_RED + planetName + Screen.ANSI_RESET);
    }

    private String printHomePlanet(String planetName) {
        return String.format("[%10s]", Screen.ANSI_GREEN + planetName + Screen.ANSI_RESET);
    }

    private String printUniverseVoid() {
        return String.format("%s", EMPTY_SPACE);
    }
}
