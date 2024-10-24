package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private Swarm swarm; // Manages a collection of all Asteroids and enemy spaceships
    private BouncyAsteroidsGame game;
    private int currentLevel;
    // Constructor to initialize the level
    public Level(BouncyAsteroidsGame g, int currentLevel) {
        game = g;
        this.currentLevel = currentLevel;
        swarm = new Swarm(g, this.currentLevel);
        reset();
    }

    // Returns the remaining number of ships in the level
    public int getShipsRemaining() {
        return swarm.getShipsRemaining();
    }
    // Gets the lowest Y-coordinate of the ships in the current Swarm
    public int getBottomY() {
        return swarm.getBottomY();
    }
    // Gets all hittable objects, including asteroids and enemy ships
    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittable());
        targets.addAll(swarm.getEnemyShips());
        return targets;
    }

    // Moves all objects in the Swarm and returns all enemy ships
    public List<Asteroids> move() {
        swarm.move();
        return swarm.getEnemyShips();
    }

    // Updates positions of enemy ships and handles their firing logic
    public List<Bullet> updateEnemyShipsAndFireBullets(Player player) {
        swarm.move(); // Move enemy ships
        List<Asteroids> enemyShips = swarm.getEnemyShip();
        List<Bullet> eBullets = new ArrayList<Bullet>();
        Random random = new Random();
        for (Asteroids s : enemyShips) {
            if (s.isAlive()) {
                // Differentiate between large and small enemy ships for firing bullets
                if(s.getType()==AlienType.D){
                    // Larger ships have a different firing rate
                    if (random.nextInt(50) == 1) {
                        Bullet b = s.fireBig(player);
                        if (b != null) {
                            eBullets.add(b);
                        }
                    }
                }else if(s.getType()==AlienType.E){
                    // Smaller ships fire more frequently
                    if (random.nextInt(25) == 1) {
                        Bullet b = s.fire(player);
                        if (b != null) {
                            eBullets.add(b);
                        }
                    }
                }
            }
        }
        return eBullets;
    }
    // Add an enemy ship to the swarm
    public void addEnemyShip(Asteroids enemyShip) {
        swarm.addEnemyShip(enemyShip);
    }
    // Get enemy ships from the swarm
    public List<Asteroids> getEnemyShips() {
        return swarm.getEnemyShips();
    }
    // Add asteroids to the swarm
    public void add(Asteroids asteroids) {
        swarm.add(asteroids);
    }
    // Reset the level, primarily by reinitializing the swarm
    public void reset() {
        swarm = new Swarm(game, currentLevel);

    }
}
