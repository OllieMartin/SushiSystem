package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class OrderManager {

	private List<Order> orders;
	private Map<String,List<Order>> userOrders;

	private static OrderManager instance;

	public static OrderManager getInstance() {
		return instance;
	}

	static {
		instance = new OrderManager();
	}

	private OrderManager() {
		orders = new ArrayList<Order>();
		userOrders = new HashMap<String,List<Order>>();
	}

	public void createOrder(String user, List<OrderDish> dishes) {
		Order order = new Order(user, dishes);
		orders.add(order);
		synchronized (userOrders) {
			if (userOrders.containsKey(user)) {
				getUserOrders(user).add(order);
			} else {
				List<Order> newEntry = new ArrayList<Order>();
				newEntry.add(order);
				userOrders.put(user, newEntry);
			}
		}
		for (OrderDish dish : dishes) {

			if (StockedDish.isStocked(dish.getDish().getName())) {
				StockedDish.getStockedDish(dish.getDish().getName()).increaseDemand(dish.getQuantity());
			}

		}
		DroneManager.getInstance().addTask(order);
	}

	public List<Order> getWaitingOrders() {
		List<Order> waiting = new ArrayList<Order>();
		for (Order o : orders) {
			if (o.getStatus() == OrderStatus.PLACED) {
				waiting.add(o);
			}
		}
		return waiting;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void loadOrders(List<Order> orders) {
		synchronized (this.orders) {
			this.orders = orders;
			for (Order o : this.orders) {
				if (o.getStatus() == OrderStatus.TRANSIT) {
					o.setStatus(OrderStatus.PLACED);
				}
				if (BusinessApplicationPane.getOrderTableModel() != null) {
					o.addListener(BusinessApplicationPane.getOrderTableModel() );//TODO Move static reference location
				}
				o.newAdded();
				synchronized (userOrders) {
					if (userOrders.containsKey(o.getUser())) {
						getUserOrders(o.getUser()).add(o);
					} else {
						List<Order> newEntry = new ArrayList<Order>();
						newEntry.add(o);
						userOrders.put(o.getUser(), newEntry);
					}
				}
				if (o.getStatus() == OrderStatus.PLACED) {
					DroneManager.getInstance().addTask(o);
				}
			}
		}
	}

	public List<Order> getUserOrders(String user) throws NoSuchElementException {
		synchronized (userOrders) {
			if (userOrders.containsKey(user)) {
				return userOrders.get(user);
			} else {
				return null;
			}
		}
	}



}
