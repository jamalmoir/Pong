import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameFrame extends JPanel {
	private int frameX;
	private int frameY;
	private int ballSize;
	private int ballX;
	private int ballY;
	private int ballVelocityX;
	private int ballVelocityY;
	private int paddleWidth;
	private int paddleHeight;
	private int paddleX;
	private int paddleY;
	private int score;

	boolean running;

	public GameFrame() {
		frameX = getPreferredSize().width;
		frameY = getPreferredSize().height;
		ballSize = 50;
		ballVelocityX = 1;
		ballVelocityY = 1;
		ballX = (frameX / 2) + (ballSize / 2);
		ballY = 0;
		paddleWidth = 150;
		paddleHeight = 25;
		paddleY = frameY - (paddleHeight + 5);
		running = false;
		score = 0;

		addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e) {
				if (e.getX() > frameX - paddleWidth) {
					paddleX = frameX - paddleWidth;
				} else {
					paddleX = e.getX();
				}
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
		});
	}

	public void gameLoop() {
		running = true;

		while (running) {
			update();
			render();

			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		int level = (score / 10) + 1;
		
		if (horizontalCollision()) {
			ballX += ballVelocityX;
		} else {
			ballVelocityX *= -1;
			ballX += ballVelocityX;
		}

		if (verticalCollision()) {
			//check if it hits paddle
			if (ballY + ballSize >= paddleY 
					&& ballX + ballSize > paddleX
					&& ballX < paddleX + paddleWidth) {
				ballVelocityY *= -1;
				score++;
			}

			ballY += ballVelocityY;
		} else {
			//Check if it is hitting bottom wall
			if (isLoss()) {
				running = false;
				
				int choice = JOptionPane.showOptionDialog(this, 
						"Game over! You Scored " + score + "!", 
						"Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				
				if (choice == 0) {
					score = 0;
					ballX = (frameX / 2) - (ballSize / 2);
					ballY = 0;
					ballVelocityX = 1;
					ballVelocityY = 1;
					running = true;
				} else {
					System.exit(0);
				}
			} else {
				ballVelocityY *= -1;
				ballY += ballVelocityY;
			}
		}
	}
	
	private boolean isLoss() {
		if (ballY + ballSize > paddleY) {
			return true;
		}
		
		return false;
	}
	
	private boolean horizontalCollision() {
		if ((ballX + ballVelocityX) + ballSize < frameX 
				&& ballX >= 0)  {
			return true;
		}
		
		return false;
	}
	
	private boolean verticalCollision() {
		if ((ballY + ballVelocityY) + ballSize < frameY 
				&& ballY >= 0) {
			return true;
		}
		
		return false;
	}

	private void render() {
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.fillOval(ballX, ballY, ballSize, ballSize);
		g2d.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
		g2d.drawString("Score: " + score, 10, 20);
	}
}
