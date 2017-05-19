package business;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents an Ingredient stocked by the company
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class StockedIngredient extends StockedProduct {

	private static final long serialVersionUID = 1L;
	private static List<StockedIngredient> stockedIngredients = new ArrayList<StockedIngredient>();

	public static List<StockedIngredient> getStockedIngredients() {
		return stockedIngredients;
	}

	public static void loadStockedIngredients(List<StockedIngredient> ingredients) {
		stockedIngredients = ingredients;
		for (StockedIngredient i : stockedIngredients) {
			i.decrementNumberBeingRestocked(i.getNumberBeingRestocked());
			if (BusinessApplicationPane.getIngredientTableModel() != null) {
				i.addListener(BusinessApplicationPane.getIngredientTableModel() );//TODO Move static reference location
			}
			i.newAdded();
		}
	}

	private List<IngredientListener> ingredientListeners = new ArrayList<IngredientListener>();

	/**
	 * Gets the StockedIngredient for a given Ingredient
	 * 
	 * @param ingredient The ingredient to retrieve the stock for
	 * @return The Stocked Ingredient
	 * @throws NoSuchElementException if the ingredient is not stocked
	 */
	public static StockedIngredient getStockedIngredient(Ingredient ingredient) {
		for (StockedIngredient sIngredient : stockedIngredients) {
			if (sIngredient.getIngredient() == ingredient) {
				return sIngredient;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Checks if a ingredient is being stocked by the system
	 * 
	 * @param ingredient The ingredient to check
	 * @return True if it is being stocked, false otherwise
	 */
	public static boolean isStocked(Ingredient ingredient) {
		for (StockedIngredient sIngredient : stockedIngredients) {
			if (sIngredient.getIngredient() == ingredient) {
				return true;
			}
		}
		return false;
	}

	private Ingredient ingredient; // The ingredient being stocked

	/**
	 * Create a new StockedIngredient with the specified ingredient and restocking level
	 * 
	 * @param ingredient The ingredient to be stored
	 * @param restockingLevel The restocking level for the ingredient
	 */
	public StockedIngredient(Ingredient ingredient, int restockingLevel) {
		super(restockingLevel, ProductType.INGREDIENT);
		this.ingredient = ingredient;
		stockedIngredients.add(this);
		if (BusinessApplicationPane.getIngredientTableModel() != null) {
			this.addListener(BusinessApplicationPane.getIngredientTableModel() );//TODO Move static reference location
		}
		if (BusinessApplication.getInstance().getDroneManager() != null) {
			this.addListener(BusinessApplication.getInstance().getDroneManager() );//TODO Move static reference location
		}
		newAdded();
	}

	/**
	 * Get the ingredient being stored
	 * 
	 * @return The ingredient being stored
	 */
	public Ingredient getIngredient() {
		return this.ingredient;
	}

	/**
	 * Removes this ingredient from the list of stocked ingredients if it is in the list
	 */
	public void remove() {
		if ( stockedIngredients.contains(this) ) stockedIngredients.remove(this);
	}

	@Override
	public String toString() {
		return this.getIngredient().toString();
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

	public void addListener(IngredientListener toAdd) {
		ingredientListeners.add(toAdd);
	}

	public void stockIncreased() {
		for (IngredientListener l : ingredientListeners)
			l.stockIncreased(this);
	}

	public void stockDecreased() {
		for (IngredientListener l : ingredientListeners)
			l.stockDecreased(this);
	}

	public void newAdded() {
		for (IngredientListener l : ingredientListeners)
			l.ingredientAdded(this);
	}

	public void stockSufficient() {
		for (IngredientListener l : ingredientListeners)
			l.sufficientStock(this);
	}

	public void stockOut() {
		for (IngredientListener l : ingredientListeners)
			l.outOfStock(this);
	}

	public void restockingLevelChanged() {
		for (IngredientListener l : ingredientListeners)
			l.restockingLevelChanged(this);
	}



}
