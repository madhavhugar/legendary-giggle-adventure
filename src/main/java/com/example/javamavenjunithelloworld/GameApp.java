package com.example.javamavenjunithelloworld;

import org.apache.commons.lang3.tuple.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.HashMap;

/**
 * A very basic program that demonstrates the use of JUnit tests. The tests include a sample unit test and an
 * integration test.
 */
public class GameApp {
    public static Map<String, Pair<String, BooleanSupplier>> menuItems;

    static int [][] basicMap = {
            {1, 2, 0},
            {1, 2, 1},
            {2, 1, 3},
    };
    static String [][] planets = {
            {"Mercury", "Venus", "Earth"},
            {"Gaspra", "Mars", "Eros"},
            {"Jupiter", "Pandia", "Valetudo"},
    };
    static UniverseMap map = new UniverseMap(basicMap, planets);

    private static void startNewAdventure() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nStarting a new adventure...");
        Utilities.clearScreen(1);
        System.out.println("\nEnter your character's name:\n");
        String characterName = input.nextLine();
        Player player = new Player(characterName);
        System.out.println("\nLoading " + player.name + "'s Adventure...\n");
        Utilities.clearScreen(2);
        Adventure adventure = new Adventure(player, map);
        InGameMenu gameMenu = new InGameMenu(adventure);
        gameMenu.render();
    }

    private static void loadAdventure() {
        try {
            FileInputStream file = new FileInputStream(Utilities.SAVE_GAME_FILE);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Adventure savedAdventure = (Adventure)in.readObject();
            in.close();
            file.close();
            InGameMenu gameMenu = new InGameMenu(savedAdventure);
            gameMenu.render();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not find any saved adventure to resume...");
        }
    }

    public static void main(String[] args) {
        menuItems = new HashMap<>();

        menuItems.put("1", Pair.of("1. Start a new adventure", () -> {
            startNewAdventure();
            return false;
        }));
        menuItems.put("2", Pair.of("2. Resume adventure", () -> {
            System.out.println("\nLoading adventure...");
            Utilities.clearScreen(1);
            loadAdventure();
            return false;
        }));
        menuItems.put("3", Pair.of("3. Exit", () -> {
            System.out.println("\nExiting game...");
            return true;
        }));
        menuItems.put(Utilities.INVALID_OPTION, Pair.of("", () -> {
            System.out.println("\nInvalid option selected, select a valid option..\n");
            return false;
        }));

        MainMenu menu = new MainMenu("Rick and Morty Adventures", menuItems);
        menu.render();
    }
}
