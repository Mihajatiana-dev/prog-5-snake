package org.snake;

import java.util.Random;

public class Food {
    private static final char FOOD_SYMBOL = '*';
    private Position position;
    private final Random random;
    private final int maxRow;
    private final int maxColumn;

    public Food(int maxRow, int maxColumn) {
        this.maxRow = maxRow;
        this.maxColumn = maxColumn;
        this.random = new Random();
        this.position = generateRandomPosition();
    }

    private Position generateRandomPosition() {
        int row = random.nextInt(maxRow - 2) + 1;
        int column = random.nextInt(maxColumn - 2) + 1;
        return new Position(row, column);
    }

    public void respawn() {
        this.position = generateRandomPosition();
    }

    public char getSymbol() {
        return FOOD_SYMBOL;
    }

    public boolean isAt(Position position) {
        return this.position.equals(position);
    }
}