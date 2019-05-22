package rsa.service;

import java.io.File;
import java.util.LinkedList;

public class Users extends java.lang.Object implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private LinkedList<User> users;
	private static Users usersOBJ = null;
	private static final String filePath = "./users";

	private static File playersFile = new File(filePath);

	// Authenticate user given id and password
	boolean authenticate(String nick, String password) {
		for (User user : this.users) {
			if ((user.getNick().equals(nick)) && user.authenticate(password)) {
				return true;
			}
		}
		return false;
	}

	private Users() {

//		LOAD DATA
//		loadUserData();
//		this.loadUserData();
		this.users = new LinkedList<User>();
		
	}

	// Returns the single instance of this class as proposed in the singleton design
	// pattern.
	static Users getInstance() {

		if (usersOBJ == null)
			usersOBJ = new Users();
		return usersOBJ;
	}

	// Name of file containing managers's data
	static File getPlayersFile() {
		return playersFile;
	}

	// Change pathname of file containing mnanager's data
	static void setPlayersFile(File managerFile) {
		Users.playersFile = managerFile;
	}

	// Get the user with given nick
	public User getUser(String nick) {
		for(User user: users) {
			if(user.getNick().equals(nick))
				return user;
		}
		return null;
	}

	// Register a player with given nick and password.
	boolean register(String nick, String name, String password) {
		if(this.getUser(nick)!=null) {
			
//			System.out.println(nick);
//			System.out.println(this.users.toString());
			return false;
		}
		
		if(nick.contains(" ")) {
			return false;
		}
		try {
			User user = new User(nick, name, password);
			users.add(user);
			updateUserData();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// Resets singleton for unit testing purposes.
	void reset() {
		usersOBJ = new Users();
	}

	// Change password if old password matches current one.
	boolean updatePassword(String nick, String oldPassword, String newPassword) {
		User user = this.getUser(nick);
		if (user != null) {
			user.setPassword(newPassword);
			updateUserData();
			return true;
		}
		return false;
	}

//	Update data file
	private void updateUserData() {

	}
	
	

//	private void loadUserData() {
//		BufferedReader reader = null;
//
//		try {
//			reader = new BufferedReader(new FileReader(playersFile));
//			String text = null;
//
//			while ((text = reader.readLine()) != null) {
////				load data into a list
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (reader != null) {
//					reader.close();
//				}
//			} catch (IOException e) {
//			}
//		}
//
//	}

	@Override
	public String toString() {
		return "Users [users=" + users + "]";
	}

}
