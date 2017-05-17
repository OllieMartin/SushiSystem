package comms;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;

import business.BusinessApplication;

public class ServerComms extends Comms implements Runnable {

	protected final int PORT = 25410; //Connection port to server
	protected HashSet<String> clientNames = new HashSet<String>(); //All of the connected user names (duplicates not allowed)
	protected HashSet<PrintWriter> clientWriters = new HashSet<PrintWriter>(); //All the writer objects to each client used to distribute messages
	private BusinessApplication ba;

	public ServerComms(BusinessApplication ba) {
		this.ba = ba;
	}

	@Override
	public boolean sendMessage(Message m) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		ServerSocket listener;
		try {
			listener = new ServerSocket(PORT);

			try {
				while (true) {
					new Handler(listener.accept(),this,ba.getAccountManager()).start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//Will also occur if an interrupt occurs to end execution
				try {
					listener.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //Prevent memory leaks!
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
