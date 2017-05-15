public interface DishListener {

	void stockIncreased(StockedDish d);
	
	void stockDecreased(StockedDish d);
	
	void sufficientStock(StockedDish d);
	
	void outOfStock(StockedDish d);
	
	void dishAdded(StockedDish d);
	
	void restockingLevelChanged(StockedDish d);
	
}
