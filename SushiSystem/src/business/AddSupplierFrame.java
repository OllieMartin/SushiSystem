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

public class AddSupplierFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField nameTextbox;
	JLabel nameLabel;
	JTextField distanceTextbox;
	JLabel distanceLabel;
	JButton addButton;

	public AddSupplierFrame() {

		super("Add New Supplier");

		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));

		nameLabel = new JLabel("Supplier Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextbox = new JTextField();

		distanceLabel = new JLabel("Distance From Company");
		distanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		distanceTextbox = new JTextField();

		addButton = new JButton("Add");

		this.setLayout(new GridLayout(5,1));
		this.add(nameLabel);
		this.add(nameTextbox);
		this.add(distanceLabel);
		this.add(distanceTextbox);
		this.add(addButton);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Supplier(nameTextbox.getText(), Float.parseFloat(distanceTextbox.getText()));
				dispose();
			}

		});

		this.setVisible(true);

	}

}
