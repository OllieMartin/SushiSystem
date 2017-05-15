package comms;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import client.Client;

public class ClientComms extends Comms implements Runnable {

	protected BufferedReader in;
	protected PrintWriter out;
	Client c;

	@Override
	public void sendMessage(String message) {
		out.println(message);

	}

	public ClientComms(Client c) {
		this.c = c;
	}

	@Override
	public void run() {
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.BLUE);
		StyleConstants.setBold(keyWord, true);

		String serverAddress = "localhost"; //getServerAddress();
		Socket socket;
		try {
			socket = new Socket(serverAddress, 25410);
		
		//Define the input and output streams
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Process all messages from server, according to the protocol.
		while (true) {
			String line;
			try {
				line = in.readLine();

				if (line.startsWith("ENTERNAME")) {
					out.println("testClient");
					//Send the result of the getName dialogue box
				} else if (line.startsWith("NAMEACCEPTED")) {
					c.textField.setEditable(true);
					//If the name is accepted then allow them to type a message
				} else if (line.startsWith("INFO")) {
					//messageArea.append("INFO: " + line.substring(5) + "\n");
					try {
						c.doc.insertString(c.doc.getLength(), "INFO: " + line.substring(5) + "\n", keyWord);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (line.startsWith("MESSAGE")) {
					//messageArea.append(line.substring(8) + "\n");
					try {
						c.doc.insertString(c.doc.getLength(), line.substring(8) + "\n", null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Toolkit.getDefaultToolkit().beep();
					//Add new message to the chat box
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //Create a new socket to the server

	}

}
