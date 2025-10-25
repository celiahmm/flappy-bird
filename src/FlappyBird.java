import javax.swing.*;

public class FlappyBird extends JFrame {
	JFrame frame;
	JLabel background = new JLabel(new ImageIcon("src/images/bg.png"));

	FlappyBird() {
		frame = new JFrame("Flappy Bird");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(360,640);
		frame.setResizable(false);

		frame.setContentPane(background);
		frame.setVisible(true);

	}

}
