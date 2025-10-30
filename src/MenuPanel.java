import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

	private Button playButton;
	private Button scoresButton;
	private Button themeButton;

	private Image backgroundImg;
	private Image logoImg;


	MenuPanel() {
		this.setPreferredSize(new Dimension(360, 640));
		this.setFocusable(true);

		// load images
		try {
			backgroundImg = new ImageIcon(getClass().getResource("images/bg.png")).getImage();
			logoImg = new ImageIcon(getClass().getResource("images/logo.png")).getImage();
		} catch (Exception _) {
			System.out.println("Error while loading images");
		}

		// create buttons
		playButton = new Button("PLAY", 80, 180, 200, 65);
		scoresButton = new Button("SCORES", 80, 270, 200, 65);
		themeButton = new Button("THEME", 80, 360, 200, 65);

		// mouse listeners
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (playButton.contains(e.getPoint())) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MenuPanel.this);
					frame.getContentPane().removeAll();
					FlappyBird flappyBird = new FlappyBird();
					frame.add(flappyBird);
					flappyBird.requestFocus();
					frame.revalidate();
				}
				// TODO: add actions for scoresButton and themeButton
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				playButton.setHover(playButton.contains(e.getPoint()));
				scoresButton.setHover(scoresButton.contains(e.getPoint()));
				themeButton.setHover(themeButton.contains(e.getPoint()));
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw background
		if (backgroundImg != null) {
			g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), null);
		}

		// draw logo
		if (logoImg != null) {
			int originalWidth = logoImg.getWidth(null);
			int originalHeight = logoImg.getHeight(null);

			int maxWidth = 285;
			int logoWidth = originalWidth;
			int logoHeight = originalHeight;

			if (originalWidth > maxWidth) {
				logoWidth = maxWidth;
				logoHeight = (int) (originalHeight * ((double) maxWidth / originalWidth));
			}

			int logoX = (getWidth() - logoWidth) / 2;
			int logoY = 50;
			g.drawImage(logoImg, logoX, logoY, logoWidth, logoHeight, null);
		}

		// draw buttons
		playButton.draw(g);
		scoresButton.draw(g);
		themeButton.draw(g);
	}
}