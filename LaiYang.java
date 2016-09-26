import java.util.ArrayList;
import java.util.LinkedList;
public class LaiYang {
	boolean recorded;
	ArrayList<String> counter;
	LinkedList State;
	public void ReadMessage(String s, boolean b)
	{
		if(b)
		{
			TakeSnapshot();
		}
		else if(recorded)
		{
			//State(channel...) <- add message s
			
			/*if(state[c].total + 1 = counter[]
			 * {
			 * 	state[].terminate
			 * 
			 * }
			 */
		}
	}
	// This should run on every tick
	public void Update()
	{
		/*If send message*/
			SendMessage();
			if(recorded)
			{
				//counter.add(channel, counter.get(channel) + 1);
			}
	}
	public void SendMessage()
	{
		
	}
	public void TakeSnapshot(/*Message*/)
	{
		if(!recorded)
		{
			recorded = true;
			SendMessage();
			//record a snapshot of the state here
		}
	}
}
