
package client;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import business.MenuDish;
import business.OrderDish;
import comms.ClientComms;
import comms.LoginMessage;
import comms.OrderMessage;
import comms.OrderStatusMessageOrder;
import comms.RegistrationMessage;

public class Client extends JFrame{

	private static final long serialVersionUID = 1L;

	private ClientComms comms;
	private ClientPanel panel;
	private DishClientTableModel tableModel; //TODO maybe pass ref to this instead of whole client?
	public DishClientTableModel getTableModel() {
		return this.tableModel;
	}
	private OrderStatusClientTableModel orderModel; //TODO maybe pass ref to this instead of whole client?
	public OrderStatusClientTableModel getOrderModel() {
		return this.orderModel;
	}

	private List<MenuDish> dishes;
	boolean connected;

	public void setConnected(boolean status) {
		connected = status;
	}
	public boolean isConnected() {
		return connected;
	}

	public Client() {

		super("Sushi System Client");

		new LoginFrame(this);

		comms = new ClientComms(this);

		if (!comms.establishConnection()) {
			System.out.println("ERROR CONNECTING!");
			connected = false;
		} else {
			connected = true;
		}

		tableModel = new DishClientTableModel();
		orderModel = new OrderStatusClientTableModel();
		new Thread(new Runnable() {
			//TODO v ugly
			@Override
			public void run() {

				while (connected) {
					if (tableModel.hasUpdate()) {
						tableModel.fireTableDataChanged();
						tableModel.setUpdated();
					}
					if (orderModel.hasUpdate()) {
						orderModel.fireTableDataChanged();
						orderModel.setUpdated();
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
		panel = new ClientPanel(this);
		this.setContentPane(panel);
		this.setMinimumSize(new Dimension(600,400));
		this.setPreferredSize(new Dimension(600,400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void attemptLogin(String user, char[] password) {

		if (connected) {
			comms.sendMessage(new LoginMessage(user,new String(password)));
		} else {
			if (!comms.establishConnection()) {
				connected = false;
				JOptionPane.showMessageDialog(null, "Could not connected to server", "Information", JOptionPane.INFORMATION_MESSAGE);
			} else {
				connected = true;
				new Thread(new Runnable() {

					@Override
					public void run() {

						while (connected) {
							if (tableModel.hasUpdate()) {
								tableModel.fireTableDataChanged();
								tableModel.setUpdated();
							}
							if (orderModel.hasUpdate()) {
								orderModel.fireTableDataChanged();
								orderModel.setUpdated();
							}
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
				}).start();
				comms.sendMessage(new LoginMessage(user,new String(password)));
			}
		}

	}

	public void attemptRegister(String user, char[] password, String address, String postcode) {

		if (connected) {
			comms.sendMessage(new RegistrationMessage(user,new String(password),address,postcode));
		} else {
			if (!comms.establishConnection()) {
				connected = false;
				JOptionPane.showMessageDialog(null, "Could not connected to server", "Information", JOptionPane.INFORMATION_MESSAGE);
			} else {
				connected = true;
				new Thread(new Runnable() {

					@Override
					public void run() {

						while (connected) {
							if (tableModel.hasUpdate()) {
								tableModel.fireTableDataChanged();
								tableModel.setUpdated();
							}
							if (orderModel.hasUpdate()) {
								orderModel.fireTableDataChanged();
								orderModel.setUpdated();
							}
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
				}).start();
				comms.sendMessage(new RegistrationMessage(user,new String(password),address,postcode));
			}

		}
	}

	public void requestRegister() {

		new RegistrationFrame(this);

	}

	public void successfulLogin() {
		this.setVisible(true);
	}

	public void successfulRegistration() {
		new LoginFrame(this);
	}

	public void failedLogin() {
		new InvalidLoginFrame(this);
	}

	public void invalidRegistration() {
		new InvalidRegistrationFrame(this);
	}

	public void updateDishStock(List<MenuDish> dishes) {

		System.out.println("UPDATING DISH STOCKS!");
		tableModel.clear();
		this.dishes = dishes;
		for (MenuDish dish : dishes) {
			tableModel.dishAdded(dish);
		}

	}

	public void updateOrderStatus(List<OrderStatusMessageOrder> orders) {


		System.out.println("UPDATING ORDER STATUS!");
		orderModel.clear();
		for (OrderStatusMessageOrder order : orders) {
			System.out.println("Here is an order: " + order.getId());
			orderModel.orderAdded(order);
		}

	}

	public void placeOrder(List<OrderDish> order) {
		for (OrderDish d : order) {
			System.out.println(d.getDish().getName());
		}
		comms.sendMessage(new OrderMessage(order));
	}

	public List<MenuDish> getDishes() {
		return this.dishes;
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater( new Runnable() { 
			@Override
			public void run() {  
				new Client();
			}
		});
	}

}

