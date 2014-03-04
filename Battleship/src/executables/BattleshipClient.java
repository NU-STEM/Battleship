package executables;

import gamestates.GameState;
import gamestates.MainMenu;

import javax.swing.JFrame;

public class BattleshipClient {

	public static void main(String[] args) {
		JFrame BattleShipWindow = new JFrame();
		BattleShipWindow.setSize(600, 600);
		BattleShipWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BattleShipWindow.setVisible(true);
		BattleShipWindow.setTitle("Battleship!");
		BattleShipWindow.setResizable(false);
		GameState mainGame = new GameState();
		MainMenu mainGameMenu = new MainMenu();
		BattleShipWindow.add(mainGameMenu);
		mainGameMenu.activate();
		BattleShipWindow.repaint();
		mainGameMenu.repaint();
	}

}
