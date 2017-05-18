package business;

public abstract class DroneTask {
	
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
