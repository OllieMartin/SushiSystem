package business;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

//TODO Need to be able to remove kitchen staff from system (& update in model ofc)
public class KitchenStaff implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;
	private static List<KitchenStaff> kitchenStaff = new ArrayList<KitchenStaff>();

	public static List<KitchenStaff> getKitchenStaff() {
		return kitchenStaff;
	}

	public static void loadKitchenStaff(List<KitchenStaff> staff) {
		kitchenStaff = staff;
		for (KitchenStaff k : kitchenStaff) {
			k.setFree();
			if (BusinessApplicationPane.getKitchenTableModel() != null) {
				k.addListener(BusinessApplicationPane.getKitchenTableModel() );//TODO Move static reference location
			}
			k.newAdded();
			new Thread(k).start();
		}
	}

	private List<KitchenStaffListener> kitchenListeners = new ArrayList<KitchenStaffListener>();

	public KitchenStaff() {
		kitchenStaff.add(this);
		if (BusinessApplicationPane.getKitchenTableModel() != null) {
			this.addListener(BusinessApplicationPane.getKitchenTableModel() );//TODO Move static reference location
		}
		newAdded();
	}

	@Override
	public void run() {

		while (true) {
			checkDishStocks();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}

	private void checkDishStocks() {

		List<StockedDish> stockedDishes;
		StockedDish dish;

		synchronized (StockedDish.getStockedDishes()) {
			stockedDishes = new ArrayList<StockedDish>(StockedDish.getStockedDishes());
		}

		Iterator<StockedDish> it = stockedDishes.iterator();

		while (it.hasNext()) {

			dish = it.next();

			if (requiresRestock(dish)) {

				setBusy(dish.getDish());

				restock(dish);

				setFree();

			}

		}


	}

	private boolean requiresRestock(StockedDish dish) {
		synchronized(dish) {
			if (dish.getNumberInStock() + dish.getNumberBeingRestocked() < dish.getRestockingLevel() ||  dish.getNumberInStock() + dish.getNumberBeingRestocked() < dish.getDemand()) {
				dish.incrementNumberBeingRestocked(1);
				return true;
			}
			return false;
		}
	}

	private void restock(StockedDish dish) {

		Set<RecipeIngredient> recipeIngredients;
		RecipeIngredient ingredient;
		boolean sufficientIngredients;

		synchronized (dish.getDish().getRecipe()) {
			recipeIngredients = new HashSet<RecipeIngredient>(dish.getDish().getRecipe());
		}

		Iterator<RecipeIngredient> it = recipeIngredients.iterator();

		sufficientIngredients = true;
		while (it.hasNext()) {
			ingredient = it.next();

			if (!sufficientIngredient(ingredient)) {

				sufficientIngredients = false;
			}

		}

		if (sufficientIngredients) {
			it = recipeIngredients.iterator();
			StockedIngredient stocked;

			sufficientIngredients = true;
			while (it.hasNext()) {
				ingredient = it.next();
				synchronized (ingredient) {
					stocked = getStockedIngredient(ingredient.getIngredient());
					synchronized(stocked) {
						stocked.use(ingredient.getQuantity());
					}
				}

			}
			try {
				Thread.sleep((new Random().nextInt(40) + 20) *10); //TODO *1000);
			} catch (InterruptedException e) {/* Empty */}
			synchronized(dish) {
				dish.add(1);
			}

		}

		dish.decrementNumberBeingRestocked(1);

	}

	private boolean isInStock(Ingredient ingredient) {

		StockedIngredient stocked = getStockedIngredient(ingredient);
		if (stocked == null ) return false;
		if (stocked.getNumberInStock() > 0) {
			return true;
		}

		return false;
	}

	private StockedIngredient getStockedIngredient(Ingredient ingredient) {
		List<StockedIngredient> stockedIngredients;

		stockedIngredients = StockedIngredient.getStockedIngredients();
		synchronized (stockedIngredients) {
			for (StockedIngredient stocked : stockedIngredients) {
				synchronized (stocked) {
					if (stocked.getIngredient() == ingredient) {
						return stocked;
					}
				}
			}
		}
		return null;
	}

	private boolean sufficientIngredient(RecipeIngredient recipeIngredient) {

		synchronized (recipeIngredient) {
			if (isInStock(recipeIngredient.getIngredient())) {

				StockedIngredient ingredient;

				ingredient = getStockedIngredient(recipeIngredient.getIngredient());
				synchronized (ingredient) {
					if (ingredient.getNumberInStock() >= recipeIngredient.getQuantity()) {
						return true;
					}
					return false;
				}

			}
			return false;
		}

	}

	public void addListener(KitchenStaffListener toAdd) {
		kitchenListeners.add(toAdd);
	}

	public void setBusy(Dish d) {
		for (KitchenStaffListener l : kitchenListeners)
			l.kitchenStaffBusy(this, d);
	}

	public void setFree() {
		for (KitchenStaffListener l : kitchenListeners)
			l.kitchenStaffFree(this);
	}

	public void newAdded() {
		for (KitchenStaffListener l : kitchenListeners)
			l.kitchenStaffAdded(this);
	}


}
