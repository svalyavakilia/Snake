package snakegame.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import static java.awt.Color.WHITE;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;

class SnakeGamePanel extends JPanel {
            static final int HEIGHT_OF_THE_PANEL;
            static final int WIDTH_OF_THE_PANEL;
    private static final int MAX_LENGTH_OF_THE_SNAKE;
    private static final Image LINK;
    private static final Image APPLE;

    static {
        HEIGHT_OF_THE_PANEL = 400;
        WIDTH_OF_THE_PANEL = 400;
        MAX_LENGTH_OF_THE_SNAKE = 400;
        LINK = new ImageIcon("resources\\link.png").getImage();
        APPLE = new ImageIcon("resources\\apple.png").getImage();
    }

    private final int[] xCoordinates;
    private final int[] yCoordinates;

    private final Timer refresher;

    private int size;

    private boolean snakeIsMovingToTheRight;
    private boolean snakeIsMovingDown;
    private boolean snakeIsMovingToTheLeft;
    private boolean snakeIsMovingUp;

    private int appleXCoordinate;
    private int appleYCoordinate;

    private int score;

    {
        xCoordinates = new int[MAX_LENGTH_OF_THE_SNAKE];
        yCoordinates = new int[MAX_LENGTH_OF_THE_SNAKE];

        xCoordinates[0] = 40;
        xCoordinates[1] = 60;
        xCoordinates[2] = 80;

        yCoordinates[0] = 100;
        yCoordinates[1] = 100;
        yCoordinates[2] = 100;

        refresher = new Timer(1000, event -> {
            moveSnake();
            checkForApple();
            checkForCollisionWithTheBorders();
            checkForCollisionWithTheTail();
            repaint();
        });

        size = 3;

        snakeIsMovingToTheRight = true;

        score = 0;
    }

    SnakeGamePanel() {
        setPreferredSize(new Dimension(WIDTH_OF_THE_PANEL,
                                       HEIGHT_OF_THE_PANEL));
        setLayout(null);
        setBackground(WHITE);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent keyEvent) {
                super.keyPressed(keyEvent);

                final int keyCode = keyEvent.getKeyCode();

                if (keyCode == VK_RIGHT || keyCode == VK_D
                    &&
                    !snakeIsMovingToTheLeft) {

                    snakeIsMovingDown = false;
                    snakeIsMovingUp = false;
                    snakeIsMovingToTheRight = true;
                } else if (keyCode == VK_DOWN || keyCode == VK_S
                           &&
                           !snakeIsMovingUp) {

                    snakeIsMovingToTheLeft = false;
                    snakeIsMovingToTheRight = false;
                    snakeIsMovingDown = true;
                } else if (keyCode == VK_LEFT || keyCode == VK_A
                           &&
                           !snakeIsMovingToTheRight) {

                    snakeIsMovingUp = false;
                    snakeIsMovingDown = false;
                    snakeIsMovingToTheLeft = true;
                } else if (keyCode == VK_UP || keyCode == VK_W
                           &&
                           !snakeIsMovingDown) {

                    snakeIsMovingToTheRight = false;
                    snakeIsMovingToTheLeft = false;
                    snakeIsMovingUp = true;
                }
            }
        });
        setFocusable(true);

        createAppleCoordinates();

        refresher.start();
    }

    private void moveSnake() {
        int headXCoordinate = xCoordinates[size - 1];
        int headYCoordinate = yCoordinates[size - 1];

        for (int i = 0; i < size - 1; i = i + 1) {
            xCoordinates[i] = xCoordinates[i + 1];
            yCoordinates[i] = yCoordinates[i + 1];
        }

             if (snakeIsMovingToTheRight) headXCoordinate += 20;
        else if (snakeIsMovingDown)       headYCoordinate += 20;
        else if (snakeIsMovingToTheLeft)  headXCoordinate -= 20;
        else if (snakeIsMovingUp)         headYCoordinate -= 20;

        xCoordinates[size - 1] = headXCoordinate;
        yCoordinates[size - 1] = headYCoordinate;
    }

    private void checkForCollisionWithTheBorders() {
        final boolean xCollision = (xCoordinates[size - 1] == -20)
                                   ||
                                   (xCoordinates[size - 1] == 400);

        final boolean yCollision = (yCoordinates[size - 1] == -20)
                                   ||
                                   (yCoordinates[size - 1] == 400);

        if (xCollision || yCollision) {
            refresher.stop();

            switchToGameOverPanel();
        }
    }

    private void checkForCollisionWithTheTail() {
        for (int i = size - 2; i >= 0; i = i - 1) {
            if (xCoordinates[size - 1] == xCoordinates[i]) {
                if (yCoordinates[size - 1] == yCoordinates[i]) {
                    refresher.stop();

                    switchToGameOverPanel();
                }
            }
        }
    }

    private void checkForApple() {
        if (xCoordinates[size - 1] == appleXCoordinate
            &&
            yCoordinates[size - 1] == appleYCoordinate) {

            ++size;

            ++score;

            moveLinks();

            if (xCoordinates[2] > xCoordinates[1]) {
                xCoordinates[0] = xCoordinates[1] - 20;
                yCoordinates[0] = yCoordinates[1];
            } else if (xCoordinates[1] > xCoordinates[2]) {
                xCoordinates[0] = xCoordinates[1] + 20;
                yCoordinates[0] = yCoordinates[1];
            }

            if (yCoordinates[2] > yCoordinates[1]) {
                yCoordinates[0] = yCoordinates[1] - 20;
                xCoordinates[0] = xCoordinates[1];
            } else if (yCoordinates[1] > yCoordinates[2]) {
                yCoordinates[0] = yCoordinates[1] + 20;
                xCoordinates[0] = xCoordinates[1];
            }

            createAppleCoordinates();
        }
    }

    private void moveLinks() {
        for (int i = size - 1; i >= 0; i = i - 1) {
            xCoordinates[i + 1] = xCoordinates[i];
            yCoordinates[i + 1] = yCoordinates[i];
        }
    }

    private void createAppleCoordinates() {
        appleXCoordinate = ((int) (Math.random() * 20)) * 20;
        appleYCoordinate = ((int) (Math.random() * 20)) * 20;

        for (int i = 0; i < size; i += 1) {
            if (appleXCoordinate == xCoordinates[i]) {
                if (appleYCoordinate == yCoordinates[i])
                    createAppleCoordinates();
            }
        }
    }

    private void switchToGameOverPanel() {
        final JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.setContentPane(new GameOverPanel(score));
        window.pack();
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(APPLE, appleXCoordinate, appleYCoordinate, this);

        for (int i = size - 1; i >= 0; i = i - 1) {
            graphics.drawImage(LINK, xCoordinates[i], yCoordinates[i], this);
        }
    }
}