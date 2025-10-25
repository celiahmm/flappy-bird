import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

	int boardWidth = 360;
	int boardHeight = 640;


	// images
	Image backgroundImg;
	Image birdImg;
	Image topPipeImg;
	Image bottomPipeImg;

	// bird
	int birdX = boardWidth / 8;
	int birdY = boardHeight / 2;
	int birdWidth = 34;
	int birdHeight = 24;

	class Bird {
		int x = birdX;
		int y = birdY;
		int width = birdWidth;
		int height= birdHeight;
		Image img;

		Bird(Image img) {
			this.img = img;
		}
	}

	// pipes
	int pipeX = boardWidth;
	int pipeY = 0;
	int pipeWidth = 64; // scaled by 1/6
	int pipeHeight = 512;



	// game logic
	Bird bird;
	int velocityY = 0;
	int gravity = 1;

	Timer gameLoop;

	FlappyBird() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		setFocusable(true);
		addKeyListener(this);

		// load images
		backgroundImg = new ImageIcon(getClass().getResource("./bg.png")).getImage();
		birdImg = new ImageIcon(getClass().getResource("bird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();

		// bird
		bird = new Bird(birdImg);

		// game timer
		gameLoop = new Timer(1000/60, this); // 60fps
		gameLoop.start();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// background
		g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

		//bird
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
	}

	public void move() {
		// bird
		velocityY += gravity;
		bird.y += velocityY;
		bird.y = Math.max(bird.y, 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint(); // call paintComponent()
	}



	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			velocityY = -9 ;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
