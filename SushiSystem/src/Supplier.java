/**
 * Represents a supplier of one or more ingredients in the system
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class Supplier {

	private String name; // The name of the supplier
	private float distance; // The distance of the supplier from the Sushi business
	
	/**
	 * Create  a new Supplier with given name and distance
	 * 
	 * @param name The name of the supplier
	 * @param distance The distance from the Sushi business
	 */
	public Supplier(String name, float distance) {
		this.name = name;
		this.distance = distance;
	}
	
	/**
	 * Gets the name of the supplier
	 * 
	 * @return The name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the distance of the supplier from the sushi business
	 * 
	 * @return The distance
	 */
	public float getDistance() {
		return this.distance;
	}
	
}