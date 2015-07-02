package level;

import screen.Screen;

public class Level {
    private int width, height;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void renderBackground(Screen screen, int xOffset, int yOffset) {
        for(int y = 0; y < screen.height; y++) {
            for(int x = 0; x < screen.width; x++) {
                screen.pixels[x + y * screen.width] = 0;
            }
        }
    }

    public void renderSprites(Screen screen, int xOffset, int yOffset) {

    }
}
