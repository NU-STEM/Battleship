package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
	
	public void activate (){
		hostGameButton.setSize(100, 50);
		hostGameButton.setText("Host Game");
		listenForGameButton.setSize(100, 50); 
		listenForGameButton.setText("Search for game");
		this.add(hostGameButton);
		this.add(listenForGameButton);
		
		
	}
	JButton hostGameButton = new JButton();
	JButton listenForGameButton = new JButton();
	
	public void paint(Graphics g){
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(0,.5f,.5f));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		super.paintChildren(g);
		
	}
	
}
