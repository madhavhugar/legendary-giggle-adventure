package com.example.javamavenjunithelloworld;


import java.io.Serializable;

public class UniverseMap implements Serializable {
    int [][] galaxyMatrix;
    String [][] planetNames;

    private final String EMPTY_SPACE = "            ";

    public UniverseMap(int [][] galaxyMatrix, String [][] planetNames) {
        this.galaxyMatrix = galaxyMatrix;
        this.planetNames = planetNames;
    }

    public void showLocalVision(Position playerPosition) {
        int i = playerPosition.x;
        int j = playerPosition.y;
        Position invalidPosition = new Position(-1, -1);

        System.out.println("\n" + Utilities.ANSI_YELLOW + "--------------------------------------" + Utilities.ANSI_RESET + "\n");
        printBasedOnPlanetType(getPlanetType(invalidPosition), getPlanetName(invalidPosition));
        printBasedOnPlanetType(getPlanetType(i-1, j), getPlanetName(i-1, j));
        printBasedOnPlanetType(getPlanetType(invalidPosition), getPlanetName(invalidPosition));
        System.out.println("\n");
        printBasedOnPlanetType(getPlanetType(i,j-1), getPlanetName(i, j-1));
        printBasedOnPlanetType(getPlanetType(i, j), Utilities.ANSI_BLACK_BACKGROUND + getPlanetName(i, j) + Utilities.ANSI_RESET);
        printBasedOnPlanetType(getPlanetType(i,j+1), getPlanetName(i, j+1));
        System.out.println("\n");
        printBasedOnPlanetType(getPlanetType(invalidPosition), getPlanetName(invalidPosition));
        printBasedOnPlanetType(getPlanetType(i+1, j), getPlanetName(i+1, j));
        printBasedOnPlanetType(getPlanetType(invalidPosition), getPlanetName(invalidPosition));
        System.out.println();
        System.out.println("\n" + Utilities.ANSI_YELLOW + "--------------------------------------" + Utilities.ANSI_RESET + "\n");
    }

    private boolean isValidMapPosition(int i, int j) {
        int minimumIndex = 0;
        int maximumIndex = this.galaxyMatrix.length;
        if ((minimumIndex <= i) && (i < maximumIndex) && (minimumIndex <= j) && (j < maximumIndex)) {
            return true;
        }
        return false;
    }

    public int getPlanetType(int i, int j) {
        if (isValidMapPosition(i, j)) {
            return this.galaxyMatrix[i][j];
        }
        return Planet.UNIVERSE_VOID;
    }

    public int getPlanetType(Position position) {
        if (isValidMapPosition(position.x, position.y)) {
            return this.galaxyMatrix[position.x][position.y];
        }
        return Planet.UNIVERSE_VOID;
    }

    private String getPlanetName(int i, int j) {
        if (isValidMapPosition(i, j)) {
            return this.planetNames[i][j];
        }
        return EMPTY_SPACE;
    }

    private String getPlanetName(Position position) {
        if (isValidMapPosition(position.x, position.y)) {
            return this.planetNames[position.x][position.y];
        }
        return EMPTY_SPACE;
    }

    private void printAllyPlanet(String planetName) {
        System.out.printf("[%10s]", Utilities.ANSI_BLUE + planetName + Utilities.ANSI_RESET);
    }

    private void printNeutralPlanet(String planetName) {
        System.out.printf("[%10s]", Utilities.ANSI_WHITE + planetName + Utilities.ANSI_RESET);
    }

    private void printEnemyPlanet(String planetName) {
        System.out.printf("[%10s]", Utilities.ANSI_RED + planetName + Utilities.ANSI_RESET);
    }

    private void printHomePlanet(String planetName) {
        System.out.printf("[%10s]", Utilities.ANSI_GREEN + planetName + Utilities.ANSI_RESET);
    }

    private void printUniverseVoid() {
        System.out.printf("%s", EMPTY_SPACE);
    }

    private void printBasedOnPlanetType(int planetType, String planetName) {
        switch (planetType) {
            case Planet.HOME:
                printHomePlanet(planetName);
                break;
            case Planet.ALLY:
                printAllyPlanet(planetName);
                break;
            case Planet.NEUTRAL:
                printNeutralPlanet(planetName);
                break;
            case Planet.ENEMY:
                printEnemyPlanet(planetName);
                break;
            case Planet.UNIVERSE_VOID:
                printUniverseVoid();
                break;
            default:
                System.out.printf("?%s?", planetName);
        }
    }

    public Position getPositionByDirection(Position currentPosition, Direction direction) {
        int updatedCoordinate;
        Position newPosition = new Position(currentPosition);

        switch (direction) {
            case WEST:
                 updatedCoordinate = currentPosition.y - 1;
                if (isValidMapPosition(currentPosition.x, updatedCoordinate)) {
                    newPosition.y = updatedCoordinate;
                }
                return newPosition;
            case SOUTH:
                updatedCoordinate = currentPosition.x + 1;
                if (isValidMapPosition(updatedCoordinate, currentPosition.y)) {
                    newPosition.x = updatedCoordinate;
                }
                return newPosition;
            case NORTH:
                updatedCoordinate = currentPosition.x - 1;
                if (isValidMapPosition(updatedCoordinate, currentPosition.y)) {
                    newPosition.x = updatedCoordinate;
                }
                return newPosition;
            case EAST:
                updatedCoordinate = currentPosition.y + 1;
                if (isValidMapPosition(currentPosition.x, updatedCoordinate)) {
                    newPosition.y = updatedCoordinate;
                }
                return newPosition;
            default:
                return newPosition;
        }
    }

    private void showMap() {
        System.out.println();
        for(int i=0; i < galaxyMatrix.length; i++) {
            for(int j=0; j < galaxyMatrix[i].length; j++) {
                printBasedOnPlanetType(galaxyMatrix[i][j], planetNames[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
