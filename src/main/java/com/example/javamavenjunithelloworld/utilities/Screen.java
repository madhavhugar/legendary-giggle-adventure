package com.example.javamavenjunithelloworld.utilities;

import java.util.concurrent.TimeUnit;

public class Screen {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public static void clearScreen() {
        miniDelay(2);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearScreen(long delay) {
        miniDelay(delay);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void miniDelay(long delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
