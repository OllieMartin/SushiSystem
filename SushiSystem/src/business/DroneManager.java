package business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DroneManager implements IngredientListener {
	
	private static final long serialVersionUID = 1L;
	private static final String[] POSTCODES = {"SO162HA","SO162BQ","SO162BW","SO163RT","SO152HT","SO163ST"};
	private static final float[] DISTANCES = {500,450,360,700,900,400};
	private List<Drone> drones;
	private Queue<DroneTask> tasks;
	private static DroneManager instance;

	public static DroneManager getInstance() {
		return instance;
	}

	static {
		instance = new DroneManager();
	}

	private DroneManager() {

		drones = new ArrayList<Drone>();
		tasks = new ConcurrentLinkedQueue<DroneTask>();
		new Thread(new Runnable() {

			@Override
			public void run() {

				List<StockedIngredient> stockedIngredients;

				while (true) {
					stockedIngredients = new ArrayList<StockedIngredient>(StockedIngredient.getStockedIngredients());

					for (StockedIngredient i : stockedIngredients) {
						checkStock(i);
					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {/* Empty */}

				}

			}

		}).start();

	}

	public List<Drone> getDrones() {
		return this.drones;
	}

	public void loadDrones(List<Drone> drones) {
		this.drones = drones;
		for (Drone d : this.drones) {
			d.reset();
			if (BusinessApplicationPane.getDroneTableModel() != null) {
				d.addListener(BusinessApplicationPane.getDroneTableModel() );
			}
			d.newAdded();
			new Thread(d).start();
		}
	}

	public void addTask(StockedIngredient ingredient) {
		synchronized (tasks) {
			tasks.offer(new DroneIngredientTask(ingredient,ingredient.getIngredient().getSupplier().getDistance()));
		}
	}

	public void addTask(Order order) {
		synchronized (tasks) {
			tasks.offer(new DroneOrderTask(order,getDistanceToUser(order.getUser())));
		}
	}

	public float getDistanceToUser(String user) {

		if (AccountManager.getInstance().existsUser(user)) {
			return getDistanceTo(AccountManager.getInstance().getUser(user).getPostcode());
		}
		return 0;

	}

	public void addDrone() {
		Drone drone = new Drone(5);
		drones.add(drone);
		new Thread(drone).start();
	}
	
	public void remove(Integer id) {
		synchronized (drones) {
			Iterator<Drone> it = drones.iterator();
			Drone d;
			while (it.hasNext()) {
				d = it.next();
				if (d.getId() == id) {
					d.enabled = false;
					it.remove();
					d.removed();
				}
			}
		}
	}

	public float getDistanceTo(String postcode) {

		for (int i = 0 ; i < POSTCODES.length ; i++ ) {
			if (POSTCODES[i].equals(postcode)) {
				return DISTANCES[i];
			}
		}
		return 0;

	}

	public DroneTask getTask() {
		synchronized (tasks) {
			if (tasks.peek() != null) {
				DroneTask task = tasks.peek();
				if (task.getType() == DroneTaskType.DELIVER_ORDER) {
					DroneOrderTask dot = (DroneOrderTask)task;
					boolean sufficientStock = true;
					synchronized (dot.getOrder().getDishes()) {
						for (OrderDish d : dot.getOrder().getDishes()) {
							if (StockedDish.getStockedDish(d.getDish().getName()).getNumberInStock() < d.getQuantity()) {
								tasks.offer(tasks.poll());
								sufficientStock = false;
							}
						}
						if (sufficientStock) {
							for (OrderDish d : dot.getOrder().getDishes()) {
								StockedDish.getStockedDish(d.getDish().getName()).use(d.getQuantity());
							}
							return tasks.poll();
						}
					}
				} else if (task.getType() == DroneTaskType.FETCH_INGREDIENTS){
					DroneIngredientTask dit = (DroneIngredientTask)task;
					if (dit.getIngredient().getNumberInStock() >= dit.getIngredient().getRestockingLevel()) {
						dit.getIngredient().decrementNumberBeingRestocked(20);
						tasks.poll();
						return null;
					}
					return tasks.poll();
				}
			}

		}
		return null;
	}

	private void checkStock(StockedIngredient i) {
		synchronized (i) {
			synchronized (tasks) {
				if (i.getNumberInStock() + i.getNumberBeingRestocked() < i.getRestockingLevel()) {
					i.incrementNumberBeingRestocked(20);
					addTask(i);
				}
			}
		}

	}

	@Override
	public void stockIncreased(StockedIngredient i) {
		checkStock(i);

	}

	@Override
	public void stockDecreased(StockedIngredient i) {
		checkStock(i);

	}

	@Override
	public void sufficientStock(StockedIngredient i) {
		checkStock(i);

	}

	@Override
	public void outOfStock(StockedIngredient i) {
		checkStock(i);

	}

	@Override
	public void ingredientAdded(StockedIngredient i) {
		checkStock(i);

	}

	@Override
	public void restockingLevelChanged(StockedIngredient i) {
		checkStock(i);

	}

}
