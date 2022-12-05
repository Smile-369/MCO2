package Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed,rightPressed,leftPressed,cPressed,xPressed,menuPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code =e.getKeyCode();
        if(code==KeyEvent.VK_UP){
            upPressed=true;
        }
        if(code==KeyEvent.VK_LEFT){
            leftPressed=true;
        }
        if(code==KeyEvent.VK_DOWN){
            downPressed=true;
        }
        if(code==KeyEvent.VK_RIGHT){
            rightPressed=true;
        }
        if(code==KeyEvent.VK_C){
            cPressed=true;

        }
        if(code==KeyEvent.VK_X){
            xPressed=true;
        }
        if(code==KeyEvent.VK_ENTER){
            if(!menuPressed){
                menuPressed=true;
            }else{
                menuPressed=false;
            }
        }
        if(code==KeyEvent.VK_1){

        }
        if(code==KeyEvent.VK_2){

        }
        if(code==KeyEvent.VK_3){

        }
        if(code==KeyEvent.VK_4){

        }
        if(code==KeyEvent.VK_5){

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code =e.getKeyCode();
        if(code==KeyEvent.VK_W||code==KeyEvent.VK_UP){
            upPressed=false;
        }
        if(code==KeyEvent.VK_A||code==KeyEvent.VK_LEFT){
            leftPressed=false;
        }
        if(code==KeyEvent.VK_S||code==KeyEvent.VK_DOWN){
            downPressed=false;
        }
        if(code==KeyEvent.VK_D||code==KeyEvent.VK_RIGHT){
            rightPressed=false;
        }
        if(code==KeyEvent.VK_C){
            cPressed=false;

        }
        if(code==KeyEvent.VK_X){
            xPressed=false;

        }

    }
}
