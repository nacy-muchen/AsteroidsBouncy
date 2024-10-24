package si.model;

import java.awt.*;

public class Player implements Hittable {
    private static final int MAX_SPEED = 25;  // 最大速度
    private double speedFactor = 0.5; // 当前速度
    private static final double ACCELERATION = 0.05;
    private int x;
    private int y;
    private double XSpeed = 0;
    private double YSpeed = 0;
    private Rectangle hitBox;
    private int weaponCountdown;
    private boolean alive = true;
    private double rotationAngle;

    public static final int SHIP_SCALE = 4;
    private static final int WIDTH = SHIP_SCALE * 8;
    private boolean invincible;
    private long invincibleTimeLeft;

    public Player() {
        x = BouncyAsteroidsGame.SCREEN_WIDTH / 2;
        y = 450;
        rotationAngle = 0.0d;
        hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
    }
    public double getRotationAngle(){
        return rotationAngle;
    }

    public boolean isHit(Bullet b) {
        if (!invincible) {
            Rectangle s = b.getHitBox();
            if (s.intersects(hitBox.getBounds())) {
                alive = false;
            }
            return s.intersects(hitBox.getBounds());
        }
        return false;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void tick() {
        if (invincible && invincibleTimeLeft > 0) {
            invincibleTimeLeft -=100 ;
            if (invincibleTimeLeft <= 0) {
                invincible = false;
            }
        }
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 10;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        alive = true;
        invincible = true;
        invincibleTimeLeft = 5000;
        x = BouncyAsteroidsGame.SCREEN_WIDTH / 2;
        y = 450;
        XSpeed=0;
        YSpeed=0;
        rotationAngle=0;
        hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);

    }


    public int getPoints() {
        return -100;
    }

    public boolean isPlayer() {
        return true;
    }

    public void rotateClockwise(double rotate) {
        rotationAngle += Math.toRadians(rotate);
    }

    public void rotateAntiClockwise(double rotate) {
        rotationAngle -= Math.toRadians(rotate);
    }

    public Bullet fire(Player p) {
        if (weaponCountdown == 0) {
            int shipSize = Player.SHIP_SCALE * 8;
            int shipHeight=Player.SHIP_SCALE*5;
            //int shipHalfWidth = shipSize / 2;
            int[] x_coords = {0,shipSize / 2, shipSize, 24,shipSize / 2,8};
            int[] y_coords = {shipSize / 2, -shipSize / 2,shipSize / 2, 8,shipSize / 2,8};
            int triangleCenterX = (x_coords[0] + x_coords[1] + x_coords[2] + x_coords[4]) / 4;
            int triangleCenterY = (y_coords[0] + y_coords[1] + y_coords[2] + y_coords[4]) / 4;

            int shipFrontX = shipSize/2-triangleCenterX;
            int shipFrontY = -shipSize/2-triangleCenterY;


            int rotatedShipFrontX =(int) (shipFrontX * Math.cos(p.getRotationAngle())-shipFrontY * Math.sin(p.getRotationAngle()));
            int rotatedShipFrontY =(int) (shipFrontX * Math.sin(p.getRotationAngle())+shipFrontY * Math.cos(p.getRotationAngle()));
            int screenX=p.getX()+triangleCenterX+rotatedShipFrontX;
            int screenY=p.getY()+triangleCenterY+rotatedShipFrontY;
            Bullet b = new Bullet(screenX, screenY, "Player", p.getRotationAngle());
            return b;

        }
        return null;

    }
    private boolean isAccelerating = false;

    public void setIsAccelerating(boolean isAccelerating) {
        this.isAccelerating = isAccelerating;
    }
    public void move(int x1, int y1) {
        double cos = Math.cos(rotationAngle);
        double sin = Math.sin(rotationAngle);
        if (isAccelerating){
            XSpeed += (x1 * cos - y1 * sin) * speedFactor;
            YSpeed += (x1 * sin + y1 * cos) * speedFactor;
            XSpeed = Math.min(XSpeed, MAX_SPEED);
            YSpeed = Math.min(YSpeed, MAX_SPEED);
        }
        keepMove();


    }
    public void keepMove(){
        int newX = x + (int) XSpeed/10;
        int newY = y + (int) YSpeed/10;

        x = newX;
        y = newY;

        wrapAroundScreen();


        updateHitBox();

    }
    private void updateHitBox() {
        hitBox.setLocation(x, y);

    }
    private void wrapAroundScreen() {
        if (x > BouncyAsteroidsGame.SCREEN_WIDTH) {
            x = 0;
        } else if (x < 0) {
            x = BouncyAsteroidsGame.SCREEN_WIDTH;
        }

        if (y > BouncyAsteroidsGame.SCREEN_HEIGHT) {
            y = 0;
        } else if (y < 0) {
            y = BouncyAsteroidsGame.SCREEN_HEIGHT;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
