package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{

	private List<OrderListener> orderListeners = new ArrayList<OrderListener>();
	
	private static final long serialVersionUID = 1L;
	private List<OrderDish> dishes;
	private String user;
	private OrderStatus status;
	private int id;
	private static Integer nextId;
	private float price;
	
	public Order(String user, List<OrderDish> dishes) {
		this.dishes = dishes;
		this.user = user;
		this.status = OrderStatus.PLACED;
		if (nextId == null) {
			nextId = 0;
		}
		synchronized (nextId) {
			this.id = nextId;
			nextId++;
		}
		price = 0;
		for (OrderDish dish : dishes) {
			price = price + dish.getDish().getPrice() * dish.getQuantity();
		}
		if (BusinessApplicationPane.getOrderTableModel() != null) {
			this.addListener(BusinessApplicationPane.getOrderTableModel() );//TODO Move static reference location
		}
		newAdded();
	}
	
	public int getId() {
		return this.id;
	}
	
	public float getPrice() { //TODO Thread safety
		
		return this.price;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public List<OrderDish> getDishes() {
		return this.dishes;
	}
	
	public OrderStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
		orderStatusChanged();
	}
	
	public void addListener(OrderListener toAdd) {
		orderListeners.add(toAdd);
	}

	public void newAdded() {
		for (OrderListener l : orderListeners)
			l.orderPlaced(this);
	}
	
	public void orderStatusChanged() {
		for (OrderListener l : orderListeners)
			l.orderStatusChanged(this);
	}
	
}
