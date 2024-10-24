package si.display;

import si.model.BouncyAsteroidsGame;

import javax.swing.*;
import java.awt.*;

public class AboutScreen extends JPanel {
    private static final long serialVersionUID = -1264717778772722118L;
    private boolean menu = false;
    private PlayerListener listener;

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, BouncyAsteroidsGame.SCREEN_WIDTH, BouncyAsteroidsGame.SCREEN_HEIGHT);
        g.setColor(Color.BLACK);
        drawString(g, "Space Invader Controls", new Rectangle(0, 0, BouncyAsteroidsGame.SCREEN_WIDTH, 64), 36);
        g.drawString("Turn Left", 1 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 0 * 32);
        g.drawString("left arrow", 4 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 0 * 32);
        g.drawString("Turn Right", 1 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 1 * 32);
        g.drawString("right arrow", 4 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 1 * 32);
        g.drawString("Accelerate", 1 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 4 * 32);
        g.drawString("up", 4 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 4 * 32);
        g.drawString("Fire", 1 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 2 * 32);
        g.drawString("space bar", 4 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 2 * 32);
        g.drawString("Play/Pause", 1 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 3 * 32);
        g.drawString("p", 4 * BouncyAsteroidsGame.SCREEN_WIDTH / 6, 96 + 3 * 32);
        drawString(g, "Press 'M' to return to the Main Menu", new Rectangle(0, 416, BouncyAsteroidsGame.SCREEN_WIDTH, 96), 24);
    }

    private void drawString(Graphics g, String text, Rectangle rect, int size) {
        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Arial", Font.BOLD, size);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x, y);
    }
}
