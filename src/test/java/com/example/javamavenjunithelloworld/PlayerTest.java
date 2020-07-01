package com.example.javamavenjunithelloworld;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for Player.
 * <p/>
 */
public class PlayerTest {

    @Test
    public void testPlayerHealth() {
        Player p = new Player("Player1");

        p.setHealth(90);
        int wanted = 90;
        assertThat(p.getHealth(), is(equalTo(wanted)));

        p.setHealth(110);
        wanted = 100;
        assertThat(p.getHealth(), is(equalTo(wanted)));

        p.setHealth(-10);
        wanted = 0;
        assertThat(p.getHealth(), is(equalTo(wanted)));
    }

    @Test
    public void testPlayerAlive() {
        Player p = new Player("Player1");

        boolean wanted = true;
        assertThat(p.isAlive(), is(equalTo(wanted)));

        wanted = false;
        p.setHealth(-10);
        assertThat(p.isAlive(), is(equalTo(wanted)));
    }

    @Test
    public void testPlayerExperience() {
        Player p = new Player("Player1");

        int wanted = 120;
        p.setExperience(120);
        assertThat(p.getExperience(), is(equalTo(wanted)));

        wanted = -120;
        p.setExperience(-120);
        assertThat(p.getExperience(), is(equalTo(wanted)));
    }
}
