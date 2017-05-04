/**
 * Represents a product held in stock by the company
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public abstract class StockedProduct {

	protected ProductType type; // The type of product being stocked, ingredient or dish
	protected int restockingLevel; // The re-stocking level of the product
	protected int stock; // The current stock level of the product
	
	public StockedProduct(int restockingLevel, ProductType type) {
		this.restockingLevel = restockingLevel;
		this.type = type;
		stock = 0;
	}
	
	/**
	 * Get the re-stocking level of the product in stock
	 * @return The re-stocking level of the product
	 */
	public int getRestockingLevel() {
		return this.restockingLevel;
	}
	
	/**
	 * Get the current number of this product in stock
	 * @return The number of the product in stock
	 */
	public synchronized int getNumberInStock() {
		return this.stock;
	}
	
	/**
	 * Get the type of product being stored
	 * @return The type of the product
	 */
	public ProductType getType() {
		return this.type;
	}

	public synchronized void use(int numberToUse) {
		if (numberToUse <= stock) {
			stock = stock - numberToUse;
		} else 
			stock = 0;
	}
	
	public synchronized void add(int numberToAdd) {
		stock = stock + numberToAdd;
	}
}
