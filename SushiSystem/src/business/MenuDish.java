package business;

import java.io.Serializable;

public class MenuDish implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Dish dish;
	int stock;
	
	public MenuDish(StockedDish dish) {
		
		this.dish = new Dish(dish.getDish().getName(),dish.getDish().getDescription(),dish.getDish().getPrice(),dish.getDish().getRecipe());
		this.stock = dish.getNumberInStock();
		
	}
	
	public Dish getDish() {
		return this.dish;
	}
	
	public int getStock() {
		return this.stock;
	}
	
}
