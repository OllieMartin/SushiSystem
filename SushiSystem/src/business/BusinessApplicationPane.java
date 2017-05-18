package business;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class BusinessApplicationPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable dishTable;
	private JTable ingredientTable;
	private JTable kitchenTable;
	private JButton addKitchenStaff = new JButton("Add Kitchen Staff");
	private JButton addDrone = new JButton("Add Drone");
	private JButton addSupplier;
	private JButton addIngredient;
	private JButton addDish;
	private JButton changeRestockingLevel;
	private JTabbedPane tabs;
	private JTable orderTable;
	private JTable droneTable;
	private JButton saveButton;
	
	private JPanel addButtons;
	private JPanel others;

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
	
	private static OrderTableModel orderTableModel; //TODO
	public static OrderTableModel getOrderTableModel() {
		return orderTableModel;
	}
	
	private static DroneTableModel droneTableModel; //TODO
	public static DroneTableModel getDroneTableModel() {
		return droneTableModel;
	}


	public BusinessApplicationPane(BusinessApplication ba) {
		tabs = new JTabbedPane();
		
		//this.setLayout(new GridLayout(3,1));
		this.setLayout(new BorderLayout());
		this.add(tabs,BorderLayout.CENTER);
		
		addButtons = new JPanel();
		addButtons.setLayout(new GridLayout(1,5));
		others = new JPanel();
		others.setLayout(new GridLayout(1,2));
		
		dishTableModel = new DishTableModel();
		dishTable = new JTable();
		dishTable.setModel(dishTableModel);
		tabs.addTab("Dishes" ,new JScrollPane(dishTable));
		ingredientTableModel = new IngredientTableModel();
		ingredientTable = new JTable();
		ingredientTable.setModel(ingredientTableModel);
		tabs.addTab("Ingredients",new JScrollPane(ingredientTable));

		kitchenTableModel = new KitchenTableModel();
		kitchenTable = new JTable();
		kitchenTable.setModel(kitchenTableModel);
		tabs.addTab("Kitchen Staff",new JScrollPane(kitchenTable));
		
		orderTableModel = new OrderTableModel();
		orderTable = new JTable();
		orderTable.setModel(orderTableModel);
		tabs.addTab("Orders",new JScrollPane(orderTable));
		
		droneTableModel = new DroneTableModel();
		droneTable = new JTable();
		droneTable.setModel(droneTableModel);
		tabs.addTab("Drones",new JScrollPane(droneTable));

		addSupplier = new JButton("Add New Supplier");
		addSupplier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSupplierFrame();
			}

		});
		addButtons.add(addSupplier);

		addIngredient = new JButton("Add New Ingredient");
		addIngredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddIngredientFrame();
			}

		});
		addButtons.add(addIngredient);

		addDish = new JButton("Add New Dish");
		addDish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddDishFrame();
			}

		});
		addButtons.add(addDish);

		changeRestockingLevel = new JButton("Change restocking levels");
		changeRestockingLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ChangeRestockingLevelFrame();
			}

		});
		
		others.add(changeRestockingLevel);

		addButtons.add(addKitchenStaff);
		addKitchenStaff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new KitchenStaff()).start();
			}

		});
		
		addButtons.add(addDrone);
		addDrone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ba.getDroneManager().addDrone();
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
						e.printStackTrace();
					}
				}
			}

		});
		t.start();
		
		saveButton = new JButton("Save data to file");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				DataPersistence.getInstance().saveAll("save.dat");
				
			}
	
		});
		
		others.add(saveButton);
		
		this.add(addButtons,BorderLayout.NORTH);
		this.add(others,BorderLayout.SOUTH);
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
		if (orderTableModel.hasUpdate()) {
			orderTableModel.fireTableDataChanged();
			orderTableModel.setUpdated();
		}
		if (droneTableModel.hasUpdate()) {
			droneTableModel.fireTableDataChanged();
			droneTableModel.setUpdated();
		}

	}

}
