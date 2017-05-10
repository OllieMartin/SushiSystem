import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BusinessApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() {  
				new BusinessApplication();
			}
		});
		
		Dish d = new Dish("Food", "Some food", 10);
		Ingredient i = new Ingredient("FoodPart", "Parts", new Supplier("Supplier1", 100));
		d.addRecipeIngredient(i, 5);
		StockedDish sd = new StockedDish(d,10);
		StockedIngredient si = new StockedIngredient(i,30);
		si.add(60);

		//Thread t = new Thread(new KitchenStaff());
		//Thread t2 = new Thread(new KitchenStaff());
		//t.start();
		//t2.start();
		
	}
	
	private static final int DEFAULT_WIDTH = 1000; // Default width for the main window pixels
	private static final int DEFAULT_HEIGHT = 500; // Default height for the main window pixels
	private static final int MINIMUM_WIDTH = 750; // Minimum width for the main window pixels
	private static final int MINIMUM_HEIGHT = 300; // Minimum height for the main window pixels
	
	private BusinessApplicationPane mainPane;
	
	public BusinessApplication() {
		
		super("Sushi System - Business Application");

		mainPane = new BusinessApplicationPane();
		this.add(mainPane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

		this.setVisible(true);
		
	}
	
}
