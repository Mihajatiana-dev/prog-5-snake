package org.snake;

public class ScoreObserver implements GameObserver {

    @Override
    public void onGameEvent(String event, Object data) {
        switch (event) {
            case "FOOD_EATEN":
                int score = (int) data;
                System.out.println("🎉 Nourriture mangée ! Score : " + score);
                if (score % 5 == 0) {
                    System.out.println("⭐ Jalon atteint : " + score + " points !");
                }
                break;

            case "COLLISION":
                System.out.println("💥 Collision avec : " + data);
                break;

            case "DIRECTION_CHANGED":
                System.out.println("↔️  Direction changée vers : " + data);
                break;

            case "STATE_CHANGED":
                System.out.println("🎮 État du jeu : " + data);
                break;
        }
    }
}