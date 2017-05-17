package business;

import java.util.List;

public class Order {

	private List<OrderDish> dishes;
	private String user;
	private OrderStatus status;
	
	public Order(String user, List<OrderDish> dishes) {
		this.dishes = dishes;
		this.user = user;
		this.status = OrderStatus.PLACED;
	}
	
	public float getPrice() { //TODO Thread safety
		
		float price = 0;
		for (OrderDish od : dishes) {
			price = price + (od.getDish().getPrice()*od.getQuantity());
		}
		
		return price;
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
	}
	
}
