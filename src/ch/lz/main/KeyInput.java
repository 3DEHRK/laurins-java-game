package ch.lz.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private HUD hud;
    private boolean[] keyDown = new boolean[4];

    public KeyInput(Handler handler){
        this.handler = handler;
        this.hud = handler.getHUD();

        keyDown[0]=false;
        keyDown[1]=false;
        keyDown[2]=false;
        keyDown[3]=false;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            handler.getObjects().get(0).setVelY(-6);
            keyDown[0]=true;
        }
        if(key == KeyEvent.VK_S){
            handler.getObjects().get(0).setVelY(6);
            keyDown[1]=true;
        }
        if(key == KeyEvent.VK_A){
            handler.getObjects().get(0).setVelX(-6);
            keyDown[2]=true;
        }
        if(key == KeyEvent.VK_D){
            handler.getObjects().get(0).setVelX(6);
            keyDown[3]=true;
        }

        if(key == KeyEvent.VK_ESCAPE) System.exit(1);
        if(key == KeyEvent.VK_ENTER) handler.getGameMode().restartGame();
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) keyDown[0]=false;
        if(key == KeyEvent.VK_S) keyDown[1]=false;
        if(key == KeyEvent.VK_A) keyDown[2]=false;
        if(key == KeyEvent.VK_D) keyDown[3]=false;

        if(!keyDown[0] && !keyDown[1]) handler.getObjects().get(0).setVelY(0);
        if(!keyDown[2] && !keyDown[3]) handler.getObjects().get(0).setVelX(0);
    }
}
