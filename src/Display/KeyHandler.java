package Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * is class implements key listener and sets certain values to true when a key event occurs
 */
public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed, cPressed, xPressed, menuPressed, sPressed, aPressed;
    public boolean[] numPressed = new boolean[9];
    public int choice, cropChoice = 1;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * checks to see if a key is pressed and if it is sets its corresponding value to true
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_C) {
            cPressed = true;

        }
        if (code == KeyEvent.VK_X) {
            xPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            menuPressed = !menuPressed;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = true;

        }
        if (code == KeyEvent.VK_S) {
            sPressed = true;

        }

        if (code == KeyEvent.VK_1) {
            numPressed[1] = true;
        }
        if (code == KeyEvent.VK_2) {
            numPressed[2] = true;
        }
        if (code == KeyEvent.VK_3) {
            numPressed[3] = true;
        }
        if (code == KeyEvent.VK_4) {
            numPressed[4] = true;
        }
        if (code == KeyEvent.VK_5) {
            numPressed[5] = true;
        }
        if (code == KeyEvent.VK_6) {
            numPressed[6] = true;
        }
        if (code == KeyEvent.VK_7) {
            numPressed[7] = true;
        }
        if (code == KeyEvent.VK_8) {
            numPressed[8] = true;

        }
        if (numPressed[1]) {
            choice = 1;
        }
        if (numPressed[2]) {
            choice = 2;
        }
        if (numPressed[3]) {
            choice = 3;
        }
        if (numPressed[4]) {
            choice = 4;
        }
        if (numPressed[5]) {
            choice = 5;
        }
        if (numPressed[6]) {
            choice = 6;
        }
        if (numPressed[7]) {
            choice = 7;
        }
        if (numPressed[8]) {
            choice = 8;
        }

        if (choice == 3) {
            if (cropChoice < 1) {
                cropChoice = 8;
            }
            if (cropChoice > 8) {
                cropChoice = 1;

            }
            if (aPressed) {
                cropChoice--;
            }
            if (sPressed) {
                cropChoice++;
            }
        }


    }

    /**
     * checks to see if a key is released and sets its corresponding value to false
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_C) {
            cPressed = false;

        }
        if (code == KeyEvent.VK_X) {
            xPressed = false;

        }
        if (code == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (code == KeyEvent.VK_8) {
            numPressed[8] = false;
        }
        if (code == KeyEvent.VK_1) {
            numPressed[1] = false;
        }
        if (code == KeyEvent.VK_2) {
            numPressed[2] = false;
        }
        if (code == KeyEvent.VK_3) {
            numPressed[3] = false;
        }
        if (code == KeyEvent.VK_4) {
            numPressed[4] = false;
        }
        if (code == KeyEvent.VK_5) {
            numPressed[5] = false;
        }
        if (code == KeyEvent.VK_6) {
            numPressed[6] = false;
        }
        if (code == KeyEvent.VK_7) {
            numPressed[7] = false;
        }
        if (code == KeyEvent.VK_8) {
            numPressed[8] = false;

        }
    }


}
