package comms;

import java.util.ArrayList;
import java.util.List;

import business.Order;

public class OrderStatusMessage extends Message {

	private static final long serialVersionUID = 1L;
	
	List<OrderStatusMessageOrder> orders;

	public OrderStatusMessage(List<Order> orders) {
		super(MessageType.ORDER_STATUS);
		this.orders = new ArrayList<OrderStatusMessageOrder>();
		for (Order o : orders) {
			this.orders.add(new OrderStatusMessageOrder(o));
			System.out.println("The message has: " + o.getId());
		}
	}
	
	public List<OrderStatusMessageOrder> getOrders() {
		return this.orders;
	}

}
