package comms;

public abstract class Comms {
	
	protected static final int PORT = 25410;
	
	public abstract boolean sendMessage(Message m);
	
}
