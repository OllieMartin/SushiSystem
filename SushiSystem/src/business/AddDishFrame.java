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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddDishFrame extends JFrame { //TODO VALIDATION ON ALL ADD FRAME
	//TODO MAKE ALL ADD FRAMES ALSO EDITABLE 
	private static final long serialVersionUID = 1L;

	private JTextField nameTextbox;
	private JTextField descTextbox;
	private JTextField priceTextbox;
	private JTextField restockingTextbox;

	private JPanel ingredientSelector;
	private JList<String> ingredientsList;
	private DefaultListModel<String> ingredientModel;
	private JComboBox<Ingredient> ingredientComboBox;
	private JSpinner quantity;
	private Set<RecipeIngredient> ingredients;
	private JLabel ingredientLabel;
	private JLabel quantityLabel;
	private JLabel ingredientListLabel;

	private JButton addIngredient;
	private JButton addButton;

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
		ingredientSelector.setLayout(new GridLayout(2,2));
		ingredientComboBox = new JComboBox<Ingredient>();
		for (StockedIngredient i : StockedIngredient.getStockedIngredients()) {
			ingredientComboBox.addItem(i.getIngredient()); //TODO Concurrent modification?!
		}
		quantity = new JSpinner();
		quantity.setValue(1);

		ingredients = new HashSet<RecipeIngredient>();
		addIngredient = new JButton("Add Ingredient");

		if (ingredientComboBox.getItemCount() == 0) {
			ingredientComboBox.setEnabled(false);
			quantity.setEnabled(false);
			addIngredient.setEnabled(false);
		}
		ingredientLabel = new JLabel("Add ingredients",SwingConstants.CENTER);
		quantityLabel = new JLabel("Quantity",SwingConstants.CENTER);
		ingredientSelector.add(ingredientLabel);
		ingredientSelector.add(quantityLabel);
		ingredientSelector.add(ingredientComboBox);
		ingredientSelector.add(quantity);



		ingredientListLabel = new JLabel("Added ingredients:",SwingConstants.CENTER);

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

		this.setLayout(new GridLayout(9,1));

		this.add(nameTextbox);
		this.add(descTextbox);
		this.add(priceTextbox);
		this.add(restockingTextbox);
		this.add(ingredientSelector);
		this.add(addIngredient);
		this.add(ingredientListLabel);
		this.add(new JScrollPane(ingredientsList));
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (Integer.parseInt(restockingTextbox.getText()) <= 0) {
						throw new NumberFormatException();
					}
					if (Float.parseFloat(priceTextbox.getText()) < 0) {
						throw new NumberFormatException();
					}
					Dish d = new Dish(nameTextbox.getText(), descTextbox.getText(), Float.parseFloat(priceTextbox.getText()),ingredients);
					new StockedDish(d, Integer.parseInt(restockingTextbox.getText()));
					dispose();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Please sure restocking level is an integer larger than 0 and price is a valid positive number", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});

		this.setVisible(true);

	}

}
