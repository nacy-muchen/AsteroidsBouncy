package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Swarm implements Movable {
    private List<Asteroids> ships;
    private List<Asteroids> enemyShips;
    private double x =1 , y =1 ;
    private int space = 30;
    private Asteroids[][] shipGrid;
    private int rows, cols;
    private int count = 0;
    private BouncyAsteroidsGame game;

    public Swarm(BouncyAsteroidsGame g, int currentLevel) {
        game = g;
        ships = new ArrayList<Asteroids>();
        enemyShips = new ArrayList<Asteroids>();
        Random rand = new Random();
        int asteroidDiameter = 80;
        int totalAsteroids =currentLevel+1;
        for (int i = 0; i < totalAsteroids; i++) {
            int x = 50+rand.nextInt(game.getScreenWidth()-asteroidDiameter-50);
            int y = 50+rand.nextInt(game.getScreenHeight()-asteroidDiameter-60);
            double angle=rand.nextDouble()*2*Math.PI;
            Asteroids asteroid = new Asteroids(x, y, AlienType.A, angle,game);
            asteroid.setSpeed(1.0);
            ships.add(asteroid);
        }
    }

    public void move() {
        List<Asteroids> add = new ArrayList<>();
        List<Asteroids> remove = new ArrayList<Asteroids>();

        for (Asteroids s : ships) {
            if (!s.isAlive()) {
                remove.add(s);
                if (s.getType() == AlienType.A || s.getType() == AlienType.B) {
                    AlienType newType = s.getType() == AlienType.A ? AlienType.B : AlienType.C;
                    double newSpeed = s.getType() == AlienType.A ? 2.0 : 2.5;

                    // 创建两个新的行星，方向有随机偏差
                    add.add(createNewAsteroid(s, newType, newSpeed));
                    add.add(createNewAsteroid(s, newType, newSpeed));
                }
            } else{
                s.move(this);
            }
        }
        ships.removeAll(remove);
        ships.addAll(add);
    }
    private Asteroids createNewAsteroid(Asteroids original, AlienType type, double speed) {
        double newAngle = calculateNewAngle(original.getAngle());
        Asteroids newAsteroid = new Asteroids((int) original.getX(), (int) original.getY(), type, newAngle,game);
        newAsteroid.setSpeed(speed);

        return newAsteroid;
    }
    private double calculateNewAngle(double originalAngle) {
        Random rand = new Random();
        double deviation = Math.toRadians(rand.nextInt(40) - 20);
        return originalAngle + deviation;
    }

    public void addEnemyShip(Asteroids enemyShip) {
        enemyShips.add(enemyShip);
    }

    public List<Asteroids> getEnemyShip() {
        return enemyShips;
    }


    public int getBottomY() {
        int b = 0;
        for (Asteroids e : getBottom()) {
            Shape hb = e.getShape();
            Rectangle bb = hb.getBounds();
            if (e.getY() + bb.height > b) {
                b = e.getY() + bb.height;
            }
        }
        return b;
    }


    public List<Hittable> getHittable() {
        return new ArrayList<Hittable>(ships);
    }

    public List<Asteroids> getBottom() {
        List<Asteroids> bottomShips = new ArrayList<Asteroids>();

        for (int i = 0; i < cols; i++) {
            boolean found = false;
            for (int j = rows - 1; j >= 0 && !found; j--) {
                if (shipGrid[j][i].isAlive()) {
                    found = true;
                    bottomShips.add(shipGrid[j][i]);
                }
            }
        }
        return bottomShips;
    }
    public void add(Asteroids asteroids) {
        ships.add(asteroids);
    }
    public List<Asteroids> getEnemyShips() {
        return new ArrayList<Asteroids>(ships);
    }

    public int getShipsRemaining() {
        return ships.size();
    }

}
