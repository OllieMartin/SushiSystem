package business;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class AddDishFrame extends JFrame { //TODO VALIDATION ON ALL ADD FRAME
	//TODO MAKE ALL ADD FRAMES ALSO EDITABLE 
	private static final long serialVersionUID = 1L;

	JTextField nameTextbox;
	JTextField descTextbox;
	JTextField priceTextbox;
	JTextField restockingTextbox;
	JButton addButton;
	JList<String> ingredientsList;
	DefaultListModel<String> ingredientModel;
	private JPanel ingredientSelector;
	private JComboBox<Ingredient> ingredientComboBox;
	private JSpinner quantity;
	private Set<RecipeIngredient> ingredients;
	private JButton addIngredient;

	public AddDishFrame() {

		super("Add New Dish");

		this.setMinimumSize(new Dimension(400,600));
		this.setMaximumSize(new Dimension(400,600));

		nameTextbox = new JTextField();
		nameTextbox.setBorder(BorderFactory.createTitledBorder("Dish Name"));

		descTextbox = new JTextField();
		descTextbox.setBorder(BorderFactory.createTitledBorder("Dish description"));

		priceTextbox = new JTextField();
		priceTextbox.setBorder(BorderFactory.createTitledBorder("Dish price (£)"));

		restockingTextbox = new JTextField();
		restockingTextbox.setBorder(BorderFactory.createTitledBorder("Restocking level"));

		ingredientSelector = new JPanel();
		ingredientSelector.setLayout(new GridLayout(1,2));
		ingredientComboBox = new JComboBox<Ingredient>();
		for (StockedIngredient i : StockedIngredient.getStockedIngredients()) {
			ingredientComboBox.addItem(i.getIngredient()); //TODO Concurrent modification?!
		}
		quantity = new JSpinner();
		quantity.setValue(1);
		ingredientSelector.add(ingredientComboBox);
		ingredientSelector.add(quantity);

		ingredients = new HashSet<RecipeIngredient>();
		addIngredient = new JButton("Add Ingredient");


		ingredientModel = new DefaultListModel<String>();
		ingredientsList = new JList<String>(ingredientModel);

		addIngredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ingredientModel.addElement(((Ingredient)ingredientComboBox.getSelectedItem()).getName() + " : " + quantity.getValue());
				ingredients.add(new RecipeIngredient((Ingredient)ingredientComboBox.getSelectedItem(),(int)quantity.getValue()));
				ingredientComboBox.removeItem(ingredientComboBox.getSelectedItem());
				if (ingredientComboBox.getItemCount() == 0) {
					ingredientComboBox.setEnabled(false);
					addIngredient.setEnabled(false);
					quantity.setEnabled(false);
				}
			}

		});

		addButton = new JButton("Add");

		this.setLayout(new GridLayout(8,1));

		this.add(nameTextbox);
		this.add(descTextbox);
		this.add(priceTextbox);
		this.add(restockingTextbox);
		this.add(ingredientSelector);
		this.add(addIngredient);
		this.add(new JScrollPane(ingredientsList));
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Dish d = new Dish(nameTextbox.getText(), descTextbox.getText(), Float.parseFloat(priceTextbox.getText()),ingredients);
				new StockedDish(d, Integer.parseInt(restockingTextbox.getText()));
				dispose();
			}

		});

		this.setVisible(true);

	}

}
