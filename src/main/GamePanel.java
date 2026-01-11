package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	// screen size
	final int originalTileSize = 64;
	final int scale = 1;
	public int tileSize = originalTileSize;
	final int maxScreenCol = 20;
	final int maxScreenRow = 12;
	final int screenWidth = maxScreenCol * tileSize;
	final int screenHeight = maxScreenRow * tileSize;

	final int FPS = 60;

	Thread gameThread;
	KeyHandler keyH = new KeyHandler();
	Player player = new Player(this,keyH);

	int playerX = 100;
	int playerY = 100;
	int PlayerSpeed = 5;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		double lastTime = System.nanoTime();
		double currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta+=(currentTime - lastTime)/drawInterval;
			timer+=(currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if (timer>=1000000000) {
				System.out.println("FPS : " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {
		
		player.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		player.draw(g2);

		g2.dispose();
	}
}
