package ch.lz.main;

import java.awt.*;

public class HUD {

    public int health = 100;
    public int score = 0;
    public boolean end = false;
    private int greenValue = 255;

    public void tick(){
        health = Game.clamp(health, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);
        greenValue = health *2;
    }

    public void render(Graphics g){
        if(end){
            g.setColor(Color.ORANGE);
            g.drawString("Game over, your score is: " + score, Game.WIDTH/2, Game.HEIGHT/2);
            g.setColor(Color.WHITE);
            g.drawString("Press enter to restart!", Game.WIDTH/2, Game.HEIGHT/2+20);
        }

        g.setColor(Color.GRAY);
        g.fillRect(20,20,200,20);

        g.setColor(new Color(255-greenValue, greenValue, 0));
        g.fillRect(20,20, health *2,20);

        g.setColor(Color.WHITE);
        g.drawRect(20,20,200,20);

        g.drawString("Score: " + score, 20, 60);
    }
}
