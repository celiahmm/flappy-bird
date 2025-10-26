import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Main game panel for Flappy Bird
 * Handles rendering, game logic, and user input
 */
public class FlappyBird extends JPanel implements ActionListener, KeyListener {

	// BOARD CONSTANTS
	private static final int BOARD_WIDTH = 360;
	private static final int BOARD_HEIGHT = 640;

	// BIRD CONSTANTS
	private static final int BIRD_X = BOARD_WIDTH / 8;
	private static final int BIRD_Y = BOARD_HEIGHT / 2;
	private static final int BIRD_WIDTH = 34;
	private static final int BIRD_HEIGHT = 24;

	// PIPE CONSTANTS
	private static final int PIPE_X = BOARD_WIDTH;
	private static final int PIPE_Y = 0;
	private static final int PIPE_WIDTH = 64;
	private static final int PIPE_HEIGHT = 512;
	private static final int OPENING_SPACE = BOARD_HEIGHT / 4;

	// GAME PHYSICS CONSTANTS
	private static final int VELOCITY_X = -4;
	private static final double GRAVITY = 0.7;
	private static final int JUMP_STRENGTH = -9;

	// TIMER CONSTANTS
	private static final int GAME_SPEED = 1000 / 60; // 60 FPS
	private static final int PIPE_SPAWN_DELAY = 1500; // milliseconds

	// IMAGES & FONT
	private Image backgroundImg;
	private Image birdImg;
	private Image topPipeImg;
	private Image bottomPipeImg;

	// GAME OBJECTS
	private Bird bird;
	private ArrayList<Pipe> pipes;

	// GAME STATE
	private double velocityY = 0;
	private boolean gameOver = false;
	private double score = 0;

	// TIMERS
	private Timer gameLoop;
	private Timer placePipesTimer;

	/**
	 * Constructor initializes the game panel and starts the game
	 */
	FlappyBird() {
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setFocusable(true);
		addKeyListener(this);

		loadImages();
		initializeGame();
		startTimers();
	}

	/**
	 * Load all game images
	 */
	private void loadImages() {
		backgroundImg = new ImageIcon(getClass().getResource("images/bg.png")).getImage();
		birdImg = new ImageIcon(getClass().getResource("images/bird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("images/toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("images/bottompipe.png")).getImage();
	}

	/**
	 * Initialize game objects
	 */
	private void initializeGame() {
		bird = new Bird(BIRD_X, BIRD_Y, BIRD_WIDTH, BIRD_HEIGHT, birdImg);
		pipes = new ArrayList<>();
	}

	/**
	 * Start game timers
	 */
	private void startTimers() {
		// place pipes timer
		placePipesTimer = new Timer(PIPE_SPAWN_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				placePipes();
			}
		});
		placePipesTimer.start();

		// game loop timer
		gameLoop = new Timer(GAME_SPEED, this);
		gameLoop.start();

	}

	/**
	 * Place a new pair of pipes (top and bottom)
	 */
	public void placePipes()  {
		int randomPipeY = (int) (PIPE_Y - PIPE_HEIGHT / 4 - Math.random() * (PIPE_HEIGHT / 2));

		Pipe topPipe = new Pipe(PIPE_X, randomPipeY, PIPE_WIDTH, PIPE_HEIGHT, topPipeImg);
		pipes.add(topPipe);

		Pipe bottomPipe = new Pipe(PIPE_X, topPipe.y + PIPE_HEIGHT + OPENING_SPACE, PIPE_WIDTH, PIPE_HEIGHT, bottomPipeImg);
		pipes.add(bottomPipe);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	/**
	 * Draw all game elements
	 */
	public void draw(Graphics g) {
		// background
		g.drawImage(backgroundImg, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);

		// bird
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

		// pipes
		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
		}

		// score
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		if (gameOver) {
			g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
		} else {
			g.drawString(String.valueOf((int) score), 10, 35);
		}
	}

	/**
	 * Update game state (bird position, pipe positions, collisions)
	 */
	public void move() {
		// bird movement
		velocityY += GRAVITY;
		bird.y += (int) velocityY;
		bird.y = Math.max(bird.y, 0);

		// pipes movement
		for (Pipe pipe : pipes) {
			pipe.x += VELOCITY_X;

			if (!pipe.passed && bird.x > pipe.x + pipe.width) {
				pipe.passed = true;
				score += 0.5;
				SoundPlayer.playSound("sound_effects/point.wav");
			}

			if (collision(bird, pipe)) {
				gameOver = true;
				SoundPlayer.playSound("sound_effects/hit.wav");
			}
		}

		// check if bird fell off-screen
		if (bird.y > BOARD_HEIGHT) {
			gameOver = true;
			SoundPlayer.playSound("sound_effects/die.wav");
		}
	}

	/**
	 * Check if bird collides with a pipe
	 */
	public boolean collision(Bird b, Pipe p) {
		return  b.x < (p.x + p.width) &&
				b.x + b.width > p.x   &&
				b.y < p.y + p.height  &&
				b.y + b.height > p.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint(); // calls paintComponent()
		if (gameOver) {
			placePipesTimer.stop();
			gameLoop.stop();
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			velocityY = JUMP_STRENGTH;
			SoundPlayer.playSound("sound_effects/wing.wav");
			if (gameOver) {
				bird.y = BIRD_Y;
				velocityY = 0;
				pipes.clear();
				score = 0;
				gameOver = false;
				gameLoop.start();
				placePipesTimer.start();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
