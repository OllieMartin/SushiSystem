import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

public class AddIngredientPane extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField name;
	JTextField unit;
	public JComboBox<Supplier> supplier;
	JTextField restockingLevel;
	JButton add;
	
	public AddIngredientPane() {
		
		name = new JTextField();
		unit = new JTextField();
		supplier = new JComboBox<Supplier>();
		restockingLevel = new JTextField();
		add = new JButton("Add");
		
		this.setLayout(new GridLayout(5,1));
		this.add(name);
		this.add(unit);
		this.add(supplier);
		this.add(restockingLevel);
		this.add(add);
		
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Ingredient i = new Ingredient(name.getText(), unit.getText(), (Supplier)supplier.getSelectedItem());
				StockedIngredient si = new StockedIngredient(i, Integer.parseInt(restockingLevel.getText()));
			}
			
		});
		
	}
	
}
