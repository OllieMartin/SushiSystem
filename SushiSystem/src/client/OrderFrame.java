package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import business.Dish;
import business.MenuDish;
import business.OrderDish;

public class OrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Client client;
	private JPanel dishSelector;
	private JComboBox<Dish> dishes;
	private JSpinner quantity;
	private List<OrderDish> order;
	private JButton go;
	private JButton addItem;
	
	public OrderFrame(Client client) {
		
		this.client = client;
		this.setLayout(new GridLayout(3,1));
		dishSelector = new JPanel();
		dishSelector.setLayout(new FlowLayout());
		dishes = new JComboBox<Dish>();
		for (MenuDish dish : this.client.getDishes()) {
			dishes.addItem(dish.getDish());
		}
		dishSelector.add(dishes);
		quantity = new JSpinner();
		quantity.setValue(1);
		dishSelector.add(quantity);
		this.add(dishSelector);
		order = new ArrayList<OrderDish>();
		addItem = new JButton();
		addItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				order.add(new OrderDish((Dish)dishes.getSelectedItem(),(int)quantity.getValue()));
				dishes.removeItem(dishes.getSelectedItem());
				
			}
			
		});
		this.add(addItem);
		
		go = new JButton("Place Order");
		go.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				client.placeOrder(order);
				setVisible(false);
				
			}
			
		});
		this.add(go);
		this.setMinimumSize(new Dimension(400,400));
		this.setPreferredSize(new Dimension(400,400));
		this.setVisible(true);
	}
	
}
