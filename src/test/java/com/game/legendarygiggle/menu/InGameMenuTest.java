package com.game.legendarygiggle.menu;


import com.game.legendarygiggle.Config;
import com.game.legendarygiggle.adventure.AdventureInterface;
import com.game.legendarygiggle.adventure.map.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class InGameMenuTest {
    private static InGameMenu inGameMenu;
    private static AdventureInterface adventure;
    private static ByteArrayInputStream in;

    private static boolean moveInvoked;
    private static int moveInvokedCount;
    private static Direction moveInvokedWith;

    private static boolean saveInvoked;
    private static int saveInvokedCount;
    private static String saveInvokedWith;

    class AdventureMock implements AdventureInterface {

        @Override
        public boolean move(Direction direction) {
            moveInvoked = true;
            moveInvokedCount++;
            moveInvokedWith = direction;
            return true;
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public String showPlayerVision() {
            return "";
        }

        @Override
        public String getStatistics() {
            return "";
        }

        @Override
        public void endGame() {
            return;
        }

        @Override
        public boolean save(String filename) {
            saveInvokedCount++;
            saveInvoked = true;
            saveInvokedWith = filename;
            return true;
        }
    }

    @BeforeEach
    public void beforeEach() {
        adventure = new AdventureMock();
        inGameMenu = new InGameMenu(adventure);

        moveInvokedCount = 0;
        moveInvoked = false;
        moveInvokedWith = null;

        saveInvokedCount = 0;
        saveInvoked = false;
        saveInvokedWith = null;
    }

    private void simulateUserInput(String userInput) {
        in = new ByteArrayInputStream(userInput.getBytes());
    }

    @Test
    public void testMoveInputTowardsNorth() {
        simulateUserInput("w");
        inGameMenu.render(in);

        assertThat("should invoke move once with NORTH",
                ((moveInvokedCount == 1) &&
                        moveInvoked &&
                        (moveInvokedWith == Direction.NORTH)));
    }

    @Test
    public void testMoveInputTowardsSouth() {
        simulateUserInput("s");
        inGameMenu.render(in);

        assertThat("should invoke move once with SOUTH",
                ((moveInvokedCount == 1) &&
                        moveInvoked &&
                        (moveInvokedWith == Direction.SOUTH)));
    }

    @Test
    public void testMoveInputTowardsEast() {
        simulateUserInput("d");
        inGameMenu.render(in);

        assertThat("should invoke move once with EAST",
                ((moveInvokedCount == 1) &&
                        moveInvoked &&
                        (moveInvokedWith == Direction.EAST)));
    }

    @Test
    public void testMoveInputTowardsWest() {
        simulateUserInput("a");
        inGameMenu.render(in);

        assertThat("should invoke move once with WEST",
                ((moveInvokedCount == 1) &&
                        moveInvoked &&
                        (moveInvokedWith == Direction.WEST)));
    }

    @Test
    public void testSaveInput() {
        simulateUserInput("save");
        inGameMenu.render(in);

        assertThat("should invoke save once with Config.SAVE_GAME_FILE",
                ((saveInvokedCount == 1) &&
                        saveInvoked &&
                        (saveInvokedWith.equals(Config.SAVE_GAME_FILE))));
    }

    @Test
    public void testExitInput() {
        simulateUserInput("exit");
        inGameMenu.render(in);

        assertThat("should end game",
                (!saveInvoked && !moveInvoked));
    }
}
