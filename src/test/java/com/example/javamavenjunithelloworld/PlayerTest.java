package com.example.javamavenjunithelloworld;

import com.example.javamavenjunithelloworld.map.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for Player.
 */
public class PlayerTest {
    private static Player p;

    @BeforeEach
    public void beforeEach() {
        p = new Player("Player1");
    }

    @Test
    public void testSetPlayerHealth() {
        p.setHealth(90);
        int wanted = 90;
        assertThat("should update health within range MIN_HEALTH - MAX_HEALTH",
                p.getHealth(),
                is(equalTo(wanted)));

        p.setHealth(110);
        wanted = Player.MAX_HEALTH;
        assertThat("should limit health increment to MAX_HEALTH",
                p.getHealth(),
                is(equalTo(wanted)));

        p.setHealth(-10);
        wanted = Player.MIN_HEALTH;
        assertThat("should limit health decrement to MIN_HEALTH",
                p.getHealth(),
                is(equalTo(wanted)));
    }

    @Test
    public void testUpdateIsAlive() {
        assertThat("should ensure player is alive when created",
                p.isAlive(),
                is(equalTo(true)));

        p.setHealth(-10);
        assertThat("should ensure player is not alive when health is below MIN_HEALTH",
                p.isAlive(),
                is(equalTo(false)));
    }

    @Test
    public void testSetPlayerExperience() {
        int wanted = 120;
        p.setExperience(120);
        assertThat("should update experience to positive amount",
                p.getExperience(),
                is(equalTo(wanted)));

        wanted = -120;
        p.setExperience(-120);
        assertThat("should update experience to negative amount",
                p.getExperience(),
                is(equalTo(wanted)));
    }

    @Test
    public void testGetPlayerStats() {
        String wanted = String.format(
                "Health: %d%%\tExperience: %d XP",
                p.getHealth(),
                p.getExperience());
        String got = p.getPlayerStats();
        assertThat(got, is(equalTo(wanted)));
    }

    @Test
    public void testGetAndSetPlayerCurrentPosition() {
        Position setPosition = new Position(1,1);
        p.setCurrentPosition(setPosition);
        assertThat("should update the player position",
                p.getCurrentPosition().equals(setPosition));
    }
}
