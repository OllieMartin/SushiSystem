import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class KitchenStaff implements Runnable {

	public static void main(String args[]) {
		Dish d = new Dish("Food", "Some food", 10);
		Ingredient i = new Ingredient("FoodPart", "Parts", new Supplier("Supplier1", 100));
		d.addRecipeIngredient(i, 5);
		StockedDish sd = new StockedDish(d,10);
		StockedIngredient si = new StockedIngredient(i,30);
		
		Thread t = new Thread(new KitchenStaff());
		Thread t2 = new Thread(new KitchenStaff());
		t.start();
		t2.start();
	}
	
	public KitchenStaff() {

	}

	@Override
	public void run() {

		while (true) {
			checkDishStocks();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private boolean requiresRestock(StockedDish dish) {

		synchronized(dish) {
			if (dish.getNumberInStock() < dish.getRestockingLevel() && !dish.isBeingRestocked() ) {
				dish.toggleBeingRestocked();
				return true;
			}
			return false;
		}
	}

	private void restock(StockedDish dish) {

		Set<RecipeIngredient> recipeIngredients;
		RecipeIngredient ingredient;
		boolean sufficientIngredients;

		synchronized (dish.getDish().getRecipe()) {
			recipeIngredients = new HashSet<RecipeIngredient>(dish.getDish().getRecipe());
		}

		Iterator<RecipeIngredient> it = recipeIngredients.iterator();

		sufficientIngredients = true;
		while (it.hasNext()) {
			ingredient = it.next();

			if (!sufficientIngredient(ingredient)) {
				sufficientIngredients = false;
			}

		}

		if (sufficientIngredients) {

			it = recipeIngredients.iterator();
			StockedIngredient stocked;

			sufficientIngredients = true;
			while (it.hasNext()) {
				ingredient = it.next();

				synchronized (ingredient) {
					stocked = getStockedIngredient(ingredient.getIngredient());
					synchronized(stocked) {
						stocked.use(ingredient.getQuantity());
					}
				}

			}

			try {
				Thread.sleep((new Random().nextInt(40) + 20) *1000);
			} catch (InterruptedException e) {}
			synchronized(dish) {
				dish.add(1);
			}

		}

	}

	private boolean isInStock(Ingredient ingredient) {

		StockedIngredient stocked = getStockedIngredient(ingredient);
		if (stocked == null ) return false;
		if (stocked.getNumberInStock() > 0) {
			return true;
		}

		return false;
	}

	private StockedIngredient getStockedIngredient(Ingredient ingredient) {
		List<StockedIngredient> stockedIngredients;

		stockedIngredients = StockedIngredient.getStockedIngredients();
		synchronized (stockedIngredients) {
			for (StockedIngredient stocked : stockedIngredients) {
				synchronized (stocked) {
					if (stocked.getIngredient() == ingredient) {
						return stocked;
					}
				}
			}
		}
		return null;
	}

	private boolean sufficientIngredient(RecipeIngredient recipeIngredient) {

		synchronized (recipeIngredient) {
			if (isInStock(recipeIngredient.getIngredient())) {

				StockedIngredient ingredient;

				ingredient = getStockedIngredient(recipeIngredient.getIngredient());
				synchronized (ingredient) {
					if (ingredient.getNumberInStock() >= recipeIngredient.getQuantity()) {
						return true;
					}
					return false;
				}

			}
			return false;
		}

	}

	private void checkDishStocks() {

		System.out.println("Checking Dish Stock");
		List<StockedDish> stockedDishes;
		StockedDish dish;

		synchronized (StockedDish.getStockedDishes()) {
			stockedDishes = new ArrayList<StockedDish>(StockedDish.getStockedDishes());
		}

		Iterator<StockedDish> it = stockedDishes.iterator();

		while (it.hasNext()) {
			
			dish = it.next();
			System.out.println("Checking: " + dish.getDish().getName());

			if (requiresRestock(dish)) {
				
				System.out.println("Restocking: " + dish.getDish().getName());

				restock(dish);

			}

		}
		
		
	}

}
