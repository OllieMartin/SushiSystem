package comms;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerComms implements Runnable {

	public ServerComms() {
	}

	@Override
	public void run() {
		ServerSocket listener;
		try {
			listener = new ServerSocket(Comms.getPort());

			try {
				while (true) {
					new Thread(new Handler(listener.accept(),this)).start();
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
