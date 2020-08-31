package snakegame.frame;

import javax.swing.JFrame;
import snakegame.panel.MainMenuPanel;

public class SnakeGameFrame extends JFrame {
    public SnakeGameFrame() {
        setContentPane(new MainMenuPanel());
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Snake");
        setVisible(true);
    }
}