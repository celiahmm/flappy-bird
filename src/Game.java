import javax.swing.*;

/**
 * Main entry point for Flappy Bird game
 */
public class Game {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Flappy Bird");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(360, 640);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		MenuPanel menu = new MenuPanel();

		frame.add(menu);

		frame.pack();

		frame.setVisible(true);
	}
}

/* TODO - Future improvements:
 *   - Add high scores tracking
 *   - Improve UI/UX
 *   - Game reskin options
 */
