package comms;

public class LoginMessage extends Message {

	private String user;
	private String password; //TODO hash-code?
	
	public LoginMessage(String user, String password) {
		super(MessageType.LOGIN);
		this.user = user;
		this.password = password;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public boolean checkPassword(String password) {
		if (password.equals(this.password)) {
			return true;
		}
		return false;
	}

}
