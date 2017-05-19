package comms;

public class LoginSuccessMessage extends Message {

	private static final long serialVersionUID = 1L;

	public LoginSuccessMessage() {
		super(MessageType.LOGIN_SUCCESS);
	}

}
