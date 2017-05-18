package business;

import java.io.Serializable;

public interface IngredientListener extends Serializable {

	void stockIncreased(StockedIngredient i);
	
	void stockDecreased(StockedIngredient i);
	
	void sufficientStock(StockedIngredient i);
	
	void outOfStock(StockedIngredient i);
	
	void ingredientAdded(StockedIngredient i);
	
	void restockingLevelChanged(StockedIngredient i);
	
}
