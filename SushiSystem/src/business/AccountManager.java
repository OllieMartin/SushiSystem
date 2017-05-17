package business;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {

	private Map<String,UserAccount> registeredUsers;
	
	public AccountManager() {
		
		this.registeredUsers = new HashMap<String, UserAccount>();
		
	}
	
	public boolean existsUser(String user) {
		if (registeredUsers.containsKey(user)) {
			return true;
		} else {
			return false;
		}
	}
	//TODO THREADSAFETY!
	public UserAccount getUser(String user) {
		if (existsUser(user)) {
			return registeredUsers.get(user);
		} else {
			return null;
		}
	}
	
	public boolean verifyPassword(String user, String password) {
		if (existsUser(user)) {
			if (registeredUsers.get(user).checkPassword(password)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean registerUser(String user, String password, String address, String postcode) {
		if (password.length() <= 5) {
			return false;
		}
		if (user.length() == 0) {
			return false;
		}
		if (address.length() == 0) {
			return false;
		}
		if (postcode.length() == 0) {
			return false;
		}
		synchronized(registeredUsers) {
			if (existsUser(user)) {
				return false;
			}
			UserAccount newUser = new UserAccount(user,password,address,postcode);
			registeredUsers.put(user,newUser);
			return true;
		}
	}
	
}
