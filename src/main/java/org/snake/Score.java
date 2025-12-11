package org.snake;

public class Score {
    private int points;

    public Score() {
        this.points = 0;
    }

    public void increment() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Score: " + points;
    }
}