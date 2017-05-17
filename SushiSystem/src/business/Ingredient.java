package business;
/**
 * Represents an ingredient to be made into Sushi in the system
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class Ingredient {

	private String name; // The name of the ingredient
	private String unit; // The unit the quantity is typically measured in, i.e. grams or litres
	private Supplier supplier; // The supplier of this ingredient
	
	/**
	 * Creates a new ingredient with the specified parameters
	 * 
	 * @param name The name of the ingredient
	 * @param unit The unit the quantity of this ingredient is usually measured in
	 * @param supplier The supplier of the this ingredient
	 */
	public Ingredient(String name, String unit, Supplier supplier) {
		this.name = name;
		this.unit = unit;
		this.supplier = supplier;
	}
	
	/**
	 * Gets the name of the ingredient
	 * @return The name of the ingredient
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the units used to represent the quantity of this ingredient
	 * @return The name of the units to be used
	 */
	public String getUnit() {
		return this.unit;
	}
	
	/**
	 * Gets the supplier of this ingredient
	 * @return The supplier of the ingredient
	 */
	public Supplier getSupplier() {
		return this.supplier;
	}
	
	/**
	 * Changes the supplier of the ingredient to a newly specified supplier
	 * @param newSupplier
	 */
	public void changeSupplier(Supplier newSupplier) {
		this.supplier = newSupplier;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
