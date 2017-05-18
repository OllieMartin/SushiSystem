package business;

public interface OrderListener {
	
	void orderStatusChanged(Order o);
	
	void orderPlaced(Order o);

}
