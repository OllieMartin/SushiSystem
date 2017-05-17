package comms;

public class RegistrationFailureMessage extends Message {

	private static final long serialVersionUID = 1L;

	public RegistrationFailureMessage() {
		super(MessageType.REGISTRATION_FAILURE);
	}

}
