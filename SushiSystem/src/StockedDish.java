import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a pre-made dish stocked by the company
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class StockedDish extends StockedProduct {

	private static List<StockedDish> stockedDishes = new ArrayList<StockedDish>();
	
	public static List<StockedDish> getStockedDishes() {
		return stockedDishes;
	}
	
	private List<DishListener> dishListeners = new ArrayList<DishListener>();
	
	/**
	 * Gets the StockedDish for a given Dish
	 * 
	 * @param dish The dish to retrieve the stock for
	 * @return The Stocked Dish
	 * @throws NoSuchElementException if the dish is not stocked
	 */
	public static StockedDish getStockedDish(Dish dish) {
		for (StockedDish sDish : stockedDishes) {
			if (sDish.getDish() == dish) {
				return sDish;
			}
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * Checks if a dish is being stocked by the system
	 * 
	 * @param dish The dish to check
	 * @return True if it is being stocked, false otherwise
	 */
	public static boolean isStocked(Dish dish) {
		for (StockedDish sDish : stockedDishes) {
			if (sDish.getDish() == dish) {
				return true;
			}
		}
		return false;
	}
	
	private Dish dish; // The ingredient being stocked
	private boolean beingRestocked; // If the ingredient is already in the process of being restocked by another staff member
	
	/**
	 * Create a new StockedDish with the specified pre-made dish and restocking level
	 * It is then added to the static list of stocked dishes
	 * 
	 * @param dish The dish to be represented in storage
	 * @param restockingLevel The restocking level of the dish
	 */
	public StockedDish(Dish dish, int restockingLevel) {
		super(restockingLevel, ProductType.DISH);
		this.dish = dish;
		stockedDishes.add(this);
		if (BusinessApplicationPane.getDishTableModel() != null) {
			this.addListener(BusinessApplicationPane.getDishTableModel() );//TODO Move static reference location
		}
		newAdded();
	}
	
	/**
	 * Get the dish being stored
	 * 
	 * @return The dish being stored
	 */
	public Dish getDish() {
		return this.dish;
	}
	
	/**
	 * Removes this dish from the list of stocked dishes if it is in the list
	 */
	public void remove() {
		if ( stockedDishes.contains(this) ) stockedDishes.remove(this);
	}
	
	public void toggleBeingRestocked() {
		this.beingRestocked = !this.beingRestocked;
	}
	
	public boolean isBeingRestocked() {
		return this.beingRestocked;
	}
	
	@Override
	public String toString() {
		return this.getDish().toString();
	}
	
	@Override
	public synchronized void use(int numberToUse) {
		super.use(numberToUse);
		stockDecreased();
		if (getNumberInStock() == 0) {
			stockOut();
		}
	}
	
	@Override
	public synchronized void add(int numberToAdd) {
		super.add(numberToAdd);
		stockIncreased();
		
		if (getNumberInStock() == getRestockingLevel()) {
			stockSufficient();
		}
	}
	
	@Override
	public void setRestockingLevel(int restockingLevel) {
		super.setRestockingLevel(restockingLevel);
		restockingLevelChanged();
		
	}
	
	public void addListener(DishListener toAdd) {
		dishListeners.add(toAdd);
	}

	public void stockIncreased() {
		for (DishListener l : dishListeners)
			l.stockIncreased(this);
	}

	public void stockDecreased() {
		for (DishListener l : dishListeners)
			l.stockDecreased(this);
	}

	public void newAdded() {
		for (DishListener l : dishListeners)
			l.dishAdded(this);
	}
	
	public void stockSufficient() {
		for (DishListener l : dishListeners)
			l.sufficientStock(this);
	}
	
	public void stockOut() {
		for (DishListener l : dishListeners)
			l.outOfStock(this);
	}
	
	public void restockingLevelChanged() {
		for (DishListener l : dishListeners)
			l.restockingLevelChanged(this);
	}
	
}
