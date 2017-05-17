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
