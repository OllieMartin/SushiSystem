package comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import business.AccountManager;
import business.MenuDish;
import business.Order;
import business.OrderManager;
import business.StockedDish;

/**
 * Handler creates threads for each of the client connections
 * @author Oliver Martin (ojm1g16)
 *
 */
public class Handler extends Comms implements Runnable {

	protected String name;
	protected ServerComms server;
	Message m;
	boolean login = false;

	public Handler(Socket socket, ServerComms server) {
		super();
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {

			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			while (!login) {

				sendMessage(new LoginRequestMessage());

				m = receiveMessage();
				if (m == null) {
					return; //Implies connected is terminated
				}

				System.out.println("Recevied Message");
				if (m.getType() == MessageType.LOGIN) {
					System.out.println("Login detected");
					LoginMessage lm = (LoginMessage)m;
					if (AccountManager.getInstance().loginUser(lm.getUser(), lm.getPassword())) {
						sendMessage(new LoginSuccessMessage());
						login = true;
						name = lm.getUser();
					} else {
						sendMessage(new LoginFailureMessage());
					}
				}
				if (m.getType() == MessageType.REGISTRATION) {
					RegistrationMessage rm = (RegistrationMessage)m;
					if (AccountManager.getInstance().registerUser(rm.getUser(), rm.getPassword(), rm.getAddress(), rm.getPostcode())) {
						sendMessage(new RegistrationSuccessMessage());
					} else {
						sendMessage(new RegistrationFailureMessage());
					}
				}
			}

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
							sendMessage(new DishStockMessage(dishes));

							List<Order> orders = OrderManager.getInstance().getUserOrders(name);
							if (orders != null) {
								for (Order o : orders) {
									System.out.println("Handler has: " + o.getId());
								}
								sendMessage(new OrderStatusMessage(orders));
							}
							Thread.sleep(5000);
						} catch (InterruptedException e) { /*EMPTY*/}
					}

				}

			}).start();

			while (login) {
				m = receiveMessage(); //Set name to the next line of the input stream
				if (m == null) {
					return; //If the connected is terminated and the input stream no longer gets a result then terminate this runnable
				}
				if (m.getType() == MessageType.ORDER) {
					OrderMessage o = (OrderMessage)m;
					OrderManager.getInstance().createOrder(name, o.getDishes());
				}
			}


		} catch (IOException e) {
			System.err.println("Connection lost");
		} finally {
			AccountManager.getInstance().logoutUser(name);
			connected = false;
			login = false;
			try {
				socket.close(); //Close the socket if possible
			} catch (IOException e) {
			}
		}
	}
}