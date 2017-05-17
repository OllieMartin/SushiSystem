package comms;

public class RegistrationSuccessMessage extends Message {

	private static final long serialVersionUID = 1L;

	public RegistrationSuccessMessage() {
		super(MessageType.REGISTRATION_SUCCESS);
	}
	
}
