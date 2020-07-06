package com.game.legendarygiggle.menu;

import java.io.InputStream;

/**
 * MenuInterface to render menus
 */
public interface MenuInterface {
    /**
     * Render a menu within a loop with an option set,
     * which also includes exit option
     * @param in InputStream object to read user input, ideally use System.in
     */
    void render(InputStream in);
}
