package comms;

public class LoginRequestMessage extends Message {

	private static final long serialVersionUID = 1L;

	public LoginRequestMessage() {
		super(MessageType.LOGIN_REQUEST);
	}

}
