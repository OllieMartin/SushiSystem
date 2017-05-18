package business;

import java.util.ArrayList;
import java.util.List;

public class Drone implements Runnable {

	private int flyingSpeed;
	private DroneStatus status;
	private DroneTask task;
	private float distanceToDestination;
	private float distanceFromBusiness;
	private DroneManager dm;
	private int id;
	private static Integer nextId;
	
	private List<DroneListener> droneListeners = new ArrayList<DroneListener>();

	public Drone(DroneManager dm, int flyingSpeed) {
		this.flyingSpeed = flyingSpeed;
		this.status = DroneStatus.IDLE;
		this.distanceToDestination = -1;
		this.distanceFromBusiness = -1;
		this.dm = dm;
		if (nextId == null) {
			nextId = 0;
		}
		synchronized (nextId) {
			id = nextId;
			nextId++;
		}
		if (BusinessApplicationPane.getDroneTableModel() != null) {
			this.addListener(BusinessApplicationPane.getDroneTableModel() );//TODO Move static reference location
		}
		newAdded();
	}

	public int getId() {
		return this.id;
	}
	
	public int getFlyingSpeed() {
		return this.flyingSpeed;
	}

	public DroneStatus getStatus() {
		return this.status;
	}

	public float getDistanceToDestination() {
		return distanceToDestination;
	}

	public float getDistanceFromBusiness() {
		return distanceFromBusiness;
	}

	public void setTask(DroneTask task) {
		this.task = task;
		if (task.getType() == DroneTaskType.DELIVER_ORDER) {
			DroneOrderTask orderTask = (DroneOrderTask) task;
			this.distanceToDestination = task.getDistance();
			this.distanceFromBusiness = 0;
			status = DroneStatus.DELIVERING_ORDER;
			statusChanged();
			orderTask.getOrder().setStatus(OrderStatus.TRANSIT);

		} else {
			status = DroneStatus.COLLECTING_INGREDIENT;
			statusChanged();
		}
	}

	@Override
	public void run() {
		DroneTask task;
		while (true) {

			if (status == DroneStatus.IDLE) {
				task = dm.getTask();
				if (task != null) {
					setTask(task);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (status == DroneStatus.DELIVERING_ORDER) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				distanceToDestination -= flyingSpeed;
				distanceFromBusiness += flyingSpeed;
				distanceChanged();

				if (distanceToDestination <= 0) {
					DroneOrderTask orderTask = (DroneOrderTask) this.task;
					orderTask.getOrder().setStatus(OrderStatus.DELIVERED);
					this.status = DroneStatus.RETURNING_ORDER;
					statusChanged();
				}

			}
			if (status == DroneStatus.RETURNING_ORDER) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				distanceToDestination += flyingSpeed;
				distanceFromBusiness -= flyingSpeed;
				distanceChanged();
				if (distanceFromBusiness <= 0) {
					this.distanceToDestination = -1;
					this.distanceFromBusiness = -1;
					this.status = DroneStatus.IDLE;
					statusChanged();
					distanceChanged();
					this.task = null;
				}

			}
			if (status == DroneStatus.COLLECTING_INGREDIENT) {

			}


		}

	}
	
	public void addListener(DroneListener toAdd) {
		droneListeners.add(toAdd);
	}

	public void statusChanged() {
		for (DroneListener l : droneListeners)
			l.droneStatusChanged(this);
	}

	public void distanceChanged() {
		for (DroneListener l : droneListeners)
			l.droneDistanceChanged(this);
	}

	public void newAdded() {
		for (DroneListener l : droneListeners)
			l.droneAdded(this);
	}

}
