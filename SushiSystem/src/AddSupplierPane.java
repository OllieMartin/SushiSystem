import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddSupplierPane extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField name;
	JTextField distance;
	JButton add;
	AddIngredientPane i;
	
	public AddSupplierPane(AddIngredientPane i) {
		this.i = i;
		name = new JTextField();
		distance = new JTextField();
		add = new JButton("Add");
		
		this.setLayout(new GridLayout(3,1));
		this.add(name);
		this.add(distance);
		this.add(add);
		
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Supplier s = new Supplier(name.getText(), Float.parseFloat(distance.getText()));
				i.supplier.addItem(s);
			}
			
		});
		
	}
	
}
