package business;

public class OrderDish {

	private Dish dish;
	private int quantity;
	
	public OrderDish(Dish dish, int quantity) {
		this.dish = dish;
		this.quantity = quantity;
	}
	
	public Dish getDish() {
		return this.dish;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
}
