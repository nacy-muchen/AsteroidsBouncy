package si.model;

import java.awt.*;
import java.util.Random;

public class Asteroids implements Hittable {
    private  Swarm swarm;
    private boolean alive;
    private double x, y;
    private AlienType type;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    private  double speedX;
    private  double speedY;
    private double angle;
    private BouncyAsteroidsGame game;
    private Rectangle hitBoxA;
    private double speed;
    private boolean hasCrossedScreen = false;


    public Asteroids(int x, int y, AlienType type, double angle,BouncyAsteroidsGame g) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.game = g;
        this.rand = new Random(x * 100 + y);
        this.alive = true;
        Random rand=new Random();
        this.angle=angle;
        this.speed = 0;
        hitBoxA = new Rectangle(x-type.getWidth()/2, y-type.getHeight()/2, type.getWidth(), type.getHeight());
    }

    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }
    public void setSpeed(double speed){
        this.speed=speed;
        this.speedX = Math.cos(angle) * speed;
        this.speedY = Math.sin(angle) * speed;

    }
    @Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x-type.getWidth(), (int) y-type.getHeight(), type.getWidth()*2, type.getHeight()*2);

    }
    public void updateHitBoxA(){
        hitBoxA = new Rectangle((int) x-type.getWidth(), (int) y-type.getHeight(), type.getWidth()*2, type.getHeight()*2);
    }

    public boolean isAlive() {
        return alive;
    }
    public void destroy(){
        alive=false;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }


    public void move(Swarm swarm) {
        this.swarm = swarm;
        x+=speedX;
        y+=speedY;
        if (isAsteroid()) {
            if (x > game.getScreenWidth() - getHitBox().width / 2 - 10 || x < getHitBox().width / 2) {
                angle = Math.PI - angle;
                speedX = -speedX;
            } else if (y > game.getScreenHeight() - getHitBox().height / 2 - 35 || y < getHitBox().height / 2) {
                speedY = -speedY;
                angle = -angle;
            }
        }
        if(isEnemyShip()){
            avoidCollisions();
            if (x > game.getScreenWidth() || x < 0 || y > game.getScreenHeight() || y < 0) {
                if (!hasCrossedScreen) {
                    hasCrossedScreen = true;
                } else {
                    alive = false;
                    game.setStartTime();
                    game.setTriggered();
                }
            }
        }
        updateHitBoxA();

    }

    public Bullet fire(Player player) {
        Bullet bul = null;
        double deltaX = player.getX() - this.x;
        double deltaY = player.getY() - this.y;
        double angleToPlayer = Math.atan2(deltaY, deltaX);
        bul = new Bullet((int)this.x, (int)this.y, "Enemy", angleToPlayer);
        return bul;
    }
    public Bullet fireBig(Player player) {
        Bullet bul = null;
        Random random=new Random();
        double deltaX = player.getX() - this.x;
        double deltaY = player.getY() - this.y;
        double angleToPlayer = Math.atan2(deltaY, deltaX)+Math.toRadians(random.nextInt(40));
        bul = new Bullet((int)this.x, (int)this.y, "Enemy", angleToPlayer);
        return bul;
    }

    public Shape getShape() {
        return new Rectangle();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
    public double getAngle() {
        return angle;
    }

    public AlienType getType() {
        return type;
    }
    public boolean isEnemyShip() {
        return type == AlienType.D || type == AlienType.E;
    }

    public boolean isAsteroid() {
        return type == AlienType.A || type == AlienType.B || type == AlienType.C;
    }
    public void avoidCollisions() {
        double avoidanceRadius = 100;
        for (Asteroids asteroid : swarm.getEnemyShips()) {
            double distance = Math.hypot(x - asteroid.getX(), y - asteroid.getY());
            if (distance < avoidanceRadius) {
                adjustDirection(asteroid);
                break;
            }
        }

        Player player = game.getShip();
        double distanceToPlayer = Math.hypot(x - player.getX(), y - player.getY());
        if (distanceToPlayer < avoidanceRadius) {
            adjustDirection(player);
        }else {
            adjustDirectionFar(player);
        }

    }
        public void adjustDirection(Object obj) {
            double targetX = 0;
            double targetY = 0;
            if (obj instanceof Player) {
                Player player = (Player) obj;
                targetX = player.getX();
                targetY = player.getY();
            } else if (obj instanceof Asteroids) {
                Asteroids asteroid = (Asteroids) obj;
                targetX = asteroid.getX();
                targetY = asteroid.getY();
            }
            double diffX = x - targetX;
            double diffY = y - targetY;
            double newAngle;

            if (diffX != 0) {
                newAngle = Math.atan2(diffY, diffX) + Math.PI;
            } else {
                newAngle = diffY > 0 ? 0 : Math.PI;
            }
            speedX = -Math.cos(newAngle) * speed;
            speedY = -Math.sin(newAngle) * speed;
    }
    public void adjustDirectionFar(Object obj) {
        double targetX = 0;
        double targetY = 0;

        Player player = (Player) obj;
        targetX = player.getX();
        targetY = player.getY();

        double diffX = x - targetX;
        double diffY = y - targetY;
        double newAngle;
        Random rand=new Random();

        if (diffX != 0) {
            newAngle = Math.atan2(diffY, diffX) + Math.PI+ Math.toRadians(rand.nextInt(50));
        } else {
            newAngle = diffY > 0 ? 0 : Math.PI;
        }
        speedX = Math.cos(newAngle) * speed;
        speedY = Math.sin(newAngle) * speed;
    }

}
