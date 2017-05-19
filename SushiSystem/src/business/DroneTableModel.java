package business;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DroneTableModel extends AbstractTableModel implements DroneListener {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Drone ID", "Status", "Flying Speed", "Distance From Destination", "Distance From Business"};
	private List<Drone> drones = new ArrayList<Drone>();
	boolean update;

	public DroneTableModel() {
		super();
	}

	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}

	public boolean hasUpdate() {
		return update;
	}

	public void setUpdated() {
		update = false;
	}

	private void addDrone(Drone d) {
		drones.add(d);
		update = true;
	}

	public void removeDrone(Drone d) {
		drones.remove(d);
		update = true;
	}

	@Override
	public int getRowCount() {
		return drones.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Drone drone;

		drone = drones.get(rowIndex);

		//TODO EXCEPTIONS
		if (drone == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			return drone.getId(); // ID
		case 1:
			return drone.getStatus();
		case 2:
			return drone.getFlyingSpeed();
		case 3:
			return drone.getDistanceToDestination();
		case 4:
			return drone.getDistanceFromBusiness();
		default:
			return null; //TODO EXCEPTIONS
		}
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void clear() {
		drones.clear();
		update = true;
	}

	@Override
	public void droneStatusChanged(Drone d) {
		// TODO Auto-generated method stub
		update = true;
	}

	@Override
	public void droneDistanceChanged(Drone d) {
		// TODO Auto-generated method stub
		update = true;
	}

	@Override
	public void droneAdded(Drone d) {
		// TODO Auto-generated method stub
		addDrone(d);
		update = true;
	}

}
