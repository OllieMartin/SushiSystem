package comms;

public class RegistrationMessage extends Message {

	private static final long serialVersionUID = 1L;
	
	private String user;
	private String password;
	private String address;
	private String postcode;
	
	public RegistrationMessage(String user, String password, String address, String postcode) {
		super(MessageType.REGISTRATION);
		this.user =  user;
		this.password = password;
		this.address = address;
		this.postcode = postcode;
	}

	public String getUser() {
		return this.user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getPostcode() {
		return this.postcode;
	}
	
}
