import javax.swing.JFrame;


public class Gui {

	public void createAndShowGui() {
		JFrame frame = new JFrame("Pong");
		GameFrame game = new GameFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		
		frame.getContentPane().add(game);
		frame.pack();
		frame.setVisible(true);
		
		game.gameLoop();
	}
}
