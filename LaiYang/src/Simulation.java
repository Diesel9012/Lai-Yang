
public class Simulation {
	public static void main(String args[])
	{
		UDPServer2 server = new UDPServer2();
		UDPClient2 client = new UDPClient2();
		UDPClient2 client2 = new UDPClient2();
		UDPClient2 client10 = new UDPClient2();
		UDPClient2 client3 = new UDPClient2();
		UDPClient2 client4 = new UDPClient2();
		UDPClient2 client5 = new UDPClient2();
		UDPClient2 client6 = new UDPClient2();
		UDPClient2 client7 = new UDPClient2();
		UDPClient2 client8 = new UDPClient2();
		UDPClient2 client9 = new UDPClient2();
		Thread t2 = new Thread(client);
		Thread t3 = new Thread(client2);
		Thread t4 = new Thread(client3);
		Thread t5 = new Thread(client4);
		Thread t6 = new Thread(client5);
		Thread t7 = new Thread(client6);
		Thread t8 = new Thread(client7);
		Thread t9 = new Thread(client8);
		Thread t10 = new Thread(client9);
		Thread t11 = new Thread(client10);
		Thread t1 = new Thread(server);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();
		t11.start();
	}
}
