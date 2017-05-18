package business;

import java.io.Serializable;

public interface OrderListener extends Serializable {
	
	void orderStatusChanged(Order o);
	
	void orderPlaced(Order o);

}
