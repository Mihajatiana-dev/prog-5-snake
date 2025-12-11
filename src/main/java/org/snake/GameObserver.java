package org.snake;

/**
 * OBSERVER PATTERN : Interface simple pour observer les événements du jeu
 */
public interface GameObserver {
    /**
     * Appelé quand un événement se produit dans le jeu
     * @param event Type d'événement : "FOOD_EATEN", "COLLISION", "DIRECTION_CHANGED", "STATE_CHANGED"
     * @param data Données associées à l'événement
     */
    void onGameEvent(String event, Object data);
}