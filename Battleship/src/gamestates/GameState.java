package gamestates;

import javax.swing.JPanel;

public class GameState extends JPanel {
	
	public void enterGameLoop(){
		int LogicHertz = 30;
		int RenderHertz = 60;
		int Time_Between_Logic = 1000000000/LogicHertz;
		int Time_Between_Render = 1000000000/RenderHertz;
		long lastRenderTime = System.nanoTime();
		long lastLogicTime = System.nanoTime();
		
		
		boolean running = true;
		while(running){
			logicUpdate();
			lastLogicTime = System.nanoTime();
			while(System.nanoTime()-lastLogicTime > Time_Between_Logic){
				double interpolation = (System.nanoTime() -lastLogicTime)/ Time_Between_Logic;
				renderUpdate(interpolation);
				
				lastRenderTime = System.nanoTime();
				while(System.nanoTime()-lastRenderTime > Time_Between_Render){
					Thread.yield();
				}
			}
		}
	}
	public void logicUpdate(){
		
	}
	public void renderUpdate(double Interpolation){
		
	}
}
