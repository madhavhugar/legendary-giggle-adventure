package com.example.javamavenjunithelloworld.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositionTest {

    @Test
    public void testPositionEquality() {
        Position positionOne = new Position(1,1);
        Position positionTwo = new Position(1,1);
        Position positionThree = new Position(0,0);

        assertThat("should match for exact positions",
                positionOne.equals(positionTwo));

        assertThat("should not match for different positions",
                !positionOne.equals(positionThree));
    }
}
