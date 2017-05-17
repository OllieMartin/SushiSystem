package comms;

import java.util.List;

import business.OrderDish;

public class OrderMessage extends Message {

	private static final long serialVersionUID = 1L;
	private List<OrderDish> dishes;
	
	public OrderMessage(List<OrderDish> dishes) {
		super(MessageType.ORDER);
		this.dishes = dishes;
	}
	
	public List<OrderDish> getDishes() {
		return dishes;
	}

}
