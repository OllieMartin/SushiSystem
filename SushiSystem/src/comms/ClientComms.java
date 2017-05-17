package comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Client;

public class ClientComms extends Comms  {

	protected ObjectOutputStream oout;
	protected ObjectInputStream oin;
	Client c;
	private boolean connected;
	private static final int PORT = 25410;
	Socket socket;
	Thread listener;


	@Override
	public boolean sendMessage(Message m) {
		try {
			oout.writeObject(m);
			System.out.println("message sent");
			return true;
		} catch (IOException e) {
			System.out.println("FAIL");
			return false;
		}
	}

	public ClientComms(Client c) {
		this.c = c;
		connected = false;
	}

	public boolean establishConnection() {
		return establistConnection("localhost");
	}

	public boolean establistConnection(String serverAddress) {
		if (connected) return false;
		try {
			socket = new Socket(serverAddress,PORT); //TODO NEEDS TO BE CLOSED SOMEWHERE
			oout = new ObjectOutputStream(socket.getOutputStream());
			oin = new ObjectInputStream(socket.getInputStream());

			connected = true;
			listener = new Thread(new Runnable() {

				@Override
				public void run() {
					Message m;
					while (connected) {

						try {
							m = (Message)oin.readObject();
						} catch (IOException e) {
							connected = false;
							return;
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}

						if (m.getType() == MessageType.LOGIN_SUCCESS) {
							System.out.println("SUCCESSFUL LOGIN");
						}

						/*if (inputLine.startsWith("LOGINSUCCESSFUL")) {
							System.out.println("Login success!");
						} else if (inputLine.startsWith("LOGINFAILURE")) {

						} else if (inputLine.startsWith("LOGOUTSUCCESSFUL")) {

						} else if (inputLine.startsWith("ORDERSUCCESSFUL")) {

						}*/
					}

				}

			});
			listener.start();
			return true;

		} catch (IOException e) {
			if (socket != null) { try { socket.close(); } catch (IOException e2) {/* Empty */} };
			connected = false;
			return false;
		}

	}

	public void terminateConnection() {
		if (socket != null) {
			try { socket.close(); } catch (IOException e) {/* Empty */}
		}
		connected = false;
	}

}