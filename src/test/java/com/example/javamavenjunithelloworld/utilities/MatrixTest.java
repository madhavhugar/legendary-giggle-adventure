package com.example.javamavenjunithelloworld.utilities;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MatrixTest {

    @Test
    public void testIsValidSquareMatrixIndices() {
        boolean got = Matrix.isValidSquareMatrixIndices(0, 0, 10);
        assertThat("should return true for valid matrix position",
                got == true);

        got = Matrix.isValidSquareMatrixIndices(-1, -1, 10);
        assertThat("should return false for invalid matrix position",
                got == false);

        got = Matrix.isValidSquareMatrixIndices(11, 12, 10);
        assertThat("should return false for invalid matrix position",
                got == false);
    }
}
