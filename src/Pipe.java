import java.awt.*;

/**
 * Represents a pipe obstacle in the Flappy Bird game
 */
public class Pipe {
    int x;
    int y;
    int width;
    int height;
    Image img;
    boolean passed = false;

    public Pipe(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}


