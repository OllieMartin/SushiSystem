package business;

import java.io.Serializable;

public class OrderDish implements Serializable {

	private static final long serialVersionUID = 1L;
	private Dish dish;
	private int quantity;
	
	public OrderDish(Dish dish, int quantity) {
		this.dish = new Dish(dish.getName(),dish.getDescription(),dish.getPrice(),dish.getRecipe());
		this.quantity = quantity;
	}
	
	public Dish getDish() {
		return this.dish;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
}
