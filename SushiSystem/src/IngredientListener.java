public interface IngredientListener {

	void stockIncreased(StockedIngredient i);
	
	void stockDecreased(StockedIngredient i);
	
	void sufficientStock(StockedIngredient i);
	
	void outOfStock(StockedIngredient i);
	
	void ingredientAdded(StockedIngredient i);
	
	void restockingLevelChanged(StockedIngredient i);
	
}
