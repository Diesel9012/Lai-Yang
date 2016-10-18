import java.net.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.io.*;
public class UDPServer2 implements Runnable{
	public static void main(String args[]){ 
	boolean recorded = false;
	boolean serverSnap = false;
	DatagramSocket aSocket = null;
	ArrayList<String> client = new ArrayList<String>();
	ArrayList<Integer> total = new ArrayList<Integer>();
	ArrayList<Integer> fin = new ArrayList();
	List<String> State = new ArrayList<String>();
	    try{
	    	aSocket = new DatagramSocket(6789);
		byte[] buffer = new byte[1000];
 		while(true){
 		    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  		   aSocket.receive(request);
  		   int num = ByteBuffer.wrap(request.getData()).asIntBuffer().get();
  		   boolean b = (ByteBuffer.wrap(request.getData()).getChar(50) == 'y');
  		   if(client.contains(request.getSocketAddress().toString()))
  		   {
  			   if(!(num - 1 == total.get((client.indexOf(request.getSocketAddress().toString())))))
  			   {
  				   //System.out.println("A packet was lost");
  			   }
  			   int t = client.indexOf(request.getSocketAddress().toString());
  			   
  			   if(!recorded)
  			   {
  				   total.set(t, num);
  			   }
  		   }
  		   else
  		   {
  			   client.add(request.getSocketAddress().toString());
  			   total.add(0);
  			   fin.add(num);
  			   State.add("Start Connection: ");
  		   }
  		   
  		 System.out.print(num + new String(request.getData()) + "   ");
  		int t = client.indexOf(request.getSocketAddress().toString());
  		if(serverSnap)
  		{
  			for(int i = 0; i < client.size(); i ++)
  			{
  				ByteBuffer l = ByteBuffer.allocate(4+"presnap".getBytes().length).putInt(total.get(t)+1);
  	  			l.put("presnap".getBytes());
  	  			DatagramPacket reply = new DatagramPacket(l.array(), l.array().length, InetAddress.getByName(client.get(t).toString().substring(1,10)), Integer.parseInt(client.get(t).toString().substring(11,16)));
  	  			aSocket.send(reply);
  			}
  			recorded = true;
  			takeSnapshot(client, total, State);
  		}
  		if(b)
  		{
  			for(int i = 0; i < client.size(); i ++)
  			{
  				ByteBuffer l = ByteBuffer.allocate(4+"presnap".getBytes().length).putInt(total.get(t)+1);
  	  			l.put("presnap".getBytes());
  	  			DatagramPacket reply = new DatagramPacket(l.array(), l.array().length, InetAddress.getByName(client.get(t).toString().substring(1,10)), Integer.parseInt(client.get(t).toString().substring(11,16)));
  	  			aSocket.send(reply);
  			}
  			recorded = true;
  			takeSnapshot(client, total, State);
  		}
  		else if (recorded)
  		{
  			State.set(t, State.get(t) + new String(request.getData()) + " / ");
  			total.set(t, num);
  			System.out.println("test");
  			if(client.isEmpty())
  			{
  				recorded = false;
  				//break;
  			}
  		}
  		//If p recieves presnap
  		 if(new String(request.getData()).contains("presnap") && total.get(t) == fin.get(t))
  		 {
  			recorded = true;
  			takeSnapshot(client, total, State);
  			for(int i = 0; i < client.size(); i ++)
  			{
  				ByteBuffer l = ByteBuffer.allocate(4+"presnap".getBytes().length).putInt(total.get(t)+1);
  	  			l.put("presnap".getBytes());
  	  			DatagramPacket reply = new DatagramPacket(l.array(), l.array().length, InetAddress.getByName(client.get(t).toString().substring(1,10)), Integer.parseInt(client.get(t).toString().substring(11,16)));
  	  			aSocket.send(reply);
  			}
  			int index = client.indexOf(request.getSocketAddress().toString());
  			client.remove(index);
  			total.remove(index);
  			State.remove(index);
  			fin.remove(index);
  			if(client.isEmpty())
  			{
  				recorded = false;
  				//break;
  			}
  			System.out.println("a connection has been terminated");
  		 }
		   DatagramPacket reply = new DatagramPacket(request.getData(), 
				   request.getLength(), request.getAddress(), request.getPort());
		   aSocket.send(reply);
		}
	    }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
	   }catch (IOException e) {System.out.println("IO: " + e.getMessage());}
	finally {if(aSocket != null) aSocket.close();}
    }
	
	@Override
	public void run() {
		UDPServer2.main(null);
	}
	public static void takeSnapshot(ArrayList client, ArrayList num, List messages) throws FileNotFoundException, UnsupportedEncodingException
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("M-E-d-HH:mm:ss:SSS");
        System.out.println( sdf.format(cal.getTime()) );
		PrintWriter writer = new PrintWriter(sdf.format(cal.getTime()) + ".dat", "UTF-8");
		for (int i = 0; i < client.size(); i++)
		{
			writer.println(client.get(i) + " message number:" + num.get(i) + " messages: " + messages.get(i));
		}
		writer.close();
	}
}