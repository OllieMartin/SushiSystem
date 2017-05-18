package business;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DroneManager implements IngredientListener {

	private static final String[] POSTCODES = {"SO162HA","SO162BQ","SO162BW","SO163RT","SO152HT","SO163ST"};
	private static final float[] DISTANCES = {500,450,360,700,900,400};
	private List<Drone> drones;
	private Queue<DroneTask> tasks;
	private AccountManager am;

	public DroneManager(AccountManager am) {

		drones = new ArrayList<Drone>();
		tasks = new ConcurrentLinkedQueue<DroneTask>();
		this.am = am;
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
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
			
		}).start();

	}
	
	public void addTask(StockedIngredient ingredient) {
		tasks.add(new DroneIngredientTask(ingredient,ingredient.getIngredient().getSupplier().getDistance()));
	}

	public void addTask(Order order) {
		tasks.add(new DroneOrderTask(order,getDistanceToUser(order.getUser())));
	}

	public float getDistanceToUser(String user) {

		if (am.existsUser(user)) {
			return getDistanceTo(am.getUser(user).getPostcode());
		}
		return 0;

	}

	public void addDrone() {
		Drone drone = new Drone(this,5);
		drones.add(drone);
		new Thread(drone).start();
	}

	public float getDistanceTo(String postcode) {

		for (int i = 0 ; i < POSTCODES.length ; i++ ) {
			System.out.println(POSTCODES[i] + " " + postcode);
			if (POSTCODES[i].equals(postcode)) {
				return DISTANCES[i];
			}
		}
		return 0;

	}

	public DroneTask getTask() {
		synchronized (tasks) {
			if (tasks.peek() != null) {
				System.out.println("Can see a task");
				DroneTask task = tasks.peek();
				if (task.getType() == DroneTaskType.DELIVER_ORDER) {
					DroneOrderTask dot = (DroneOrderTask)task;
					boolean sufficientStock = true;
					synchronized (dot.getOrder().getDishes()) {
						for (OrderDish d : dot.getOrder().getDishes()) {
							if (StockedDish.getStockedDish(d.getDish().getName()).getNumberInStock() < d.getQuantity()) {
								System.out.println("DRONE: dishes not in stock :(");
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
					return tasks.poll();
				}
			}

		}
		return null;
	}
	
	private void checkStock(StockedIngredient i) {
		synchronized (i) {
			if (i.getNumberInStock() + i.getNumberBeingRestocked() < i.getRestockingLevel()) {
				i.incrementNumberBeingRestocked(20);
				addTask(i);
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