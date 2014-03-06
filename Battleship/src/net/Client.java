/*	Notes
 *  Grid size: 25x25
 * 
*/
package net;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

// This class is instantiated once for each game client

public class Client<T> {
	
	// Packet info
	public static final int MAX_PACKET_SIZE = 1024;
	
	// These fields hard code in the network discovery ports
	public static final int BROADCAST_PORT = 11100, RESPONSE_PORT = 11000;
	
	// These fields hold the UDP socket references
	MulticastSocket multiSocket;
	DatagramSocket dataSocket;
	InetAddress group;
	
	// These fields hold the TCP socket references
	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	// Any other fields
	int gamePort;
	
	public void connect() throws IOException, ClassNotFoundException {
		
		// Initialize UDP sockets
		// group = InetAddress.getByName("224.0.0.0"); Unnecessary? TODO
		group = InetAddress.getByName("224.0.0.0");
		dataSocket = new DatagramSocket(RESPONSE_PORT);
		
		// Sends packet
		byte[] buffer = new byte[0];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, BROADCAST_PORT);
		dataSocket.send(packet);
		
		// Get game info packet
		buffer = new byte[MAX_PACKET_SIZE];
		packet = new DatagramPacket(buffer, buffer.length);
		dataSocket.receive(packet);
		
		// Get game data
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
		in = new ObjectInputStream(bis);
		GameData gameData = (GameData) in.readObject();
		
		// Get game port and address
		InetAddress clientAddress = packet.getAddress();
		int clientPort = gameData.getGamePort();
		
		// Connect using TCP
		socket = new Socket(clientAddress, clientPort);
		
		// Get socket IO
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		
	}
	
	public void host(int gamePort) throws IOException {
		
		// Initialize UDP sockets
		dataSocket = new DatagramSocket();
		group = InetAddress.getByName("224.0.0.0");
		multiSocket = new MulticastSocket(BROADCAST_PORT);
		multiSocket.joinGroup(group);
		
		// Wait for connection
		byte[] buffer = new byte[0];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		multiSocket.receive(packet);
		
		// Get other client info
		InetAddress clientAddress = packet.getAddress();
		
		// Send hosting packet
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.flush();
		out = new ObjectOutputStream(bos);
		out.writeObject(new GameData(gamePort));
		buffer = bos.toByteArray();
		packet = new DatagramPacket(buffer, buffer.length, clientAddress, RESPONSE_PORT);
		dataSocket.send(packet);
		
		// Wait for TCP connection
		serverSocket = new ServerSocket(gamePort);
		serverSocket.setSoTimeout(0);
		Socket socket = serverSocket.accept();
		
		// Get socket IO
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		
	}
	
	public T receive() throws ReflectiveOperationException, IOException {
		
		return (T) in.readObject();
		
	}
	
	public void send(T packet) throws IOException {
		
		out.writeObject((Object) packet);
		out.flush();
		
	}

}
