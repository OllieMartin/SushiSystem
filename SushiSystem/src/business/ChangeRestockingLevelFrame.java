package business;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ChangeRestockingLevelFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JComboBox<ProductType> optionComboBox;
	JLabel optionLabel;
	JComboBox<StockedProduct> productComboBox;
	JLabel productLabel;
	JTextField restockingLevelTextbox;
	JLabel restockingLevelLabel;
	JButton saveButton;

	public ChangeRestockingLevelFrame() {

		super("Change Restocking Level");

		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		optionLabel = new JLabel("Select type of product to change restocking level");
		optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionComboBox = new JComboBox<ProductType>();
		optionComboBox.addItem(ProductType.DISH);
		optionComboBox.addItem(ProductType.INGREDIENT);
		optionComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				productComboBox.removeAllItems();
				if (optionComboBox.getSelectedItem().equals(ProductType.INGREDIENT)) {
					synchronized (StockedIngredient.getStockedIngredients()) {
						for (StockedIngredient i : StockedIngredient.getStockedIngredients()) {

							productComboBox.addItem(i);

						}
					}
				} else {
					synchronized (StockedDish.getStockedDishes()) {
						for (StockedDish d : StockedDish.getStockedDishes()) {

							productComboBox.addItem(d);

						}
					}
				}

				productComboBox.setEnabled(true);
				restockingLevelTextbox.setEnabled(false);
				saveButton.setEnabled(false);

			}

		});

		productLabel = new JLabel("Select product to change restocking level of");
		productLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productComboBox = new JComboBox<StockedProduct>();
		productComboBox.setEnabled(false);
		productComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				restockingLevelTextbox.setText(Integer.toString(((StockedProduct)productComboBox.getSelectedItem()).getRestockingLevel()));
				restockingLevelTextbox.setEnabled(true);
				saveButton.setEnabled(true);

			}

		});

		restockingLevelLabel = new JLabel("Enter restocking level");
		restockingLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		restockingLevelTextbox = new JTextField();
		restockingLevelTextbox.setEnabled(false);

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);

		this.setLayout(new GridLayout(7,1));

		this.add(optionLabel);
		this.add(optionComboBox);
		this.add(productLabel);
		this.add(productComboBox);
		this.add(restockingLevelLabel);
		this.add(restockingLevelTextbox);
		this.add(saveButton);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (Integer.parseInt(restockingLevelTextbox.getText()) <= 0) {
						throw new NumberFormatException();
					}

					((StockedProduct)productComboBox.getSelectedItem()).setRestockingLevel(Integer.parseInt(restockingLevelTextbox.getText()));
					dispose();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Please sure restocking level is an integer larger than 0", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});

		this.setVisible(true);

	}

}
