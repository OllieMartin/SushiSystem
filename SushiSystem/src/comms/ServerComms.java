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
				System.err.println("Connection error occurred");
			} finally {
				try {
					listener.close();
				} catch (IOException e) {
					System.err.println("Error occurred closing listener");
				}
			}
		} catch (IOException e1) {
			System.err.println("Could not bind to port, close any applications already running");
		}

	}

}
