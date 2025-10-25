import javax.swing.*;

public class Game {
    public static void main(String[] args) {

	    JFrame frame = new JFrame("Flappy Bird");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(360,640);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		FlappyBird flappyBird = new FlappyBird();
		frame.add(flappyBird);
		frame.pack();
		flappyBird.requestFocus();

	    frame.setVisible(true);

    }
}
