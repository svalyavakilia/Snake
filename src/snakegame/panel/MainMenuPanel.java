package snakegame.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;
import static snakegame.panel.SnakeGamePanel.HEIGHT_OF_THE_PANEL;
import static snakegame.panel.SnakeGamePanel.WIDTH_OF_THE_PANEL;

public class MainMenuPanel extends JPanel {
    private final JButton play;

    {
        play = new JButton();
    }
    
    public MainMenuPanel() {
        setPreferredSize(new Dimension(WIDTH_OF_THE_PANEL,
                                       HEIGHT_OF_THE_PANEL));
        setLayout(null);
        setBackground(WHITE);

        play.setLocation(100, 150);
        play.setSize(WIDTH_OF_THE_PANEL / 2,
                     HEIGHT_OF_THE_PANEL / 4);
        play.setText("Play!");
        play.setFont(new Font(MONOSPACED, BOLD, 35));
        play.setForeground(WHITE);
        play.setBackground(new Color(83, 33, 83));
        play.setToolTipText("Press so start playing!");
        play.addActionListener(listener -> switchToGamePanel());
        play.setFocusable(false);

        add(play);
    }

    private void switchToGamePanel() {
        final JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.setContentPane(new SnakeGamePanel());
        window.getContentPane().requestFocus();
        window.pack();
    }
}