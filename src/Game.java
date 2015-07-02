import level.Level;
import screen.Screen;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private boolean running;

    private Level level;

    private Screen screen;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); // links it to image

    private void start() {
        running = true;
        new Thread(this).start();
    }

    // taken from Minicraft by Notch
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick =  1000000000.0 / 60; // 60 ticks per second
        int frames = 0; // for debug
        int ticks = 0;
        long lastTimer1 = System.currentTimeMillis(); // for debug

        init();

        while(running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            while(unprocessed >= 1) {
                ticks++;
                tick();
                unprocessed--;
            }

            try {
                Thread.sleep(2);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            
            frames++;
            render();
            
            if(System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " ticks, " + frames + " fps");
                frames = ticks = 0;
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }

        int xOffset = 0; // these can be dynamic later
        int yOffset = 0;

        level.renderBackground(screen, xOffset, yOffset);
        level.renderSprites(screen, xOffset, yOffset);

        System.arraycopy(screen.pixels, 0, pixels, 0, WIDTH * HEIGHT);

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight()); // black background

        int xo = (getWidth() - WIDTH) / 2;
        int yo = (getHeight() - HEIGHT) / 2;
        g.drawImage(image, xo, yo, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    private void tick() {
        // nothing to be done here yet
    }

    private void init() {
        screen = new Screen(WIDTH, HEIGHT);
        level = new Level(30, 30);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame("Render Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.add(game, BorderLayout.CENTER);
        frame.add(game);
        frame.setResizable(false); // do this before pack to avoid border
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }
}
