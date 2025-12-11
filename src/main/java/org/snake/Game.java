package org.snake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale qui orchestre le jeu Snake
 * Utilise le State Pattern (enum) et Observer Pattern (liste d'observers)
 */
public class Game {
    private static final int TICK_DELAY_MS = 120;

    // STATE PATTERN : États du jeu
    public enum State {
        RUNNING, PAUSED, GAME_OVER
    }

    private final Screen screen;
    private final Snake snake;
    private final Food food;
    private final Score score;

    // État actuel du jeu
    private State currentState;

    // OBSERVER PATTERN : Liste des observers
    private final List<GameObserver> observers;

    public Game(int screenHeight, int screenWidth) {
        this.screen = new Screen(screenHeight, screenWidth);
        this.snake = new Snake(new Position(10, 10), Direction.RIGHT);
        this.food = new Food(screenHeight, screenWidth);
        this.score = new Score();
        this.currentState = State.RUNNING;
        this.observers = new ArrayList<>();
    }

    /**
     * OBSERVER PATTERN : Ajoute un observer
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * OBSERVER PATTERN : Notifie tous les observers
     */
    private void notifyObservers(String event, Object data) {
        for (GameObserver observer : observers) {
            observer.onGameEvent(event, data);
        }
    }

    /**
     * Lance la boucle principale du jeu
     */
    public void start() throws InterruptedException, IOException {
        while (currentState != State.GAME_OVER) {
            handleInput();

            // STATE PATTERN : Ne met à jour que si on n'est pas en pause
            if (currentState == State.RUNNING) {
                update();
            }

            render();
            Thread.sleep(TICK_DELAY_MS);
        }

        System.out.println("Merci d'avoir joué !");
    }

    /**
     * Gère les entrées utilisateur
     */
    private void handleInput() throws IOException {
        if (System.in.available() > 0) {
            char input = (char) System.in.read();

            // Touche 'p' pour pause/reprendre
            if (input == 'p') {
                if (currentState == State.RUNNING) {
                    currentState = State.PAUSED;
                    notifyObservers("STATE_CHANGED", "PAUSED");
                } else if (currentState == State.PAUSED) {
                    currentState = State.RUNNING;
                    notifyObservers("STATE_CHANGED", "RUNNING");
                }
                return;
            }

            // Ne change la direction que si le jeu est en cours
            if (currentState == State.RUNNING) {
                Direction newDirection = mapInputToDirection(input);
                if (newDirection != null) {
                    Direction oldDirection = snake.getCurrentDirection();
                    boolean changed = snake.changeDirection(newDirection);

                    if (changed) {
                        notifyObservers("DIRECTION_CHANGED", newDirection);
                    }
                }
            }
        }
    }

    /**
     * Convertit une touche en direction
     */
    private Direction mapInputToDirection(char input) {
        switch (input) {
            case 'a': return Direction.LEFT;
            case 'd': return Direction.RIGHT;
            case 'w': return Direction.UP;
            case 's': return Direction.DOWN;
            default: return null;
        }
    }

    /**
     * Met à jour l'état du jeu
     */
    private void update() {
        Position newHeadPosition = snake.getHead().move(snake.getCurrentDirection());

        // Vérifie les collisions avec les murs
        if (screen.isOutOfBounds(newHeadPosition)) {
            notifyObservers("COLLISION", "Mur");
            gameOver();
            return;
        }

        // Vérifie si le serpent se mord lui-même
        if (snake.occupies(newHeadPosition)) {
            notifyObservers("COLLISION", "Auto-collision");
            gameOver();
            return;
        }

        // Vérifie si le serpent mange la nourriture
        boolean foodEaten = food.isAt(newHeadPosition);

        if (foodEaten) {
            score.increment();
            food.respawn();
            snake.move(true);
            notifyObservers("FOOD_EATEN", score.getPoints());
        } else {
            snake.move(false);
        }
    }

    /**
     * Affiche le jeu à l'écran
     */
    private void render() {
        screen.render(snake, food, score);

        // STATE PATTERN : Affichage selon l'état
        if (currentState == State.PAUSED) {
            System.out.println("⏸️  JEU EN PAUSE - Appuyez sur 'p' pour reprendre");
        } else if (currentState == State.RUNNING) {
            System.out.println("Appuyez sur 'p' pour pause");
        }
    }

    /**
     * Termine le jeu
     */
    private void gameOver() {
        currentState = State.GAME_OVER;
        notifyObservers("STATE_CHANGED", "GAME_OVER");
        System.out.println("\n╔════════════════════════════╗");
        System.out.println("║       GAME OVER!           ║");
        System.out.println("║  Score final: " + String.format("%-12d", score.getPoints()) + "║");
        System.out.println("╚════════════════════════════╝");
    }
}