import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;
public class UDPClient2 implements Runnable{
    public static void main(String args[]){ 
	// args give message contents and server hostname
	DatagramSocket aSocket = null;
	int i = 0;
	int messagesize = 500;
	  try {
		String[] message = {"Some Message", "total","plus assignment", "pass", "test", "Larp", "run"};
		aSocket = new DatagramSocket();  
		aSocket.setSoTimeout(3000);
		int serverPort = 6789;	
		InetAddress aHost = InetAddress.getByName("127.0.0.1");
		ByteBuffer l = ByteBuffer.allocate(messagesize).putInt(message.length+1);
		l.put("presnap".getBytes());
		l.putChar(50, 'y');
		DatagramPacket request = new DatagramPacket(l.array(),  l.array().length, aHost, serverPort);
		aSocket.send(request);	
		byte[] buffer = new byte[1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
		aSocket.receive(reply);
		while (i < message.length)
		{
		l = ByteBuffer.allocate(messagesize/*4+message[i].getBytes().length*/).putInt(i+1);
		l.put(message[i].getBytes());
		l.putChar(50, 'n');
		request = new DatagramPacket(l.array(),  l.array().length, aHost, serverPort);
		aSocket.send(request);			                        
		reply = new DatagramPacket(buffer, buffer.length);	
		aSocket.receive(reply);
		//System.out.println("Reply: " + new String(reply.getData()));
		i++;
		}
		l = ByteBuffer.allocate(messagesize).putInt(i+1);

		l.put("presnap".getBytes());
		l.putChar(50, 'n');
		request = new DatagramPacket(l.array(),  l.array().length, aHost, serverPort);
		aSocket.send(request);	
	  }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
	  }catch (IOException e){System.out.println("IO: " + e.getMessage());}
	finally {if(aSocket != null) aSocket.close();}
   }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UDPClient2.main(null);
	} 
}