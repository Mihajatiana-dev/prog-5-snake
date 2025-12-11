package org.snake;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le serpent dans le jeu
 */
public class Snake {
    private static final char SNAKE_SYMBOL = '#';
    private final List<Position> body;
    private Direction currentDirection;

    /**
     * Crée un serpent avec une position initiale et une direction
     */
    public Snake(Position initialHead, Direction initialDirection) {
        this.body = new ArrayList<>();
        this.currentDirection = initialDirection;

        // Initialise le serpent avec 3 segments
        body.add(initialHead);
        body.add(new Position(initialHead.getRow(), initialHead.getColumn() - 1));
        body.add(new Position(initialHead.getRow(), initialHead.getColumn() - 2));
    }

    /**
     * Déplace le serpent dans la direction actuelle
     * @param grow true si le serpent doit grandir (nourriture mangée)
     */
    public void move(boolean grow) {
        Position newHead = getHead().move(currentDirection);
        body.add(0, newHead);

        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    /**
     * Change la direction du serpent (avec validation pour éviter les demi-tours)
     * @return true si la direction a été changée, false sinon
     */
    public boolean changeDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            this.currentDirection = newDirection;
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la nouvelle direction est opposée à la direction actuelle
     */
    private boolean isOppositeDirection(Direction newDirection) {
        return (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT) ||
                (currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP);
    }

    /**
     * Retourne la position de la tête du serpent
     */
    public Position getHead() {
        return body.get(0);
    }

    /**
     * Vérifie si le serpent se mord lui-même
     */
    public boolean checkSelfCollision() {
        Position head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si une position donnée fait partie du corps du serpent
     */
    public boolean occupies(Position position) {
        return body.contains(position);
    }

    public List<Position> getBody() {
        return new ArrayList<>(body);
    }

    public char getSymbol() {
        return SNAKE_SYMBOL;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}