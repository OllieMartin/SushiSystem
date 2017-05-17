package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {

	private List<Order> orders;
	private Map<String,List<Order>> userOrders;
	
	public OrderManager() {
		orders = new ArrayList<Order>();
		userOrders = new HashMap<String,List<Order>>();
	}
	
	public void createOrder(String user, List<OrderDish> dishes) {
		Order order = new Order(user, dishes);
		orders.add(order);
		if (userOrders.containsKey(user)) {
			getUserOrders(user).add(order);
		} else {
			List<Order> newEntry = new ArrayList<Order>();
			newEntry.add(order);
			userOrders.put(user, newEntry);
		}
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
	
	/**
	 * Can return null //TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @param user
	 * @return
	 */
	public List<Order> getUserOrders(String user) {
		if (userOrders.containsKey(user)) {
			return userOrders.get(user);
		} else {
			return null;
		}
	}
	
}
