package screen;

public class Screen {
    public final int width;
    public final int height;
    public int[] pixels;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
}
