package business;
/**
 * Represents the types of products held in company storage
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public enum ProductType {

	INGREDIENT {
		@Override
		public String toString() {
			return "Ingredient";
		}
	}, // An ingredient
	
	DISH {
		@Override
		public String toString() {
			return "Dish";
		}
	} // A pre-made dish
	
}
