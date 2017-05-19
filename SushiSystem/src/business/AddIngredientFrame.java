package business;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddIngredientFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField nameTextbox;
	private JTextField unitTextbox;
	private JComboBox<Supplier> supplierComboBox;
	private JTextField restockingTextbox;
	private JButton addButton;
	private JLabel supplierLabel;

	public AddIngredientFrame() {

		super("Add New Ingredient");

		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		nameTextbox = new JTextField();
		nameTextbox.setBorder(BorderFactory.createTitledBorder("Ingredient Name"));

		unitTextbox = new JTextField();
		unitTextbox.setBorder(BorderFactory.createTitledBorder("Units of measurement"));

		supplierLabel = new JLabel("Select supplier", SwingConstants.CENTER);

		supplierComboBox = new JComboBox<Supplier>();
		//TODO THREAD SAFETY
		for (Supplier s : Supplier.getSuppliers()) {
			supplierComboBox.addItem(s);
		}

		restockingTextbox = new JTextField();
		restockingTextbox.setBorder(BorderFactory.createTitledBorder("Restocking level"));

		addButton = new JButton("Add");

		this.setLayout(new GridLayout(6,1));
		this.add(nameTextbox);
		this.add(unitTextbox);
		this.add(supplierLabel);
		this.add(supplierComboBox);
		this.add(restockingTextbox);
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (supplierComboBox.getSelectedItem() != null) {
					try {
						if (Integer.parseInt(restockingTextbox.getText()) <= 0) {
							throw new NumberFormatException();
						}
						Ingredient i = new Ingredient(nameTextbox.getText(), unitTextbox.getText(), (Supplier)supplierComboBox.getSelectedItem());
						new StockedIngredient(i, Integer.parseInt(restockingTextbox.getText()));
						dispose();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Restocking level must be an integer greater than 0", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a valid supplier", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});

		this.setVisible(true);

	}

}
