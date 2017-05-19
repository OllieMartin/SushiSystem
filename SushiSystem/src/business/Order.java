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

	static {
		nextId = 0;
	}

	public static void setNextId(Integer id) {
		synchronized (nextId) {
			nextId = id;
		}
	}

	public static Integer getNextId() {
		synchronized (nextId) {
			return nextId;
		}
	}

	public Order(String user, List<OrderDish> dishes) {
		this.dishes = dishes;
		this.user = user;
		this.status = OrderStatus.PLACED;
		synchronized (nextId) {
			this.id = nextId;
			nextId++;
		}
		price = 0;
		synchronized (dishes) {
			for (OrderDish dish : dishes) {
				price = price + dish.getDish().getPrice() * dish.getQuantity();
			}
		}
		if (BusinessApplicationPane.getOrderTableModel() != null) {
			this.addListener(BusinessApplicationPane.getOrderTableModel() );//TODO Move static reference location
		}
		newAdded();
	}

	public int getId() {
		return this.id;
	}

	public float getPrice() {
		return this.price;
	}

	public String getUser() {
		return this.user;
	}

	public List<OrderDish> getDishes() {
		synchronized (dishes) {
			return this.dishes;
		}
	}

	public OrderStatus getStatus() {
		synchronized (status) {
			return this.status;
		}
	}

	public void setStatus(OrderStatus status) {
		synchronized (this.status) { 
			this.status = status;
		}
		orderStatusChanged();
	}

	public void addListener(OrderListener toAdd) {
		synchronized (orderListeners) {
			orderListeners.add(toAdd);
		}
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
