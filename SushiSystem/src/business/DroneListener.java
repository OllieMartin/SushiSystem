package business;

public interface DroneListener {

	void droneStatusChanged(Drone d);
	
	void droneDistanceChanged(Drone d);
	
	void droneAdded(Drone d);
	
}
