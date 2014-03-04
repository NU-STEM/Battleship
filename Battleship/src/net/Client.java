package net;

import java.io.IOException;
import java.net.*;

// This class is instantiated once for each game client

public class Client {
	
	MulticastSocket multiSocket;
	DatagramSocket dataSocket;
	InetAddress group;
	
	public Client() throws IOException {
		
		group = InetAddress.getByName("123.0.0.1");
		multiSocket = new MulticastSocket();
		multiSocket.joinGroup(group);
		
	}

}
