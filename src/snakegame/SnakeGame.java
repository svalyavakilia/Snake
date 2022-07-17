package snakegame;

import javax.swing.SwingUtilities;
import snakegame.frame.SnakeGameFrame;

public class SnakeGame {
    /**
     * This method starts a new game.
     */
    public static void start() {
        SwingUtilities.invokeLater(() -> new SnakeGameFrame());
    }
}