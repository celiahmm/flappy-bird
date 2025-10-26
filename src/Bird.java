import java.awt.*;

/**
 * Represents the bird in the Flappy Bird game
 */
public class Bird {
	int x;
	int y;
	int width;
	int height;
	Image img;

	public Bird(int x, int y, int width, int height, Image img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}
}