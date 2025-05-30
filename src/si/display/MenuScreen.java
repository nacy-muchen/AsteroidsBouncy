package si.display;

import si.model.BouncyAsteroidsGame;

import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel {
    private static final long serialVersionUID = 1616386874546775416L;

    private void drawString(Graphics g, String text, Rectangle rect, int size) {
        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Arial", Font.BOLD, size);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, BouncyAsteroidsGame.SCREEN_WIDTH, BouncyAsteroidsGame.SCREEN_HEIGHT);
        drawString(g, "Welcome to Space Invaders!!!!", new Rectangle(BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 32,
                BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 3), 36);
        drawString(g, "To play a game press N", new Rectangle(BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 6,
                BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 3), 18);
        drawString(g, "To see the controls press A", new Rectangle(BouncyAsteroidsGame.SCREEN_WIDTH / 3, 2 * BouncyAsteroidsGame.SCREEN_HEIGHT / 6,
                BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 3), 18);
        drawString(g, "To see the High scores press H", new Rectangle(BouncyAsteroidsGame.SCREEN_WIDTH / 3, 3 * BouncyAsteroidsGame.SCREEN_HEIGHT / 6,
                BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 3), 18);
        drawString(g, "To exit press X", new Rectangle(BouncyAsteroidsGame.SCREEN_WIDTH / 3, 4 * BouncyAsteroidsGame.SCREEN_HEIGHT / 6,
                BouncyAsteroidsGame.SCREEN_WIDTH / 3, BouncyAsteroidsGame.SCREEN_HEIGHT / 3), 18);

    }
}
