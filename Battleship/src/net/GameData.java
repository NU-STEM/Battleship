package net;

public class GameData implements java.io.Serializable {
	
	private int gamePort;
	
	GameData(int i) {
		
		gamePort = i;
		
	}
	
	int getGamePort() {
		
		return gamePort;
		
	}

}
