package ch.lz.main.gameobjects;

import ch.lz.main.Game;
import ch.lz.main.GameObject;
import ch.lz.main.Handler;
import ch.lz.main.ID;
import java.awt.*;
import java.util.Random;

public class BasicEnemy extends GameObject {

    private Handler handler;
    private Random r;
    private int size = 4;
    private Color color = Color.RED;

    private int weakCounter = 100;

    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        r = new Random();
        velY = r.nextInt(6)+2;
        velX = r.nextInt(6)+2;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,size,size);
    }

    public void tick() {
        x += velX;
        y += velY;
        if (y <= 0 || y >= Game.HEIGHT - 50)
            velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 25)
            velX *= -1;

        handler.addObject(new Trail(x,y, ID.TRAIL, color, size, 0.05f, true, this, handler));

        if(weakCounter > 0){
            weakCounter--;
            id = ID.BASIC_ENEMY_WEAK;
            color = Color.GRAY;
            if(weakCounter < 100)
                color = Color.ORANGE;
        }else{
            id = ID.BASIC_ENEMY;
            color = Color.RED;
            weak(false);
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,size,size);
    }

    public void weak(boolean weak){
        if (weak){
            weakCounter = 1000;
        }else{
            weakCounter = 0;
        }
    }

    public int getWeakCounter() {
        return weakCounter;
    }
}
