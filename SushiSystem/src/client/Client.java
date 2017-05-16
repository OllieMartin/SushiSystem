
package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import comms.ClientComms;
import comms.LoginMessage;

//http://www.mediafire.com/file/9gn4vsbqpdwfmol/TeaParty.jar

public class Client extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ClientComms comms;
	public JTextField textField = new JTextField(40);
	protected JTextPane messageArea = new JTextPane();
	public StyledDocument doc = messageArea.getStyledDocument();

	public Client() {
		
		super("Sushi System Client");
		System.out.println("!");
		comms = new ClientComms(this);
		System.out.println("!");
		if (!comms.establishConnection()) {
			System.out.println("ERROR CONNECTING!");
		}
		comms.sendMessage(new LoginMessage("Oliver","Revilo"));
		System.out.println("!");
		// Layout GUI
		textField.setEditable(false);
		textField.setMaximumSize(
				new Dimension(Integer.MAX_VALUE,
						textField.getPreferredSize().height));
		//textField.setMaximumSize( textField.getPreferredSize() );
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(textField);
		box.add(Box.createVerticalGlue());
		messageArea.setEditable(false);
		this.getContentPane().add(box, "Center");
		//messageArea.setBounds(0,0,400,400);
		JScrollPane p = new JScrollPane(messageArea);
		p.setPreferredSize(new Dimension(200,200));
		this.getContentPane().add(p, "South");
		this.pack();
		System.out.println("!");
		// Add Listeners

	}

	public static void main(String[] args) throws Exception {
		Client client = new Client(); //Create a new chat client
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When JFrame closed, close application
		client.setVisible(true); //Make the JFrame visible
	}

}

