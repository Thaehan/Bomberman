package factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameJFrame extends JFrame {
	public JPanel gamePanel;
	private Game game;

	public GameJFrame() {
		setTitle("Bomberman");
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.setPreferredSize(new Dimension(Game.WIDTH * 3, Game.HEIGHT * 3));

		game = new Game(this);
		gamePanel.add(game);
		game.setVisible(true);
		gamePanel.setVisible(true);
		gamePanel.setFocusable(true);
		add(gamePanel, BorderLayout.PAGE_END);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		Sound.music();
		game.start();
	}

}
