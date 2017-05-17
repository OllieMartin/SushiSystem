package business;
/**
 * Represents an ingredient being used in a recipe along with its quantity
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class RecipeIngredient {

	private Ingredient ingredient; // The ingredient being used in the recipe
	private int quantity; // The amount of the ingredient being used in the recipe
	
	public RecipeIngredient(Ingredient ingredient, int quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	
	/**
	 * Gets the ingredient being represented in this recipe
	 * 
	 * @return The ingredient
	 */
	public Ingredient getIngredient() {
		return this.ingredient;
	}
	
	/**
	 * Gets the quantity of the ingredient required in the recipe
	 * 
	 * @return The quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
}
