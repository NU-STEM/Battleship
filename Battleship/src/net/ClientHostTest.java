package net;

import java.io.IOException;

public class ClientHostTest {
	
	public static void main(String[] args) throws IOException {
		
		Client<TestObject> client = new Client<TestObject>();
		client.host(2222);
		for(int i = 0; i < 10; i++) {
			
			client.send(new TestObject(i));
			
		}
		
		// this is needed so that this side of the connection doesn't reset
		// before the packet can be received on the other side
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
