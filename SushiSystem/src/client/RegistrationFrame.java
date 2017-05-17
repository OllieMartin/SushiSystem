package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrationFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String[] POSTCODES = {"SO162HA","SO162BQ","SO162BW","SO163RT","SO152HT","SO163ST"};

	private JTextField user;
	private JPasswordField password;
	private JPasswordField confirmPassword;
	private JTextField address;
	private JComboBox<String> postcode;
	private JButton register;
	private Client client;
	private boolean passwordsMatch;
	private JLabel matchStatus;
	private JLabel validStatus;
	private boolean passwordValid;

	public RegistrationFrame(Client c) {

		super("Registration - You must fill in ALL fields");
		this.client = c;
		user = new JTextField();
		user.setBorder(BorderFactory.createTitledBorder("Enter username"));
		user.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				attemptEnableButton();
			}
		});
		password = new JPasswordField();
		password.setBorder(BorderFactory.createTitledBorder("Enter password"));
		password.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (password.getPassword().length > 5) {
					passwordValid = true;
					validStatus.setForeground(Color.GREEN);
					validStatus.setText("Password valid");
					attemptEnableButton();
				} else {
					passwordValid = false;
					validStatus.setForeground(Color.RED);
					validStatus.setText("Password must be longer than 5 characters");
					attemptEnableButton();
				}
					if (Arrays.equals(password.getPassword(), confirmPassword.getPassword())) {
						passwordsMatch = true;
						matchStatus.setForeground(Color.GREEN);
						matchStatus.setText("Passwords Match");
						attemptEnableButton();
					} else {
						passwordsMatch = false;
						matchStatus.setForeground(Color.RED);
						matchStatus.setText("Passwords Different");
						attemptEnableButton();
					}
			}



		});
		validStatus = new JLabel();
		confirmPassword = new JPasswordField();
		confirmPassword.setBorder(BorderFactory.createTitledBorder("Confirm password"));
		matchStatus = new JLabel();
		passwordsMatch = false;
		passwordValid = false;
		confirmPassword.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (Arrays.equals(password.getPassword(), confirmPassword.getPassword())) {
					passwordsMatch = true;
					matchStatus.setForeground(Color.GREEN);
					matchStatus.setText("Passwords Match");
					attemptEnableButton();
				} else {
					passwordsMatch = false;
					matchStatus.setForeground(Color.RED);
					matchStatus.setText("Passwords Different");
					attemptEnableButton();
				}
			}
		});

		address = new JTextField();
		address.setBorder(BorderFactory.createTitledBorder("Enter address"));
		address.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				attemptEnableButton();
			}
		});

		postcode = new JComboBox<String>(POSTCODES);
		postcode.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				attemptEnableButton();
			}

		});

		register = new JButton("Register");
		register.setEnabled(false);
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (passwordsMatch && passwordValid && !address.getText().isEmpty() && !user.getText().isEmpty() && postcode.getSelectedItem() != null) {
					client.attemptRegister(user.getText(),password.getPassword(),address.getText(),(String)postcode.getSelectedItem());
					setVisible(false);
					//dispose();
				}

			}

		});
		this.setLayout(new GridLayout(8,1));
		this.add(user);
		this.add(password);
		this.add(validStatus);
		this.add(confirmPassword);
		this.add(matchStatus);
		this.add(address);
		this.add(postcode);
		this.add(register);
		this.setMinimumSize(new Dimension(300,500));
		this.setPreferredSize(new Dimension(300,500));
		this.setVisible(true);

	}

	private void attemptEnableButton() {

		if (passwordValid && passwordsMatch && !address.getText().isEmpty() && !user.getText().isEmpty() && postcode.getSelectedItem() != null) {
			register.setEnabled(true);
		} else {
			register.setEnabled(false);
		}

	}

}
