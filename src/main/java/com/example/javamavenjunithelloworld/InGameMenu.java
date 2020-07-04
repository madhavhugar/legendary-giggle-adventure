package com.example.javamavenjunithelloworld;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BooleanSupplier;


public class InGameMenu implements Menu {
    private String description;
    private Map<String, Pair<String, BooleanSupplier>> menuItems;
    private Adventure adventure;

    public InGameMenu(Adventure adventure) {
        this.adventure = adventure;
        this.description = adventure.getDescription();
        menuItems = new HashMap<>();
        menuItems.put("d", Pair.of("d: moveEast", () -> {
            System.out.println("\nMoving East...");
            return checkGameStatus(adventure.move(Direction.EAST));
        }));
        menuItems.put("w", Pair.of("w: moveNorth", () -> {
            System.out.println("\nMoving North...");
            return checkGameStatus(adventure.move(Direction.NORTH));
        }));
        menuItems.put("a", Pair.of("a: moveWest", () -> {
            System.out.println("\nMoving West...");
            return checkGameStatus(adventure.move(Direction.WEST));
        }));
        menuItems.put("s", Pair.of("s: moveSouth", () -> {
            System.out.println("\nMoving South...");
            return checkGameStatus(adventure.move(Direction.SOUTH));
        }));
        menuItems.put("save", Pair.of("save: saveAdventure", () -> {
            try {
                adventure.save();
            } catch (IOException e) {
                System.out.println("Could not save the game!" + e);
            }
            return false;
        }));
        menuItems.put("exit", Pair.of("exit: exitAdventure", () -> {
            System.out.println("\nExiting adventure...");
            return true;
        }));
        menuItems.put(Utilities.INVALID_OPTION, Pair.of("", () -> {
            System.out.println("\nInvalid option selected, select a valid option..\n");
            return false;
        }));
    }

    private boolean checkGameStatus(boolean isGameOver) {
        if (isGameOver) {
            renderEndGame();
        }
        return isGameOver;
    }

    private void renderEndGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\n\nthat was a quick ending to your adventure, perhaps you pushed it a little too far...");
        System.out.println("\n\n \t\t\t xxx Game Over xxx\n\nPress any key to continue");
        input.nextLine();
    }

    private void printMenu() {
        System.out.println("\n" + description + "\n\n");
        for (Pair<String, BooleanSupplier> item: menuItems.values()) {
            if (!(item.getLeft() == "")) {
                System.out.println(item.getLeft());
            }
        }
        adventure.showPlayerVision();
        System.out.println("\n" + adventure.getStatistics() + "\n");
    }

    private String acceptInput() {
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        if (!menuItems.containsKey(userInput)) {
            return Utilities.INVALID_OPTION;
        }
        return userInput;
    }

    private boolean processInput(String userInput) {
        return menuItems.get(userInput)
                .getRight()
                .getAsBoolean();
    }

    @Override
    public void render() {
        boolean done = false;
        while(!done) {
            printMenu();
            String userInput = acceptInput();
            done = processInput(userInput);
            Utilities.clearScreen(1);
        }
    }
}
