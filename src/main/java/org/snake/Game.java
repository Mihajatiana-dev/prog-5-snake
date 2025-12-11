package org.snake;

import java.io.IOException;

public class Game {
    private static final int TICK_DELAY_MS = 500;

    private final Screen screen;
    private final Snake snake;
    private final Food food;
    private final Score score;
    private boolean isRunning;

    public Game(int screenHeight, int screenWidth) {
        this.screen = new Screen(screenHeight, screenWidth);
        this.snake = new Snake(new Position(10, 10), Direction.RIGHT);
        this.food = new Food(screenHeight, screenWidth);
        this.score = new Score();
        this.isRunning = true;
    }

    public void start() throws InterruptedException, IOException {
        while (isRunning) {
            handleInput();
            update();
            render();
            Thread.sleep(TICK_DELAY_MS);
        }
    }

    private void handleInput() throws IOException {
        if (System.in.available() > 0) {
            char input = (char) System.in.read();
            Direction newDirection = mapInputToDirection(input);

            if (newDirection != null) {
                snake.changeDirection(newDirection);
            }
        }
    }

    private Direction mapInputToDirection(char input) {
        switch (input) {
            case 'a':
                return Direction.LEFT;
            case 'd':
                return Direction.RIGHT;
            case 'w':
                return Direction.UP;
            case 's':
                return Direction.DOWN;
            default:
                return null;
        }
    }

    private void update() {
        Position newHeadPosition = snake.getHead().move(snake.getCurrentDirection());

        if (screen.isOutOfBounds(newHeadPosition)) {
            gameOver();
            return;
        }

        if (snake.occupies(newHeadPosition)) {
            gameOver();
            return;
        }

        boolean foodEaten = food.isAt(newHeadPosition);

        if (foodEaten) {
            score.increment();
            food.respawn();
            snake.move(true);
        } else {
            snake.move(false);
        }
    }

    private void render() {
        screen.render(snake, food, score);
    }

    private void gameOver() {
        isRunning = false;
        System.out.println("\n╔════════════════════════════╗");
        System.out.println("║       GAME OVER!           ║");
        System.out.println("║  Score final: " + String.format("%-12d", score.getPoints()) + "║");
        System.out.println("╔════════════════════════════╗");
    }
}