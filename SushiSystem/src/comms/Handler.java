package comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import business.AccountManager;
import business.MenuDish;
import business.Order;
import business.OrderDish;
import business.OrderManager;
import business.StockedDish;

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
	private OrderManager om;
	private boolean connected;
	Message m;
	boolean login = false;

	public Handler(Socket socket, ServerComms server, AccountManager am, OrderManager om) {
		this.socket = socket;
		this.server = server;
		this.am = am;
		this.om = om;
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
					if (am.loginUser(lm.getUser(), lm.getPassword())) {
						out.writeObject(new LoginSuccessMessage());
						login = true;
						name = lm.getUser();
					} else {
						out.writeObject(new LoginFailureMessage());
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

			System.out.println("LOGGED IN MODE");
			connected = true;
			new Thread(new Runnable() {

				@Override
				public void run() {

					while (connected) {
						try {
							List<MenuDish> dishes = new ArrayList<MenuDish>();
							for (StockedDish dish : StockedDish.getStockedDishes()) {
								dishes.add(new MenuDish(dish));
							}
							out.writeObject(new DishStockMessage(dishes));
							
							List<Order> orders = om.getUserOrders(name);
							if (orders != null) {
								for (Order o : orders) {
									System.out.println("Handler has: " + o.getId());
								}
								out.writeObject(new OrderStatusMessage(orders));
							}
							Thread.sleep(5000);
						} catch (IOException e) {
							connected = false;
						} catch (InterruptedException e) { /*EMPTY*/}
					}

				}

			}).start();

			while (login) {
				m = (Message)in.readObject(); //Set name to the next line of the input stream
				if (m == null) {
					return; //If the connected is terminated and the input stream no longer gets a result then terminate this runnable
				}
				if (m.getType() == MessageType.ORDER) {
					System.out.println("Order detected");
					OrderMessage o = (OrderMessage)m;
					om.createOrder(name, o.getDishes());
					for (OrderDish d : o.getDishes()) {
						System.out.println(d.getDish().getName());
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
			am.logoutUser(name);
			connected = false;
			login = false;
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