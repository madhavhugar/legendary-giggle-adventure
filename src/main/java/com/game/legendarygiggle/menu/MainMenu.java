package com.game.legendarygiggle.menu;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.game.legendarygiggle.Config;
import com.game.legendarygiggle.utilities.Screen;
import org.apache.commons.lang3.tuple.Pair;
import java.util.function.BooleanSupplier;


public class MainMenu implements MenuInterface {
    private String gameTitle;
    private Map<String, Pair<String, BooleanSupplier>> menuItems;

    public MainMenu(String title, Map<String, Pair<String, BooleanSupplier>> items) {
        this.gameTitle = title;
        menuItems = new HashMap<>();
        for (String item: items.keySet()) {
            menuItems.put(item, items.get(item));
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println(gameTitle);
        for (Pair<String, BooleanSupplier> item: menuItems.values()) {
            System.out.println(item.getLeft());
        }
        System.out.println();
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
            Screen.clearScreen();
        }
    }
}
