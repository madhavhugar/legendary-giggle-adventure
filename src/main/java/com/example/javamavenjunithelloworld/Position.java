package com.example.javamavenjunithelloworld;

import java.io.Serializable;

public class Position implements Serializable {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public boolean equals(Position position) {
        if (this == position) {
            return true;
        }
        if (this.x == position.x && this.y == position.y) {
            return true;
        }
        return false;
    }
}
