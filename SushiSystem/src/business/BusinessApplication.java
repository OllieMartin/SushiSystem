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
	
	public static BusinessApplication getInstance() {
		return instance;
	}
	
	private static final int DEFAULT_WIDTH = 1000; // Default width for the main window pixels
	private static final int DEFAULT_HEIGHT = 500; // Default height for the main window pixels
	private static final int MINIMUM_WIDTH = 750; // Minimum width for the main window pixels
	private static final int MINIMUM_HEIGHT = 300; // Minimum height for the main window pixels
	private static BusinessApplication instance;
	
	private BusinessApplicationPane mainPane;
	private AccountManager am;
	private ServerComms sc;
	private OrderManager om;
	private DroneManager dm;
	
	public AccountManager getAccountManager() {
		return this.am;
	}
	
	public OrderManager getOrderManager() {
		return this.om;
	}
	
	public DroneManager getDroneManager() {
		return this.dm;
	}
	
	public BusinessApplication() {
		
		super("Sushi System - Business Application");

		instance = this;
		
		sc = new ServerComms(this);
		new Thread(sc).start();
		am = new AccountManager();
		dm = new DroneManager(am);
		om = new OrderManager(dm);

		
		mainPane = new BusinessApplicationPane(this);
		this.add(mainPane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

		this.setVisible(true);
		
	}
	
}
