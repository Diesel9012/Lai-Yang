import java.net.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
		byte[] buffer = new byte[1500];
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
		i++;
		}
		l = ByteBuffer.allocate(messagesize).putInt(i+1);

		l.put("presnap".getBytes());
		l.putChar(50, 'n');
		request = new DatagramPacket(l.array(),  l.array().length, aHost, serverPort);
		aSocket.send(request);
		String fname = takeSnapshot(message);
		FileInputStream in = new FileInputStream(fname);
		for (i=0;i<1500;i++)
		{
			buffer[i]=(byte)in.read();
		}
		request = new DatagramPacket(buffer,  buffer.length, aHost, serverPort);
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
	public static String takeSnapshot(String[] messages) throws FileNotFoundException, UnsupportedEncodingException
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("M-E-d-HH:mm:ss:SSS");
		PrintWriter writer = new PrintWriter(sdf.format(cal.getTime()) + ".dat", "UTF-8");
		for (int i = 0; i < messages.length; i++)
		{
			writer.println(messages[i] + " message number:" + i);
		}
		writer.close();
		return sdf.format(cal.getTime()) + ".dat";
	}
}