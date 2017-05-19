package business;

import java.io.Serializable;

public abstract class DroneTask implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private DroneTaskType type;
	private float distance;

	public DroneTask(DroneTaskType type, float distance) {
		this.type = type;
		this.distance = distance;
	}
	
	public DroneTaskType getType() {
		return this.type;
	}
	
	public float getDistance() {
		return this.distance;
	}
	
}
