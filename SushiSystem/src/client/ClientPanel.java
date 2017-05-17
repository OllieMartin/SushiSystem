package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ClientPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Client client;
	private JTabbedPane tabs;
	private JTable menu;
	private JButton placeOrder;
	private JPanel menuPanel;
	
	public ClientPanel(Client client) {
		
		this.client = client;
		this.setLayout(new BorderLayout());
		this.menu = new JTable(this.client.getTableModel());
		this.placeOrder = new JButton("Place Order");
		this.tabs = new JTabbedPane();
		this.add(tabs,BorderLayout.CENTER);
		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.add(new JScrollPane(menu), BorderLayout.CENTER);
		placeOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				new OrderFrame(client);
				
			}
		
		});
		menuPanel.add(placeOrder,BorderLayout.SOUTH);
		tabs.addTab("Menu", menuPanel);
		tabs.addTab("Order History",null);
		
	}
	
}
