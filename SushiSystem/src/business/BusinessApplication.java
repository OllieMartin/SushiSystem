package business;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import comms.ServerComms;

public class BusinessApplication extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() {  
				new BusinessApplication();
			}
		});
	}
	
	private static final int DEFAULT_WIDTH = 1000; // Default width for the main window pixels
	private static final int DEFAULT_HEIGHT = 500; // Default height for the main window pixels
	private static final int MINIMUM_WIDTH = 750; // Minimum width for the main window pixels
	private static final int MINIMUM_HEIGHT = 300; // Minimum height for the main window pixels
	
	private BusinessApplicationPane mainPane;
	private AccountManager am;
	private ServerComms sc;
	private OrderManager om;
	
	public AccountManager getAccountManager() {
		return this.am;
	}
	
	public OrderManager getOrderManager() {
		return this.om;
	}
	
	public BusinessApplication() {
		
		super("Sushi System - Business Application");

		sc = new ServerComms(this);
		new Thread(sc).start();
		am = new AccountManager();
		om = new OrderManager();
		
		mainPane = new BusinessApplicationPane();
		this.add(mainPane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

		this.setVisible(true);
		
	}
	
}
