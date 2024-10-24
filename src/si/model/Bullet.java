package si.model;

import java.awt.*;

public class Bullet implements Movable, Hittable {
    private int x, y;
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 4;
    public static final int BULLET_WIDTH = 4;
    private static final int BULLET_SPEED = 25;
    private double direction;

    public Bullet(int x, int y,String name,double direction) {
        this.x = x;
        this.y = y;
        this.name = name + " " + bulletCounter++;
        this.direction=direction;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    }


    public void move() {
        int dx = (int) (BULLET_SPEED * Math.cos(direction));
        int dy = (int) (BULLET_SPEED * Math.sin(direction));
        x += dx;
        y += dy;
        hitBox.setLocation(x, y);
    }
    public void playerBulletsMove() {
        int dx = (int) (BULLET_SPEED * Math.cos(direction-Math.PI/2));
        int dy = (int) (BULLET_SPEED * Math.sin(direction-Math.PI/2));
        x += dx;
        y += dy;
        hitBox.setLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }

    public void destroy() {
        alive = false;
    }

}
