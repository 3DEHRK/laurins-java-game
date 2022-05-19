package ch.lz.main;

import ch.lz.main.gameobjects.BasicEnemy;
import ch.lz.main.gameobjects.Health;
import ch.lz.main.gameobjects.Player;
import ch.lz.main.gameobjects.Weak;

import java.util.Random;

public class GameMode {

    private Handler handler;
    private HUD hud;
    private Random r;
    private int healthCounter = 0;
    private int healthCounterGoal;
    private int weakCounter = 0;
    private int weakCounterGoal;
    private int enemyCounter = 0;
    private int enemyCounterGoal = 0;
    private int scoreCounter = 0;
    private boolean firstTick = true;

    public GameMode(Handler handler){
        this.handler = handler;
        this.hud = handler.getHUD();
        r = new Random();
        healthCounterGoal = r.nextInt(15000);
        weakCounterGoal = r.nextInt(15000);
    }

    public void tick(){
        if(firstTick){
            hud.health = 100;
            hud.score = 0;
            hud.end = false;
            handler.getObjects().clear();
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.PLAYER, handler));
            firstTick = false;
        }

        if(hud.health < 1)
            endGame();

        if(healthCounter > healthCounterGoal){
            healthCounterGoal = r.nextInt(15000);
            handler.addObject(new Health(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT),ID.HEALTH, handler));
            healthCounter = 0;
        }
        healthCounter++;

        if(weakCounter > weakCounterGoal){
            weakCounterGoal = r.nextInt(15000);
            handler.addObject(new Weak(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT),ID.WEAK, handler));
            weakCounter = 0;
        }
        weakCounter++;

        int tempEnemies = 0;
        for (int i = 0; i < handler.getObjects().size(); i++){
            GameObject tempObject = handler.getObjects().get(i);
            if(tempObject.getId() == ID.BASIC_ENEMY || tempObject.getId() == ID.BASIC_ENEMY_WEAK){
                tempEnemies++;
            }
        }
        enemyCounterGoal = tempEnemies*100+200;

        if(enemyCounter > enemyCounterGoal){
            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT),ID.BASIC_ENEMY, handler));
            enemyCounter = 0;
        }
        enemyCounter++;

        if(scoreCounter > 500){
            hud.score++;
            scoreCounter = 0;
        }
        scoreCounter++;
    }

    public void endGame(){
        hud.end = true;
        Game.paused = true;
    }

    public void restartGame(){
        firstTick = true;
        Game.paused = false;
    }
}
