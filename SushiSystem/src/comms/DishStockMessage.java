package comms;

import java.util.ArrayList;
import java.util.List;

import business.MenuDish;

public class DishStockMessage extends Message {

	private static final long serialVersionUID = 1L;

	private List<MenuDish> dishes;

	public DishStockMessage(List<MenuDish> dishes) {
		super(MessageType.DISH_STOCK);
		this.dishes = new ArrayList<MenuDish>(dishes);
	}

	public List<MenuDish> getDishes() {
		return this.dishes;
	}

}
