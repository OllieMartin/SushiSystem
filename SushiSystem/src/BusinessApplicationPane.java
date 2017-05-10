import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class BusinessApplicationPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable dishTable;
	private DishTableModel dishTableModel; //TODO
	private JTable ingredientTable;
	private IngredientTableModel ingredientTableModel; //TODO
	private AddDishPane dishPane = new AddDishPane();
	private AddIngredientPane ingredientPane = new AddIngredientPane();
	private AddSupplierPane supplierPane = new AddSupplierPane(ingredientPane);
	private JButton addKitchenStaff = new JButton("Add Kitchen Staff");


	public BusinessApplicationPane() {
		this.setLayout(new GridLayout(3,3));
		dishTableModel = new DishTableModel();
		dishTable = new JTable();
		dishTable.setModel(dishTableModel);
		this.add(dishTable,0);
		ingredientTableModel = new IngredientTableModel();
		ingredientTable = new JTable();
		ingredientTable.setModel(ingredientTableModel);
		this.add(ingredientTable,1);
		this.add(dishPane);
		this.add(ingredientPane);
		this.add(supplierPane);
		this.add(addKitchenStaff);
		addKitchenStaff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new KitchenStaff()).start();
			}
			
		});
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
