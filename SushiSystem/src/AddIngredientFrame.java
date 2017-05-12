import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddIngredientFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField nameTextbox;
	JLabel nameLabel;
	JTextField unitTextbox;
	JLabel unitLabel;
	JComboBox<Supplier> supplierComboBox;
	JTextField restockingTextbox;
	JLabel restockingLabel;
	JButton addButton;

	public AddIngredientFrame() {

		super("Add New Ingredient");

		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		nameLabel = new JLabel("Ingredient Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextbox = new JTextField();

		unitLabel = new JLabel("Ingredient Unit");
		unitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unitTextbox = new JTextField();

		supplierComboBox = new JComboBox<Supplier>();
		//TODO THREAD SAFETY
		for (Supplier s : Supplier.getSuppliers()) {
			supplierComboBox.addItem(s);
		}

		restockingLabel = new JLabel("Restocking Level");
		restockingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		restockingTextbox = new JTextField();

		addButton = new JButton("Add");

		this.setLayout(new GridLayout(8,1));
		this.add(nameLabel);
		this.add(nameTextbox);
		this.add(unitLabel);
		this.add(unitTextbox);
		this.add(supplierComboBox);
		this.add(restockingLabel);
		this.add(restockingTextbox);
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO VALIDATION
				Ingredient i = new Ingredient(nameTextbox.getText(), unitTextbox.getText(), (Supplier)supplierComboBox.getSelectedItem());
				new StockedIngredient(i, Integer.parseInt(restockingTextbox.getText()));
				dispose();
			}

		});

		this.setVisible(true);

	}

}
