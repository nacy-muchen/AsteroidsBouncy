package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private BouncyAsteroidsGame game;
    AlienType type;
    public GameScreen(BouncyAsteroidsGame game){
        this.game = game;
    }

    private void drawPlayerShip(Graphics2D gc, Player p) {
        int x = p.getX();
        int y = p.getY();
        int shipSize=Player.SHIP_SCALE*8;
        int[] x_coords = {0,shipSize / 2, shipSize, 24,shipSize / 2,8};
        int[] y_coords = {shipSize / 2, -shipSize / 2,shipSize / 2, 8,shipSize / 2,8};
        int triangleCenterX = (x_coords[0] + x_coords[1] + x_coords[2] + x_coords[4]) / 4;
        int triangleCenterY = (y_coords[0] + y_coords[1] + y_coords[2] + y_coords[4]) / 4;

        double cos=Math.cos(p.getRotationAngle());
        double sin=Math.sin(p.getRotationAngle());
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + (int) ((x_coords[i] - triangleCenterX) * cos - (y_coords[i] - triangleCenterY) * sin) + triangleCenterX;
            y_adjusted[i] = y + (int) ((x_coords[i] - triangleCenterX) * sin + (y_coords[i] - triangleCenterY) * cos) + triangleCenterY;
        }
        Polygon pg = new Polygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.setColor(Color.BLACK);
        gc.fillPolygon(pg);



    }

    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.BLACK);
        gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }


    private void drawAsteroidA(Graphics2D gc, Asteroids es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0  , 6 , 10,   13,20, 15,20, 10, 0, -10, -20, -20, -10};
        int[] y_coords = new int[]{-20, -21,-10, -12, 0, 5,10, 20, 20, 10, 0, -10, -20};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Asteroids.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Asteroids.SHIP_SCALE;
        }
        gc.setColor(Color.BLACK);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);


    }

    private void drawAsteroidB(Graphics2D gc, Asteroids es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0  , 3 , 5,   7,10, 7,10, 5, 0, -5, -10, -10, -5};
        int[] y_coords = new int[]{-10, -11,-5, -6, 0, 3,5, 10, 10, 5, 0, -5, -10};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Asteroids.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Asteroids.SHIP_SCALE;
        }
        gc.setColor(Color.BLACK);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);

    }

    private void drawAsteroidC(Graphics2D gc, Asteroids es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0  , 2 , 3,   4,5, 4,5, 3, 0, -3, -5, -5, -3};
        int[] y_coords = new int[]{-5, -6,-3, -3, 0, 2,3, 5, 5, 3, 0, -3, -5};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Asteroids.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Asteroids.SHIP_SCALE;
        }
        gc.setColor(Color.BLACK);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);

    }
    private void drawBigEnemyShip(Graphics2D gc, Asteroids es){
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{10,-10, -15, -20,-10, 10,  20, 15,  10};
        int[] y_coords = new int[]{-10, -10,   0,   0, 10, 10 , 0 ,  0  , -10};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Asteroids.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Asteroids.SHIP_SCALE;
        }
        gc.setColor(Color.BLACK);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
    }
    private void drawSmallEnemyShip(Graphics2D gc, Asteroids es){
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{5,-5, -10, -7,-5, 5,  7, 10,  5};
        int[] y_coords = new int[]{5, 5,   0,   0, -5, -5 , 0 ,  0  , 5};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Asteroids.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Asteroids.SHIP_SCALE;
        }
        gc.setColor(Color.BLACK);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);

    }

    protected void paintComponent(Graphics g) {
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, BouncyAsteroidsGame.SCREEN_WIDTH, BouncyAsteroidsGame.SCREEN_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), BouncyAsteroidsGame.SCREEN_WIDTH / 2, 20);

            drawPlayerShip(g2,game.getShip() );
            if(!game.isPaused()) {
                for (Bullet bullet : game.getBullets()) {
                    drawBullet(g2, bullet);
                }
            }
            for (Asteroids enemy : game.getEnemyShips()) {
                if (enemy.getType() == AlienType.A) {
                    drawAsteroidA(g2, enemy);
                }else if(enemy.getType()==AlienType.B){
                    drawAsteroidB(g2,enemy);
                }else if(enemy.getType()==AlienType.C){
                    drawAsteroidC(g2,enemy);
                }else if(enemy.getType()==AlienType.D) {
                    drawBigEnemyShip(g2, enemy);
                }else if(enemy.getType()==AlienType.E) {
                    drawSmallEnemyShip(g2, enemy);
                }
            }
            g2.setColor(Color.RED);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            if (game.isPaused() && !game.isGameOver()) {
                g.setColor(Color.BLACK);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.BLACK);
                g.drawString("Game over ", 480, 256);
            }
        }
    }


}
