package business;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a supplier of one or more ingredients in the system
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;
	private static List<Supplier> suppliers = new ArrayList<Supplier>();

	public static List<Supplier> getSuppliers() {
		return suppliers;
	}
	
	public static void loadSuppliers(List<Supplier> suppliers) {
		Supplier.suppliers = suppliers;
	}


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
		suppliers.add(this);
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

	@Override
	public String toString() {

		return getName();

	}

	/**
	 * Removes this supplier from the list of suppliers if it is in the list
	 */
	public void remove() {
		if ( suppliers.contains(this) ) suppliers.remove(this);
	}

}