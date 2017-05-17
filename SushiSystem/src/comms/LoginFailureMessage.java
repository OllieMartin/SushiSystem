package comms;

public class LoginFailureMessage extends Message {
	
	private static final long serialVersionUID = 1L;

	public LoginFailureMessage() {
		super(MessageType.LOGIN_FAILURE);
	}

}
