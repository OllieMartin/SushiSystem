package business;

public class DroneIngredientTask extends DroneTask {

	private static final long serialVersionUID = 1L;
	private StockedIngredient ingredient;

	public DroneIngredientTask(StockedIngredient ingredient,float distance) {
		super(DroneTaskType.FETCH_INGREDIENTS,distance);
		this.ingredient = ingredient;
	}

	public StockedIngredient getIngredient() {
		return this.ingredient;
	}

}
