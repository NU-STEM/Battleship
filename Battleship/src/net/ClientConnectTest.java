package net;

import java.io.IOException;

public class ClientConnectTest {
	
	public static void main(String[] args) throws IOException, ReflectiveOperationException {
	
		Client<TestObject> client = new Client<TestObject>();
		client.connect();
		while(true) {
			
			System.out.println(client.receive().i);
			
		}
		
	}

}
