package level;

import level.tile.TestTile;
import level.tile.Tile;
import screen.Screen;

import java.util.Arrays;

public class Level {
    public final int width, height;
    private static final int T_SIZE = 32;
    private Tile[] tiles;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width * height];
        this.generate();
    }

    public void renderBackground(Screen screen, int xOffset, int yOffset) {
        for(int y = yOffset / T_SIZE; y <= (yOffset + screen.height) / T_SIZE; y++) {
            for(int x = xOffset / T_SIZE; x <= (xOffset + screen.width) / T_SIZE; x++) {
                tiles[x + y * this.width].render(screen, x * T_SIZE - xOffset, y * T_SIZE - yOffset);
            }
        }
//        for(int y = 0; y < screen.height; y += T_SIZE) {
//            for(int x = 0; x < screen.width; x += T_SIZE) {
//                tiles[(x + xOffset) / T_SIZE + (y + yOffset) / T_SIZE * this.width].render(screen, x + (xOffset % T_SIZE * -1), y + (yOffset % T_SIZE * -1)); // render at appropriate x, y
//            }
//        }
    }

    public void renderSprites(Screen screen, int xOffset, int yOffset) {

    }

    public void generate() {
        Arrays.fill(tiles, new TestTile());
    }
}
