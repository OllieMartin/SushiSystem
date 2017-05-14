import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class BusinessApplicationPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable dishTable;
	private DishTableModel dishTableModel; //TODO
	private JTable ingredientTable;
	private IngredientTableModel ingredientTableModel; //TODO
	private JButton addKitchenStaff = new JButton("Add Kitchen Staff");
	private JButton addSupplier;
	private JButton addIngredient;
	private JButton addDish;
	private JButton changeRestockingLevel;


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

		addSupplier = new JButton("Add New Supplier");
		addSupplier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSupplierFrame();
			}

		});
		this.add(addSupplier);

		addIngredient = new JButton("Add New Ingredient");
		addIngredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddIngredientFrame();
			}

		});
		this.add(addIngredient);

		addDish = new JButton("Add New Dish");
		addDish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddDishFrame();
			}

		});
		this.add(addDish);
		
		changeRestockingLevel = new JButton("Change restocking levels");
		changeRestockingLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeRestockingLevelFrame();
			}

		});
		this.add(changeRestockingLevel);

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
