package business;

import java.io.Serializable;

public interface KitchenStaffListener extends Serializable {

	void kitchenStaffBusy(KitchenStaff k, Dish d);
	
	void kitchenStaffFree(KitchenStaff k);
	
	void kitchenStaffAdded(KitchenStaff k);
	
}
