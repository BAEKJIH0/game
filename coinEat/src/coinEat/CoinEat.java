package coinEat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CoinEat extends JFrame {
	Image bufferImage;
	Graphics screenGraphic;
	
	private Image background = new ImageIcon("src/mainScreen.jfif").getImage().getScaledInstance(500, 500, DO_NOTHING_ON_CLOSE);
	private Image player = new ImageIcon("src/player.png").getImage();
	private Image coin = new ImageIcon("src/coin.png").getImage();
	
	int playerX, playerY;
	int playerWidth = player.getWidth(null);
	int playerHeight = player.getHeight(null);
	int coinX, coinY;
	int coinWidth = coin.getWidth(null);
	int coinHeight = coin.getHeight(null);
	
	int score;
	
	boolean up, down, left, right;
	
	public CoinEat() {
		setTitle("Coin Eat Game");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addKeyListener(new KeyAdapter() { 
			public void keyPressed(KeyEvent e) { 
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP: 
					up = true;
					break;
				case KeyEvent.VK_DOWN:
					down = true;
					break;
				case KeyEvent.VK_LEFT:
					left = true;
					break;
				case KeyEvent.VK_RIGHT:
					right = true;
					break;
				}
			}
			
			public void keyReleased(KeyEvent e) { 
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = false;
					break;
				case KeyEvent.VK_DOWN:
					down = false;
					break;
				case KeyEvent.VK_LEFT:
					left = false;
					break;
				case KeyEvent.VK_RIGHT:
					right = false;
					break;
				}
			}
		});	
		init();
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			keyProcess();
			crashCheck();
		}
	}
	
	public void init() { 
		score = 0;
		
		playerX = (500 - playerWidth)/2; 
		playerY = (500 - playerHeight)/2;
		
		coinX = (int)(Math.random()*(501-playerWidth)); 
		coinY = (int)(Math.random()*(501-playerHeight-30))+30;	
		
	}
	
	public void keyProcess() {   
		if (up && playerY - 3 > 30) playerY-=3;
		if (down && playerY + playerHeight + 3 < 500) playerY+=3;
		if (left && playerX - 3 > 0) playerX-=3;
		if (right && playerX + playerWidth + 3 < 500) playerX+=3;
	}	
	
	public void crashCheck() {
		if (playerX + playerWidth > coinX && coinX + coinWidth > playerX && playerY + playerHeight > coinY && coinY + coinHeight > playerY) {
			score+=100; 
			coinX = (int)(Math.random()*(501-playerWidth));
			coinY = (int)(Math.random()*(501-playerHeight-30))+30;
		}
	}	
	
	public void paint(Graphics g) { 
		
	    bufferImage = createImage(500, 500);
		screenGraphic = bufferImage.getGraphics(); 
		screenDraw(screenGraphic); 
		g.drawImage(bufferImage, 0, 0, null); 
	} 
	
	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(coin, coinX, coinY, null);
		g.drawImage(player, playerX, playerY, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString("SCORE : " + score, 30, 80);
		this.repaint();
	}

}