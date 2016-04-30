import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	private double x;
	private double y;
	
	private double velX = 0;
	private double velY = 0;
	private BufferedImage player;
	
	public Player (double x, double y, Game game) {
		this.x = x;
		this.y = y;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage(1, 1, 32, 32);
		
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	public void tick() {
		x += velX;
		y += velY;
		//put collisionboundaries here; if x <= left barrier x = 0 -- reset to zero so never goes outside
	}
	
	/**
	 * @param velX the velX to set
	 */
	public void setVelX(double velX) {
		this.velX = velX;
	}

	/**
	 * @param velY the velY to set
	 */
	public void setVelY(double velY) {
		this.velY = velY;
	}

	public void render (Graphics g) {
		g.drawImage(player, (int) x, (int) y, null);
	}
}
