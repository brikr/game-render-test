package level.tile;

import screen.Screen;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tile {
    private static final int T_SIZE = 32;
    int[] pixels;
    boolean passable = true;

    public Tile(String imageName) {
        try {
            pixels = ImageIO.read(new File(imageName)).getRGB(0, 0, T_SIZE, T_SIZE, null, 0, T_SIZE);
        } catch (IOException e) {
            pixels = new int[T_SIZE * T_SIZE]; // black if unable to read file
            e.printStackTrace();
        }
    }

    public void render(Screen screen, int xo, int yo) {
        for(int y = 0; y < T_SIZE; y++) {
            for (int x = 0; x < T_SIZE; x++) {
                if(x + xo < 0 || y + yo < 0) continue;
                if(x + xo >= screen.width || y + yo >= screen.height) continue;
                screen.pixels[(x + xo) + (y + yo) * screen.width] = this.pixels[x + y * T_SIZE];
            }
        }
    }
}
