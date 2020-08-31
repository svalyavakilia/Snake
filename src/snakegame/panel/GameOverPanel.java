package snakegame.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;
import static javax.swing.SwingConstants.CENTER;
import static snakegame.panel.SnakeGamePanel.HEIGHT_OF_THE_PANEL;
import static snakegame.panel.SnakeGamePanel.WIDTH_OF_THE_PANEL;

class GameOverPanel extends javax.swing.JPanel {
    private final JLabel score;
    private final JButton replay;
    private final JButton exit;

    {
        score = new JLabel();
        replay = new JButton();
        exit = new JButton();
    }

    GameOverPanel(final int score) {
        setPreferredSize(new Dimension(WIDTH_OF_THE_PANEL,
                                       HEIGHT_OF_THE_PANEL));
        setLayout(null);
        setBackground(WHITE);

        final Color color = new Color(83, 33, 83);
        final Font font = new Font(MONOSPACED, BOLD, 18);

        this.score.setLocation(100, 0);
        this.score.setSize(WIDTH_OF_THE_PANEL / 2,
                           HEIGHT_OF_THE_PANEL / 4);
        this.score.setText("Your score is: " + score);
        this.score.setFont(font);
        this.score.setForeground(BLACK);
        this.score.setHorizontalAlignment(CENTER);
        this.score.setToolTipText("This is your previous game's score!");

        replay.setLocation(100, 130);
        replay.setSize(WIDTH_OF_THE_PANEL / 2,
                       HEIGHT_OF_THE_PANEL / 4);
        replay.setText("Replay!");
        replay.setFont(font);
        replay.setForeground(WHITE);
        replay.setBackground(color);
        replay.setToolTipText("Press me to replay!");
        replay.addActionListener(listener -> startPlaying());
        replay.setFocusable(false);

        exit.setLocation(100, 260);
        exit.setSize(WIDTH_OF_THE_PANEL / 2,
                     HEIGHT_OF_THE_PANEL / 4);
        exit.setText("Exit!");
        exit.setFont(font);
        exit.setForeground(WHITE);
        exit.setBackground(color);
        exit.setToolTipText("Press me to exit!");
        exit.addActionListener(listener -> System.exit(0));
        exit.setFocusable(false);

        add(this.score);
        add(exit);
        add(replay);
    }

    private void startPlaying() {
        final JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.setContentPane(new SnakeGamePanel());
        window.getContentPane().requestFocus();
        window.pack();
    }
}