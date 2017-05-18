package business;

public class DroneOrderTask extends DroneTask {
	
	private Order order;

	public DroneOrderTask(Order order,float distance) {
		super(DroneTaskType.DELIVER_ORDER,distance);
		this.order = order;
	}
	
	public Order getOrder() {
		return this.order;
	}
	
}
