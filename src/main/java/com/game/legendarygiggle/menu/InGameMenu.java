package com.game.legendarygiggle.menu;

import com.game.legendarygiggle.Config;
import com.game.legendarygiggle.adventure.AdventureInterface;
import com.game.legendarygiggle.adventure.map.Direction;
import com.game.legendarygiggle.utilities.Screen;
import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BooleanSupplier;


public class InGameMenu implements MenuInterface {
    private String description;
    private Map<String, Pair<String, BooleanSupplier>> menuItems;
    private AdventureInterface adventure;

    public InGameMenu(AdventureInterface adventure) {
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
        menuItems.put("save", Pair.of("save: saveAdventure", () ->
            adventure.save(Config.SAVE_GAME_FILE)));
        menuItems.put("exit", Pair.of("exit: exitAdventure", () -> {
            System.out.println("\nExiting adventure...");
            return true;
        }));
        menuItems.put(Config.INVALID_OPTION, Pair.of("", () -> {
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
        adventure.endGame();
    }

    private void printMenu() {
        System.out.println("\n" + description + "\n\n");
        for (Pair<String, BooleanSupplier> item: menuItems.values()) {
            if (!(item.getLeft() == "")) {
                System.out.println(item.getLeft());
            }
        }
        String playerVision = adventure.showPlayerVision();
        System.out.println(playerVision);
        System.out.println("\n" + adventure.getStatistics() + "\n");
    }

    private String acceptInput(Scanner input) {
        String userInput = input.nextLine();
        if (!menuItems.containsKey(userInput)) {
            return Config.INVALID_OPTION;
        }
        return userInput;
    }

    private boolean processInput(String userInput) {
        return menuItems.get(userInput)
                .getRight()
                .getAsBoolean();
    }

    @Override
    public void render(InputStream in) {
        Scanner input = new Scanner(in);
        boolean done = false;
        while(!done) {
            printMenu();
            String userInput = acceptInput(input);
            done = processInput(userInput);
            Screen.clearScreen(1);
        }
    }
}
