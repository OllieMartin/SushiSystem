package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField user;
	private JPasswordField password;
	private JButton login;
	private Client client;
	private JButton register;

	public LoginFrame (Client c) {
		
		this.client = c;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		user = new JTextField();
		user.setBorder(BorderFactory.createTitledBorder("Enter username"));
		password = new JPasswordField();
		password.setBorder(BorderFactory.createTitledBorder("Enter password"));
		login = new JButton("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				client.attemptLogin(user.getText(),password.getPassword());
				setVisible(false);
				//dispose();
				
				
			}
			
		});
		register = new JButton("Register a new account");
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.requestRegister();
				setVisible(false);
			}
			
		});
		this.setLayout(new GridLayout(4,1));
		this.add(user);
		this.add(password);
		this.add(login);
		this.add(register);
		this.setMinimumSize(new Dimension(300,200));
		this.setPreferredSize(new Dimension(300,200));
		this.setVisible(true);
		
	}
	
}
