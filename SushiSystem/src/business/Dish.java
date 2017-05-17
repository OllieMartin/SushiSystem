package business;
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Represents a Sushi Dish in the system made of one or more ingredients
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; // The name of the dish
	private String description; // A brief description of the dish
	private float price; // The price of the dish
	private Set<RecipeIngredient> recipe; // The recipe of the dish, made up of recipe ingredients

	/**
	 * Creates a new dish with the specified parameters and an initially empty recipe
	 * 
	 * @param name The name of the dish
	 * @param description The description of the dish
	 * @param price The price of the dish
	 */
	public Dish(String name, String description, float price) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.recipe = new HashSet<RecipeIngredient>();
	}

	/**
	 * Gets the name of the dish
	 * @return The name of the dish
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description of the dish
	 * @return The description of the dish
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the price of the dish
	 * @return The price of the dish
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Adds an ingredient to the recipe of this dish
	 * 
	 * @param ingredient The ingredient being used in the dish
	 * @param quantity The quantity of the ingredient
	 */
	public void addRecipeIngredient(Ingredient ingredient, int quantity) {
		this.recipe.add(new RecipeIngredient(ingredient,quantity));
	}

	/**
	 * Gets the recipe of the dish as a set of RecipeIngredients.
	 * 
	 * @return The recipe of the dish
	 */
	public Set<RecipeIngredient> getRecipe() {
		return this.recipe;
	}

	/**
	 * Returns true if this dish uses the specified ingredient in its recipe
	 * 
	 * @param ingredient The ingredient to check
	 * @return True if the ingredient is used in the recipe, false otherwise
	 */
	public boolean usesIngredient(Ingredient ingredient) {

		for (RecipeIngredient rIngredient : recipe) {
			if (rIngredient.getIngredient() == ingredient) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Returns the quantity of the ingredient used in the recipe
	 * 
	 * @param ingredient The ingredient to check
	 * @return The quantity of the ingredient
	 * @throws NoSuchElementException If the ingredient is not in the recipe
	 */
	public int getQuantity(Ingredient ingredient) {

		for (RecipeIngredient rIngredient : recipe) {
			if (rIngredient.getIngredient() == ingredient) {
				return rIngredient.getQuantity();
			}
		}

		throw new NoSuchElementException();

	}

	@Override
	public String toString() {
		return getName();
	}

}
