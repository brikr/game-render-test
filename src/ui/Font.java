package ui;

import screen.Screen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Font {
    public final int F_SIZE = 16;
    public int[][] pixels; // [char][pixels]

    public Font(String imageName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pixels = new int[256][F_SIZE * F_SIZE];
        for(int i = 0; i < 256; i++) {
            pixels[i] = img.getRGB((i % 16) * F_SIZE, (i / 16) * F_SIZE, F_SIZE, F_SIZE, null, 0, F_SIZE);
        }
    }

    public void renderText(Screen screen, String text, int xo, int yo) {
        for(char c : text.toCharArray()) {
            for(int y = 0; y < F_SIZE; y++) {
                for(int x = 0; x < F_SIZE; x++) {
                    if(x + xo < 0 || y + yo < 0) continue;
                    if(x + xo >= screen.width || y + yo >= screen.height) continue;
                    if(this.pixels[c][x + y * F_SIZE] == 0xFFFF00DC || this.pixels[c][x + y * F_SIZE] == 0xFF7F006E) continue; // don't draw the pink
                    screen.pixels[(x + xo) + (y + yo) * screen.width] = this.pixels[c][x + y * F_SIZE];
                }
            }
            xo += F_SIZE;
        }
    }
}
