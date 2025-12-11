package org.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final char SNAKE_SYMBOL = '#';
    private final List<Position> body;
    private Direction currentDirection;

    public Snake(Position initialHead, Direction initialDirection) {
        this.body = new ArrayList<>();
        this.currentDirection = initialDirection;

        body.add(initialHead);
        body.add(new Position(initialHead.getRow(), initialHead.getColumn() - 1));
        body.add(new Position(initialHead.getRow(), initialHead.getColumn() - 2));
    }

    public void move(boolean grow) {
        Position newHead = getHead().move(currentDirection);
        body.add(0, newHead);

        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    public void changeDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            this.currentDirection = newDirection;
        }
    }

    private boolean isOppositeDirection(Direction newDirection) {
        return (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT) ||
                (currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP);
    }

    public Position getHead() {
        return body.get(0);
    }


    public boolean checkSelfCollision() {
        Position head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

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