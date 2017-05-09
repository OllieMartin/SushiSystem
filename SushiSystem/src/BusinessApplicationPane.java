import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

public class BusinessApplicationPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable dishTable;
	private DishTableModel dishTableModel; //TODO
	private JTable ingredientTable;
	private IngredientTableModel ingredientTableModel; //TODO

	public BusinessApplicationPane() {
		this.setLayout(new GridLayout(2,1));
		dishTableModel = new DishTableModel();
		dishTable = new JTable();
		dishTable.setModel(dishTableModel);
		this.add(dishTable);
		ingredientTableModel = new IngredientTableModel();
		ingredientTable = new JTable();
		ingredientTable.setModel(ingredientTableModel);
		this.add(ingredientTable);
		updateTables();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					updateTables();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		t.start();
	}

	public void updateTables() {
		List<StockedDish> stockedDishes = StockedDish.getStockedDishes();
		dishTableModel.clear();
		synchronized (stockedDishes) {

			for (StockedDish d : stockedDishes) {

				dishTableModel.addDish(d);

			}
		}
		dishTableModel.fireTableDataChanged();
		List<StockedIngredient> stockedIngredients = StockedIngredient.getStockedIngredients();
		ingredientTableModel.clear();
		synchronized (stockedIngredients) {

			for (StockedIngredient i : stockedIngredients) {

				ingredientTableModel.addIngredient(i);

			}
		}
		ingredientTableModel.fireTableDataChanged();
	}

}
