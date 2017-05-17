package business;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddDishFrame extends JFrame { //TODO VALIDATION ON ALL ADD FRAME
//TODO MAKE ALL ADD FRAMES ALSO EDITABLE 
	private static final long serialVersionUID = 1L;

	JTextField nameTextbox;
	JLabel nameLabel;
	JTextField descTextbox;
	JLabel descLabel;
	JTextField priceTextbox;
	JLabel priceLabel;
	JTextField restockingTextbox;
	JLabel restockingLabel;
	JButton addButton;

	public AddDishFrame() {

		super("Add New Dish");

		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		nameLabel = new JLabel("Dish Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextbox = new JTextField();

		descLabel =  new JLabel("Dish Description");
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descTextbox = new JTextField();

		priceLabel = new JLabel("Dish Price");
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceTextbox = new JTextField();

		restockingLabel = new JLabel("Restocking Level");
		restockingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		restockingTextbox = new JTextField();

		addButton = new JButton("Add");

		this.setLayout(new GridLayout(9,1));

		this.add(nameLabel);
		this.add(nameTextbox);
		this.add(descLabel);
		this.add(descTextbox);
		this.add(priceLabel);
		this.add(priceTextbox);
		this.add(restockingLabel);
		this.add(restockingTextbox);
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Dish d = new Dish(nameTextbox.getText(), descTextbox.getText(), Float.parseFloat(priceTextbox.getText()));
				new StockedDish(d, Integer.parseInt(restockingTextbox.getText()));
				dispose();
			}

		});

		this.setVisible(true);

	}

}
