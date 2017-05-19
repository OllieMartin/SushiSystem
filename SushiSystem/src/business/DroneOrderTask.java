package business;

import java.io.Serializable;

public class DroneOrderTask extends DroneTask implements Serializable {

	private static final long serialVersionUID = 1L;
	private Order order;

	public DroneOrderTask(Order order,float distance) {
		super(DroneTaskType.DELIVER_ORDER,distance);
		this.order = order;
	}

	public Order getOrder() {
		return this.order;
	}

}
