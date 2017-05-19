package business;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountManager {

	private Map<String,UserAccount> registeredUsers;
	private Set<UserAccount> online;
	private static AccountManager instance;

	public Map<String,UserAccount> getRegisteredUsers() {
		synchronized (this.registeredUsers) {
			return this.registeredUsers;
		}
	}

	public void loadRegisteredUsers(Map<String,UserAccount> registeredUsers) {
		synchronized (this.registeredUsers) {
			this.registeredUsers = registeredUsers;
		}
	}

	public static AccountManager getInstance() {
		return instance;
	}

	static {
		instance = new AccountManager();
	}

	private AccountManager() {

		this.registeredUsers = new HashMap<String, UserAccount>();
		this.online = new HashSet<UserAccount>();

	}

	public void logoutUser(String user) {
		synchronized (online) {
			if (existsUser(user)) {
				online.remove(registeredUsers.get(user));
			}
		}
	}

	public boolean loginUser(String user, String password) {
		synchronized (registeredUsers) {
			synchronized (online) {
				if (existsUser(user) && verifyPassword(user,password)) {
					online.add(registeredUsers.get(user));
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public boolean existsUser(String user) {
		synchronized (registeredUsers) {
			if (registeredUsers.containsKey(user)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public UserAccount getUser(String user) {
		synchronized (registeredUsers) {
			if (existsUser(user)) {
				return registeredUsers.get(user);
			} else {
				return null;
			}
		}
	}

	public boolean verifyPassword(String user, String password) {
		synchronized (registeredUsers) {
			if (existsUser(user)) {
				if (registeredUsers.get(user).checkPassword(password)) {
					return true;
				}
			}
			return false;
		}
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
