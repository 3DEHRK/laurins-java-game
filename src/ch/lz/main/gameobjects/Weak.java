package ch.lz.main.gameobjects;

import ch.lz.main.Game;
import ch.lz.main.GameObject;
import ch.lz.main.Handler;
import ch.lz.main.ID;

import java.awt.*;
import java.util.Random;

public class Weak extends GameObject {

    private Handler handler;
    private Random r;
    private int size = 8;

    public Weak(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        r = new Random();
        velY = r.nextInt(5)+1;
        velX = r.nextInt(5)+1;
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

        handler.addObject(new Trail(x,y, ID.TRAIL, Color.CYAN, size, 0.05f, true, this, handler));
    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x,y,size,size);
    }
}
