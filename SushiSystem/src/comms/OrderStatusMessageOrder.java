package comms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.Order;
import business.OrderDish;
import business.OrderStatus;

public class OrderStatusMessageOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private float price;
	private OrderStatus status;
	private List<OrderDish> dishes;

	public OrderStatusMessageOrder (Order o) {
		
		this.id = o.getId();
		this.price = o.getPrice();
		this.status = o.getStatus();
		this.dishes = new ArrayList<OrderDish>(o.getDishes());
		
	}
	
	public int getId() {
		return this.id;
	}
	public float getPrice() {
		return this.price;
	}
	public OrderStatus getStatus() {
		return this.status;
	}
	public List<OrderDish> getDishes() {
		return this.dishes;
	}
	
}
