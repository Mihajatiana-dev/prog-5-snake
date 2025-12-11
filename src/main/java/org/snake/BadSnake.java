package org.snake;

public class BadSnake {
    private static final int SCREEN_HEIGHT = 20;
    private static final int SCREEN_WIDTH = 40;

    public static void main(String[] args) {
        try {
            Game game = new Game(SCREEN_HEIGHT, SCREEN_WIDTH);

            game.addObserver(new ScoreObserver());

            game.start();

        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution du jeu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}