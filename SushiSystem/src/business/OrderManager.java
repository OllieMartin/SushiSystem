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
		System.out.println("Creating new order!");
		Order order = new Order(user, dishes);
		orders.add(order);
		if (userOrders.containsKey(user)) {
			getUserOrders(user).add(order);
		} else {
			List<Order> newEntry = new ArrayList<Order>();
			newEntry.add(order);
			userOrders.put(user, newEntry);
		}
		for (OrderDish dish : dishes) {
			
			if (StockedDish.isStocked(dish.getDish().getName())) {
				System.out.println("!");
				StockedDish.getStockedDish(dish.getDish().getName()).increaseDemand(dish.getQuantity());
			}
			
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
			for (Order o : userOrders.get(user)) {
				System.out.println("I present : " + o.getId());
			}
			return userOrders.get(user);
		} else {
			return null;
		}
	}
	
}
