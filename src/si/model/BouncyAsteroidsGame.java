package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncyAsteroidsGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    public static final int BUNKER_TOP = 350;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    private int playerLives;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private ArrayList<Hittable> targets;
    private ArrayList<Level> level;
    private int playerScore;
    PlayerListener listener;
    private Player player;
    private int currentLevel = 0;
    private List<Asteroids> largeAsteroids;

    private long startTime;
    private boolean enemySpaceshipTriggered;
    public BouncyAsteroidsGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
        largeAsteroids = level.get(currentLevel).getEnemyShips();

    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            player.tick();
            targets.clear();
            targets.addAll(level.get(currentLevel).getHittable());
            largeAsteroids = level.get(currentLevel).move();
            checkForEnemySpaceship();
            List<Bullet> newEnemyBullets = level.get(currentLevel).updateEnemyShipsAndFireBullets(player);
            enemyBullets.addAll(newEnemyBullets);
            checkCollisions();
            playerBullets();
            enemyBullets();
            targets.add(player);
            movePlayer();
        }
    }
    private void movePlayer() {
        player.setIsAccelerating(false);
        if (listener.hasPressedFire()) {
            Bullet b = player.fire(player);
            if (b != null) {
                playerBullets.add(b);
                listener.resetFire();
            }
        }
        if(listener.isPressingUp()) {
            player.setIsAccelerating(true);
            player.move(0, -1);
        }else {
            player.setIsAccelerating(false);
            player.move(0, -1);

        }
        if (listener.isPressingRotateClockwise()) {
            player.rotateClockwise(1.0d);
        } else if (listener.isPressingRotateAntiClockwise()) {
            player.rotateAntiClockwise(1.0d);
        }
        player.keepMove();
    }


    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBox().intersects(SCREEN_BOUNDS)) {
                playerBullets.get(i).playerBulletsMove();
                for (Hittable t : targets) {
                    if (t != playerBullets.get(i)) {
                        if (t.isHit(playerBullets.get(i))) {
                            int preScore=playerScore;
                            playerScore += t.getPoints();
                            int tempScore=playerScore;
                            if(tempScore/10000>preScore/10000){
                                playerLives+=3;
                            }
                            playerBullets.get(i).destroy();
                        }
                    }
                }
            } else {
                remove.add(playerBullets.get(i));
            }
        }
        playerBullets.removeAll(remove);
    }

   private void enemyBullets() {
       List<Bullet> remove = new ArrayList<Bullet>();
       for (Bullet b : enemyBullets) {
           if (b.isAlive() && b.getHitBox().intersects(SCREEN_BOUNDS)) {
               b.move();
               if (player.isHit(b)) {
                   playerLives--;
                   pause = true;
                   b.destroy();
               }
           } else {
               remove.add(b);
           }
       }
       enemyBullets.removeAll(remove);
   }



    private void checkForEnemySpaceship() {
        if (!enemySpaceshipTriggered && System.currentTimeMillis() - startTime > 30000) {
            enemySpaceshipTriggered = true;
            if(currentLevel<6) {
                createLargeEnemySpaceship();
            }else{
                Random rand=new Random();
                 int random=rand.nextInt(2)+1;
                 if(random==1){
                     createLargeEnemySpaceship();
                 }else {
                     createSmallEnemySpaceship();
                 }
            }
        }
    }
    private void createLargeEnemySpaceship() {
        Random rand = new Random();
        int x = 50 + rand.nextInt(BouncyAsteroidsGame.SCREEN_WIDTH -50);
        int y =  50 + rand.nextInt(BouncyAsteroidsGame.SCREEN_HEIGHT-85);;

        double angle = Math.toRadians(rand.nextInt(360));
        Asteroids enemySpaceship1 = new Asteroids(x, y, AlienType.D, angle, this);
        enemySpaceship1.setSpeed(1.0);
        this.level.get(currentLevel).add(enemySpaceship1);
        level.get(currentLevel).addEnemyShip(enemySpaceship1);

    }
    private void createSmallEnemySpaceship() {
        Random rand = new Random();
        int x = 50 + rand.nextInt(BouncyAsteroidsGame.SCREEN_WIDTH -100);
        int y =  50 + rand.nextInt(BouncyAsteroidsGame.SCREEN_HEIGHT-100);; // 假设敌舰从顶部出现
        double angle = Math.toRadians(rand.nextInt(360));
        Asteroids enemySpaceship2 = new Asteroids(x, y, AlienType.E, angle, this);
        enemySpaceship2.setSpeed(2.0);
        this.level.get(currentLevel).add(enemySpaceship2);
        level.get(currentLevel).addEnemyShip(enemySpaceship2);

        }
    public void checkCollisions() {
        for (Asteroids asteroid : largeAsteroids) {
            if (player.getHitBox().intersects(asteroid.getHitBox())) {
                handlePlayerAsteroidCollision();
                return;
            }
        }
    }
    private void handlePlayerAsteroidCollision() {
            playerLives--;
            if (playerLives <= 0) {
                player.resetDestroyed();
            } else {
                player.resetDestroyed();
            }
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        player = new Player();
        level = new ArrayList<Level>();
        level.add(new Level(this, 0));
        startTime = System.currentTimeMillis();
        enemySpaceshipTriggered = false;

    }
    public boolean setTriggered(){
        enemySpaceshipTriggered=false;
       return enemySpaceshipTriggered;
    }
    public void setStartTime(){
        this.startTime=System.currentTimeMillis();
    }

    @Override

    public boolean isLevelFinished() {
        int noShips = level.get(currentLevel).getShipsRemaining();
        return level.get(currentLevel).getBottomY() >= BUNKER_TOP || noShips == 0;
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public void moveToNextLevel() {
        startTime = System.currentTimeMillis();
        enemySpaceshipTriggered = false;
        pause = true;
        currentLevel++;
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        if (currentLevel >= level.size()) {
            level.add(new Level(this, currentLevel));
        }
    }

    @Override
    public boolean isGameOver() {
        return !(playerLives>0);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getShip() {
        return player;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        bullets.addAll(level.get(currentLevel).updateEnemyShipsAndFireBullets(player));
        return bullets;
    }

    public List<Asteroids> getEnemyShips() {
        return level.get(currentLevel).getEnemyShips();
    }


}
