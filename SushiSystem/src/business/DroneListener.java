package business;

import java.io.Serializable;

public interface DroneListener extends Serializable {

	void droneStatusChanged(Drone d);
	
	void droneDistanceChanged(Drone d);
	
	void droneAdded(Drone d);
	
	void droneRemoved(Drone d);
	
}
