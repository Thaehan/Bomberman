package factory;

import javax.swing.*;
import java.awt.*;

public class GameJFrame extends JFrame {
	public GamePanel gamePanel;
	private JPanel mainPanel;
	
	private Game game;

	public GameJFrame () {
		setTitle("Bomberman");
		mainPanel = new JPanel(new BorderLayout());
		gamePanel = new GamePanel(this);
		mainPanel.add(gamePanel , BorderLayout.PAGE_END);
		
		game = gamePanel.getGame();
		
		add(mainPanel);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		game.start();
	}

	public static void replay() {
		JButton replay = new JButton("Try again");
		replay.setPreferredSize(new Dimension(Game.DEFAULT_SIZE * 2, Game.DEFAULT_SIZE));
	}


	public void newGame() {
		game.getBoard().newGame();
	}
}
