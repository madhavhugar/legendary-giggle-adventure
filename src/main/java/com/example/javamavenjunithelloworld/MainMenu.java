package com.example.javamavenjunithelloworld;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.lang3.tuple.Pair;
import java.util.function.BooleanSupplier;


public class MainMenu implements Menu {
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
            Utilities.clearScreen();
        }
    }
}
