package business;

public class UserAccount {
	
	private String user;
	private String password;
	private String address;
	private String postcode;

	public UserAccount(String user, String password, String address, String postcode) {
		
		this.user = user;
		this.password = password;
		this.address = address;
		this.postcode = postcode;
		
	}
	
	public String getUser() {
		return this.user;
	}
	
	public boolean checkPassword(String password) {
		if (password.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getAddress() {
		return this.address;
	}
	public String getPostcode() {
		return this.postcode;
	}
}
