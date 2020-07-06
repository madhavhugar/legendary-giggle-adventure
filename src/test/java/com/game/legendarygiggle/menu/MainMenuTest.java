package com.game.legendarygiggle.menu;

import com.game.legendarygiggle.Config;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainMenuTest {
    private static MenuInterface mainMenu;
    private static Map<String, Pair<String, BooleanSupplier>> menuItems;
    private static ByteArrayInputStream in;

    private static boolean optionOneInvoked;
    private static int optionOneInvokedCount;

    private static boolean invalidOptionInvoked;
    private static int invalidOptionInvokedCount;

    @BeforeEach
    public void beforeEach() {
        menuItems = new HashMap<>();
        optionOneInvoked = false;
        optionOneInvokedCount = 0;

        invalidOptionInvoked = false;
        invalidOptionInvokedCount = 0;

        menuItems.put("1", Pair.of("1. Option one", () -> {
            optionOneInvoked = true;
            optionOneInvokedCount++;
            return true;
        }));
        menuItems.put(Config.INVALID_OPTION, Pair.of("", () -> {
            invalidOptionInvoked = true;
            invalidOptionInvokedCount++;
            return true;
        }));

        mainMenu = new MainMenu("testTitle", menuItems);
    }

    private void simulateUserInput(String userInput) {
        in = new ByteArrayInputStream(userInput.getBytes());
    }

    @Test
    public void testSelectingMenuItem() {
        simulateUserInput("1");
        mainMenu.render(in);

        assertThat("should invoke corresponding menuItem",
                (optionOneInvoked && (optionOneInvokedCount == 1)));
    }

    @Test
    public void testSelectingInvalidMenuItem() {
        simulateUserInput("non-existent");
        mainMenu.render(in);

        assertThat("should invoke default menuItem handler",
                (invalidOptionInvoked && (invalidOptionInvokedCount == 1)));
    }
}
