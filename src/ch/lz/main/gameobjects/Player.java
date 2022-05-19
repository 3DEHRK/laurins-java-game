package ch.lz.main.gameobjects;

import ch.lz.main.*;
import ch.lz.main.Handler;

import java.awt.*;

public class Player extends GameObject {

    private Handler handler;
    private HUD hud;
    private int size = 32;

    public Player(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
        this.hud = handler.getHUD();
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,size,size);
    }

    public void tick(){
        x += velX;
        y += velY;
        x = Game.clamp(x, 0, Game.WIDTH - 40);
        y = Game.clamp(y, 0, Game.HEIGHT - 60);

        collision();
    }

    private void collision(){
        for (int i = 0; i < handler.getObjects().size(); i++) {

            GameObject tempObject = handler.getObjects().get(i);
            if (tempObject.getBounds() == null)
                return;

            if (getBounds().intersects(tempObject.getBounds())) {
                if(tempObject.getId() == ID.BASIC_ENEMY){
                    hud.health -= 10;
                    return;
                }
                if(tempObject.getId() == ID.HEALTH){
                    hud.health += 100;
                    handler.removeObject(tempObject);
                    return;
                }
                if(tempObject.getId() == ID.BASIC_ENEMY_WEAK) {
                    handler.removeObject(tempObject);
                    return;
                }
                if(tempObject.getId() == ID.WEAK) {
                    handler.removeObject(tempObject);
                    for (int x = 0; x < handler.getObjects().size(); x++){
                        tempObject = handler.getObjects().get(x);
                        if(tempObject.getId() == ID.BASIC_ENEMY){
                            BasicEnemy tempBasicEnemy = (BasicEnemy) tempObject;
                            tempBasicEnemy.weak(true);
                        }
                    }
                    return;
                }
            }
        }
    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLUE);
        g.fillRect(x+1, y+1, size-2,size-2);
    }
}
