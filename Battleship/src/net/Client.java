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
	public static final int MAX_PACKET_SIZE = 65535;
	
	// These fields hard code in the network discovery ports
	public static final int BROADCAST_PORT = 1100, RESPONSE_PORT = 1000;
	
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
	
	public void connect() throws IOException {
		
		// Initialize UDP sockets
		// group = InetAddress.getByName("123.0.0.1"); Unnecessary? TODO
		dataSocket = new DatagramSocket(BROADCAST_PORT);
		
		// Wait for game info packet
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		dataSocket.receive(packet);
		
		// Get game port and address
		InetAddress clientAddress = packet.getAddress();
		int clientPort = packet.getData()[0];
		
		// Connect using TCP
		socket = new Socket(clientAddress, clientPort);
		
		// Get socket IO
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		
	}
	
	public void host(int gamePort) throws IOException {
		
		// Initialize UDP sockets
		group = InetAddress.getByName("123.0.0.1");
		multiSocket = new MulticastSocket(BROADCAST_PORT);
		multiSocket.joinGroup(group);
		dataSocket = new DatagramSocket(RESPONSE_PORT);
		
		// Send hosting packet
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		buffer = ByteBuffer.allocate(4).putInt(gamePort).array();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, RESPONSE_PORT);
		multiSocket.send(packet);
		
		// Wait for response packet
		buffer = new byte[MAX_PACKET_SIZE];
		packet = new DatagramPacket(buffer, buffer.length);
		dataSocket.receive(packet);
		
		// Get other client info
		//InetAddress clientAddress = packet.getAddress(); Unnecessary? TODO
		
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
