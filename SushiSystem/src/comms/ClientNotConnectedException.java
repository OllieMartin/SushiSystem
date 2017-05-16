package comms;

public class ClientNotConnectedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "The client is not connected to the server so was unable to listen for communications";
	}

}
