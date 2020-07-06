package com.example.javamavenjunithelloworld;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.example.javamavenjunithelloworld.utilities.Screen;
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

    private String acceptInput(InputStream in) {
        Scanner input = new Scanner(in); // TODO move scanner to render ideally or use as class property
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
        boolean done = false;
        while(!done) {
            printMenu();
            String userInput = acceptInput(in);
            done = processInput(userInput);
            Screen.clearScreen();
        }
    }
}
