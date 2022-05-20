package ch.lz.main.gameobjects;

import ch.lz.main.Game;
import ch.lz.main.GameObject;
import ch.lz.main.Handler;
import ch.lz.main.ID;
import java.awt.*;

public class Ray extends GameObject {

    private Handler handler;
    private Color color;
    private float life;
    private float alpha = 1;

    public Ray(int x, int y, ID id, Color color, Handler handler, int velY, float life) {
        super(x, y, id);
        this.color = color;
        this.handler = handler;
        this.velY = velY;
        this.life = life;
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
        g.drawLine(Game.WIDTH,y,Game.WIDTH*-1,y);

        g2d.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,1,1);
    }
}
