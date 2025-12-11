package org.snake;

/**
 * Gère l'affichage du jeu
 */
public class Screen {
    private static final char WALL_SYMBOL = 'X';
    private static final char EMPTY_SYMBOL = ' ';
    private static final String CLEAR_SCREEN = "\033[H\033[2J";

    private final int height;
    private final int width;

    public Screen(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void clear() {
        System.out.print(CLEAR_SCREEN);
        System.out.flush();
    }

    public void render(Snake snake, Food food, Score score) {
        StringBuilder display = new StringBuilder();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Position currentPos = new Position(row, col);
                char symbol = getSymbolAt(currentPos, snake, food);
                display.append(symbol);
            }
            display.append("\n");
        }

        clear();
        System.out.println(display.toString());
        System.out.println(score.toString());
    }

    private char getSymbolAt(Position position, Snake snake, Food food) {
        if (food.isAt(position)) {
            return food.getSymbol();
        }

        if (snake.occupies(position)) {
            return snake.getSymbol();
        }

        if (isWall(position)) {
            return WALL_SYMBOL;
        }

        return EMPTY_SYMBOL;
    }

    private boolean isWall(Position position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row == 0 || row == height - 1 || col == 0 || col == width - 1;
    }

    public boolean isOutOfBounds(Position position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row <= 0 || row >= height - 1 || col <= 0 || col >= width - 1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}