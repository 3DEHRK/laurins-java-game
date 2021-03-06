package ch.lz.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 900, HEIGHT = WIDTH / 12 * 9;
    public static boolean paused = false;
    public static Color background = Color.BLACK;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;

    public Game(){
        handler = new Handler();
        hud = handler.getHUD();
        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "LaurinsJavaGame", this);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                hud.fps = frames;
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        if (paused)
            return;
        handler.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(background);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max){
        if(var > max)
            return max;
        if(var < min)
            return min;
        return var;
    }

    public static void main(String ... args){
        new Game();
    }
}
