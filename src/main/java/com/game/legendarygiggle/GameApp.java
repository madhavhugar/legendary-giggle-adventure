package com.game.legendarygiggle;

import com.game.legendarygiggle.adventure.Adventure;
import com.game.legendarygiggle.adventure.map.UniverseMap;
import com.game.legendarygiggle.adventure.player.Player;
import com.game.legendarygiggle.menu.InGameMenu;
import com.game.legendarygiggle.menu.MainMenu;
import com.game.legendarygiggle.menu.MenuInterface;
import com.game.legendarygiggle.utilities.Screen;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.HashMap;

/**
 * GameApp to orchestrate legendary giggle adventure
 */
public class GameApp {
    public static Map<String, Pair<String, BooleanSupplier>> menuItems;

    static int [][] basicMap = {
            {1, 2, 0, 2, 3, 1},
            {1, 2, 2, 2, 2, 1},
            {2, 1, 3, 2, 3, 3},
            {1, 2, 2, 2, 2, 1},
            {1, 2, 1, 2, 3, 1},
            {2, 1, 3, 2, 3, 3},
    };
    static String [][] planets = {
            {"Mercury", "Venus", "Earth", "Mercitron", "Uranitron", "Fearon"},
            {"Gaspra", "Mars", "Eros", "Astrieon", "Mextron", "Texitron"},
            {"Jupiter", "Pandia", "Valetudo", "Ramonton", "Hellton", "Kirton"},
            {"Soccoalara", "Chilveuc", "Bonoria", "Nongora", "Kunerth", "Cacarro"},
            {"Gnooria", "Bapupra", "Zolla", "Lichi", "Xubenope", "Katrides"},
            {"Thiestea", "Aneron", "Ieclite", "Drabistea", "Giri 60BX", "Zealara"},
    };
    static UniverseMap map = new UniverseMap(basicMap, planets);

    private static Map<String, Pair<String, BooleanSupplier>> getMenuItems() {
        menuItems = new HashMap<>();
        menuItems.put("1", Pair.of("1. Start a new adventure", () -> {
            startNewAdventure();
            return false;
        }));
        menuItems.put("2", Pair.of("2. Resume adventure", () -> {
            System.out.println("\nLoading adventure...");
            Screen.clearScreen(1);
            loadAdventure();
            return false;
        }));
        menuItems.put("3", Pair.of("3. Exit", () -> {
            System.out.println("\nExiting game...");
            return true;
        }));
        menuItems.put(Config.INVALID_OPTION, Pair.of("", () -> {
            System.out.println("\nInvalid option selected, select a valid option..\n");
            return false;
        }));
        return menuItems;
    }

    private static void startNewAdventure() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nStarting a new adventure...");
        Screen.clearScreen(1);
        System.out.println("\nEnter your character's name:\n");
        String characterName = input.nextLine();
        Player player = new Player(characterName);
        System.out.println("\nLoading " + player.getName() + "'s Adventure...\n");
        Screen.clearScreen(2);
        Adventure adventure = new Adventure(player, map);
        InGameMenu gameMenu = new InGameMenu(adventure);
        gameMenu.render(System.in);
    }

    public static void loadAdventure() {
        Adventure savedAdventure = Adventure.load(Config.SAVE_GAME_FILE);
        if (savedAdventure == null) {
            return;
        }
        InGameMenu gameMenu = new InGameMenu(savedAdventure);
        gameMenu.render(System.in);
    }

    public static void main(String[] args) {
        Map<String, Pair<String, BooleanSupplier>> menuItems = getMenuItems();
        MenuInterface menuInterface = new MainMenu("The Legendary Giggle Adventure", menuItems);
        menuInterface.render(System.in);
    }
}
