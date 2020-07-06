package com.game.legendarygiggle.utilities;

public class Matrix {
    public static boolean isValidSquareMatrixIndices(int i, int j, int maxLength) {
        int minimumIndex = 0;
        int maximumIndex = maxLength;
        if ((minimumIndex <= i) && (i < maximumIndex) && (minimumIndex <= j) && (j < maximumIndex)) {
            return true;
        }
        return false;
    }
}
