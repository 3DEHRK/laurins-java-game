package ch.lz.main.gameobjects;

import ch.lz.main.GameObject;
import ch.lz.main.Handler;
import ch.lz.main.ID;

import java.awt.*;
import java.util.Random;

public class Trail extends GameObject {

    private float alpha = 1;
    private Handler handler;
    private Color color;
    private float life;
    private int size;
    public GameObject owner;
    private Random r;

    public Trail(int x, int y, ID id, Color color, int size, float life, boolean wind, GameObject owner, Handler handler) {
        super(x, y, id);
        this.color = color;
        this.life = life;
        this.handler = handler;
        this.size = size;
        this.owner = owner;
        r = new Random();
        if (wind){
            velX = r.nextInt(2);
            velY = r.nextInt(2);
        }
    }

    public void tick() {
        if(alpha > life){
            alpha -= life - 0.001f;
        }else handler.removeObject(this);
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));

        g.setColor(color);
        g.fillRect(x,y,size,size);

        g2d.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,size,size);
    }
}
