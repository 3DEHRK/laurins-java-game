package ch.lz.main;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    private LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private HUD hud = new HUD();
    private GameMode gameMode = new GameMode(this);

    public void tick(){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
        gameMode.tick(hud.highscore);
        hud.tick();
    }

    public void render(Graphics g){
        for(int i = 0; i < objects.size(); i++){
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
        hud.render(g);
    }

    public void addObject(GameObject object){
        this.objects.add(object);
    }

    public void removeObject(GameObject object){
        this.objects.remove(object);
    }

    public HUD getHUD(){
        return hud;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public LinkedList<GameObject> getObjects() {
        return objects;
    }
}
