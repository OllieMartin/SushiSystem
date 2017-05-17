package comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import business.AccountManager;

/**
 * A handler class which will create threads for each client connection
 * @author Oliver
 *
 */
public class Handler extends Thread {

	protected String name;
	protected Socket socket;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected ServerComms server;
	private AccountManager am;
	Message m;

	public Handler(Socket socket, ServerComms server, AccountManager am) {
		this.socket = socket;
		this.server = server;
		this.am = am;
	}
	//Set the socket, and the connected server class

	public void run() {
		try {

			// Create character streams for the socket.

			//Input stream
			in = new ObjectInputStream(
					socket.getInputStream());
			//Output PrintWriter
			out = new ObjectOutputStream(socket.getOutputStream());

			// Keep requesting the connection to enter a name
			// NB: We must lock the set of names while doing this!

			boolean login = false;
			while (!login) {
				out.writeObject(new LoginRequestMessage());

				m = (Message)in.readObject(); //Set name to the next line of the input stream
				if (m == null) {
					return; //If the connected is terminated and the input stream no longer gets a result then terminate this runnable
				}

				//Lock the set of client names
				/* synchronized (server.clientNames) {
                        if (!server.clientNames.contains(name)) {
                        	//If it is a unique name then add it to the list and exit this loop
                            server.clientNames.add(name);
                            nameAdded = true;
                        }
                    } */
				System.out.println("Recevied Message");
				if (m.getType() == MessageType.LOGIN) {
					System.out.println("Login detected");
					LoginMessage lm = (LoginMessage)m;
					if (lm.getUser().equals("Oliver")) {
						if (lm.checkPassword("Revilo")) {
							out.writeObject(new LoginSuccessMessage());
							login = true;
							System.out.println("success login");
						}
					}
				}
				if (m.getType() == MessageType.REGISTRATION) {
					System.out.println("Registration detected");
					RegistrationMessage rm = (RegistrationMessage)m;
					if (am.registerUser(rm.getUser(), rm.getPassword(), rm.getAddress(), rm.getPostcode())) {
						out.writeObject(new RegistrationSuccessMessage());
						System.out.println("Successful Registration");
					} else {
						out.writeObject(new RegistrationFailureMessage());
					}
				}
			}

			// The user is now accepted by our system
			// Add a new printWriter for this client to the hashset so we can send messages


		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// This client is going down!  Remove its name and its print
			// writer from the sets, and close its socket.
			if (name != null) {
				for (PrintWriter writer : server.clientWriters) {
					writer.println("INFO " + name + " has left the chat!");
				}
				System.out.println("INFO: " + name + " has left the chat!");
				server.clientNames.remove(name); //If they had entered a name then remove it from the list
			}
			if (out != null) {
				server.clientWriters.remove(out); //If the PrintWriter was created before disconnection then remove it from the list
			}
			try {
				socket.close(); //Close the socket if possible
			} catch (IOException e) {
			}
		}
	}      
}