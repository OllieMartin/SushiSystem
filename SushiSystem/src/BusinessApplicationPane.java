import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class BusinessApplicationPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable dishTable;
	private JTable ingredientTable;
	private JTable kitchenTable;
	private JButton addKitchenStaff = new JButton("Add Kitchen Staff");
	private JButton addSupplier;
	private JButton addIngredient;
	private JButton addDish;
	private JButton changeRestockingLevel;
	private JTabbedPane tabs;
	
	private static KitchenTableModel kitchenTableModel; //TODO
	public static KitchenTableModel getKitchenTableModel() {
		return kitchenTableModel;
	}
	
	private static DishTableModel dishTableModel; //TODO
	public static DishTableModel getDishTableModel() {
		return dishTableModel;
	}
	
	private static IngredientTableModel ingredientTableModel; //TODO
	public static IngredientTableModel getIngredientTableModel() {
		return ingredientTableModel;
	}


	public BusinessApplicationPane() {
		tabs = new JTabbedPane();
		this.add(tabs);
		this.setLayout(new GridLayout(3,3));
		dishTableModel = new DishTableModel();
		dishTable = new JTable();
		dishTable.setModel(dishTableModel);
		tabs.addTab("Dishes" ,dishTable);
		ingredientTableModel = new IngredientTableModel();
		ingredientTable = new JTable();
		ingredientTable.setModel(ingredientTableModel);
		tabs.addTab("Ingredients",ingredientTable);
		
		kitchenTableModel = new KitchenTableModel();
		kitchenTable = new JTable();
		kitchenTable.setModel(kitchenTableModel);
		tabs.addTab("Kitchen Staff",kitchenTable);

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
						Thread.sleep(200);
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
		if (dishTableModel.hasUpdate()) {
			dishTableModel.fireTableDataChanged();
			dishTableModel.setUpdated();
		}
		
		if (ingredientTableModel.hasUpdate()) {
			ingredientTableModel.fireTableDataChanged();
			ingredientTableModel.setUpdated();
		}
		
		if (kitchenTableModel.hasUpdate()) {
			kitchenTableModel.fireTableDataChanged();
			kitchenTableModel.setUpdated();
		}
		
	}

}
