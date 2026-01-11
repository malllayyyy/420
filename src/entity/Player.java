package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	public BufferedImage[] walkDown; // Array to hold the 4 frames
	public int spriteCounter = 0;
	public int spriteNum = 0;
	boolean isMoving = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {

		try {
	        // Load the full spritesheet (the strip of 4 frames)
	        BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/down.png"));

	        walkDown = new BufferedImage[4];
	        
	        // 2. Loop to "cut" the 4 frames (64x64 each)
	        for (int i = 0; i < 4; i++) {
	            walkDown[i] = spriteSheet.getSubimage(i * 64, 0, 64, 64);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void update() {

		// Only animate if a key is being pressed
	    if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
	    	
	        isMoving = true;
	    	
	        if (keyH.upPressed) { direction = "down"; y -= speed; }
	        if (keyH.downPressed) { direction = "down"; y += speed; }
	        if (keyH.leftPressed) { direction = "down"; x -= speed; }
	        if (keyH.rightPressed) { direction = "down"; x += speed; }

	        // 3. Animation Timer
	        spriteCounter++;
	        if (spriteCounter > 10) { // Change frame every 10 ticks
	            spriteNum = (spriteNum + 1) % 4; // Cycle 0, 1, 2, 3
	            spriteCounter = 0;
	        }
	    }else {
	    	isMoving = false;
	    }
	}

	public void draw(Graphics2D g2) {
	    BufferedImage image = null;
	    BufferedImage ideal = walkDown[0];
	    
	    if(isMoving == true) {
	    	// Use the array and the spriteNum to pick the current frame
		    if (direction.equals("down")) {
		        image = walkDown[spriteNum];
		    }
		    // (Add other directions here later)

		    if (image != null) {
		        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
		    }
	    }else {
	    	g2.drawImage(ideal, x, y, gp.tileSize, gp.tileSize, null);
	    }
	    
	}
}
