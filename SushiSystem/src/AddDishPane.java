import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddDishPane extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField name;
	JTextField desc;
	JTextField price;
	JTextField restockingLevel;
	JButton add;
	
	public AddDishPane() {
		
		name = new JTextField();
		desc = new JTextField();
		price = new JTextField();
		restockingLevel = new JTextField();
		add = new JButton("Add");
		
		this.setLayout(new GridLayout(5,1));
		this.add(name);
		this.add(desc);
		this.add(price);
		this.add(restockingLevel);
		this.add(add);
		
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Dish d = new Dish(name.getText(), desc.getText(), Float.parseFloat(price.getText()));
				StockedDish sd = new StockedDish(d, Integer.parseInt(restockingLevel.getText()));
				
			}
			
		});
		
	}
	
}
