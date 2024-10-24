package si.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private  boolean isAccelerating;
    private boolean up;
    private boolean down;
    private boolean fire;
    private boolean pause;
    private boolean rotateClockwise;
    private boolean rotateAntiClockwise;

    /*public boolean isPressingLeft() {
        return left;
    }

     */
    public boolean isPressingUp() {
        return up;
    }
    public boolean isPressingDown() {
        return down;
    }
    /*public boolean isPressingRight() {
        return right;
    }

     */

    public boolean hasPressedFire() {
        return fire;
    }

    public boolean hasPressedPause() {
        return pause;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pause = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rotateAntiClockwise = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rotateClockwise = true;
        }else if(e.getKeyCode()==KeyEvent.VK_UP) {
            up=true;
            isAccelerating = true;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            down=true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rotateAntiClockwise = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rotateClockwise = false;
        }else if(e.getKeyCode()==KeyEvent.VK_UP) {
             up=false;
             isAccelerating=false;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            down=false;
        }
    }
    public boolean isPressingRotateClockwise() {
        return rotateClockwise;
    }

    public boolean isPressingRotateAntiClockwise() {
        return rotateAntiClockwise;
    }
    public void resetPause() {
        pause = false;
    }

    public void resetFire() {
        fire = false;
    }
}
