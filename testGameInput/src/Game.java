import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 120;
	private static final int HEIGHT = WIDTH / 12 * 9;
	private static final int SCALE = 2;
	private final String TITLE = "2D Game";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);//buffer whole window
	private BufferedImage spriteSheet = null;
	
	private Player p;

	public static void main (String args[]) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); //can't resize
		frame.setLocationRelativeTo(null); //sets function to null - doesnt let it set location
		frame.setVisible(true);
		
		game.start();
	}
	
	/**
	 * @return the spriteSheet
	 */
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public void init() throws IOException {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		spriteSheet = loader.loadImage("/sprite2.png");
//		SpriteSheet ss = new SpriteSheet(spriteSheet);
		addKeyListener(new KeyboardInput(this));
		p = new Player(20, 20, this);
//		player = ss.grabImage(1, 1, 32, 32);
	}

	private synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this); //new thread for game class?
		thread.start(); // calls run method
	}
	
	private synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() { //game loop - Game calls run at begin
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; //change in time to catch up
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) { //if more than 1000ms (1s) passed, print calc
				timer += 1000;
				System.out.println(updates + "Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
			//game loop change for timing instead
		}
		stop(); //after exit game loop, stop game
	}
	
	private void tick() { //everything in game that updates - everything in here will occur with each game loop tick
		p.tick(); //update player with each tick .. player's tick also has nothing..
	}
	
	private void render() { //everything game that renders
		BufferStrategy bs = this.getBufferStrategy(); //this refers to canvas class - inherited as game extends canvas
		if (bs == null) {
			createBufferStrategy(3); //3 for implementing triple buffering. Load up 2 other screens whilst one is being displayed
			return;
		}
		Graphics g = bs.getDrawGraphics(); //create graphics context for drawing buffers - now use g as graphics
		/////// Draw stuff
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		p.render(g); //draw image and position
//		g.drawImage(player, 100, 100, this);
		//////
		g.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
//			p.setX(p.getX() + 5); //update with velocity next
			p.setVelX(2);
			break;
		case KeyEvent.VK_LEFT:
			p.setVelX(-2);
			break;
		case KeyEvent.VK_DOWN:
			p.setVelY(2);
//			p.setY(p.getY() + 5);
			break;
		case KeyEvent.VK_UP:
			p.setVelY(-2);
//			p.setY(p.getY() - 5);
			break;
		default:
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
//			p.setX(p.getX() + 5); //update with velocity next
			p.setVelX(0);
			break;
		case KeyEvent.VK_LEFT:
			p.setVelX(0);
			break;
		case KeyEvent.VK_DOWN:
			p.setVelY(0);
//			p.setY(p.getY() + 5);
			break;
		case KeyEvent.VK_UP:
			p.setVelY(0);
//			p.setY(p.getY() - 5);
			break;
		default:
			break;
		}
	}
}
