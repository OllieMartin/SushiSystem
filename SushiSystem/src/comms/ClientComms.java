package comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Client;

public class ClientComms extends Comms  {

	Client c;
	Thread listener;

	public ClientComms(Client c) {
		super();
		this.c = c;
	}

	public boolean establishConnection() {
		return establistConnection("localhost");
	}

	public boolean establistConnection(String serverAddress) {
		if (connected) return false;
		try {
			socket = new Socket(serverAddress,PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			connected = true;
			listener = new Thread(new Runnable() {

				@Override
				public void run() {
					Message m;
					while (connected) {

						m = receiveMessage();
						if (m != null) {
							switch (m.getType()) {
							case DISH_STOCK:
								DishStockMessage dsm = (DishStockMessage)m;
								c.updateDishStock(dsm.getDishes());
								break;
							case LOGIN:
								break;
							case LOGIN_SUCCESS:
								c.successfulLogin();
								break;
							case LOGIN_FAILURE:
								c.failedLogin();
								break;
							case LOGIN_REQUEST:
								break;
							case REGISTRATION:
								break;
							case REGISTRATION_FAILURE:
								c.invalidRegistration();
								break;
							case REGISTRATION_SUCCESS:
								c.successfulRegistration();
								break;
							case ORDER:
								break;
							case ORDER_STATUS:
								OrderStatusMessage osm = (OrderStatusMessage)m;
								c.updateOrderStatus(osm.getOrders());
								break;
							default:
								break;
							}
						} else {
							if (socket != null) { try { socket.close(); } catch (IOException e2) {/* Empty */} };
							connected = false;
							c.setConnected(false);
							return;
						}

					}

				}

			});
			listener.start();
			return true;

		} catch (IOException e) {
			if (socket != null) { try { socket.close(); } catch (IOException e2) {/* Empty */} };
			connected = false;
			c.setConnected(false);
			return false;
		}

	}

	public void terminateConnection() {
		if (socket != null) {
			try { socket.close(); } catch (IOException e) {/* Empty */}
		}
		connected = false;
		c.setConnected(false);
	}

}