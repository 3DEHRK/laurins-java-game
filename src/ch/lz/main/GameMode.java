package ch.lz.main;

import ch.lz.main.gameobjects.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

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
        healthCounterGoal = r.nextInt(10000);
        weakCounterGoal = r.nextInt(10000);
    }

    public void tick(){
        if(firstTick){
            hud.health = 100;
            hud.score = 0;
            hud.end = false;
            hud.stamina = 100;

            try {
                File myObj = new File("highscore.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    try {
                        hud.highscore = Integer.parseInt(data);
                    } catch (NumberFormatException e){
                        System.out.println("Unable to parse highscore.txt");
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to load highscore.txt");
            }

            handler.getObjects().clear();
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.PLAYER, handler));
            for (int i = 0; i < 20; i++){
                handler.addObject(new Ray(0,Game.HEIGHT/2,ID.RAY, Color.BLUE, handler, i, 0.02f));
                handler.addObject(new Ray(0,Game.HEIGHT/2,ID.RAY, Color.BLUE, handler, i*-1, 0.02f));
            }
            firstTick = false;
        }

        if(hud.health < 1)
            endGame();

        if(healthCounter > healthCounterGoal){
            healthCounterGoal = r.nextInt(10000);
            handler.addObject(new Health(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT),ID.HEALTH, handler));
            healthCounter = 0;
        }
        healthCounter++;

        if(weakCounter > weakCounterGoal){
            weakCounterGoal = r.nextInt(10000);
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
            if (hud.score > hud.highscore){
                hud.highscore = hud.score;

                try {
                    File myObj = new File("highscore.txt");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        try {
                            FileWriter myWriter = new FileWriter("highscore.txt");
                            myWriter.write("" + hud.highscore);
                            myWriter.close();
                            System.out.println("Highscore saved");
                        } catch (IOException e) {
                            System.out.println("Couldn't write to highscore.txt");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Couldn't create highscore.txt'");
                }
            }
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
