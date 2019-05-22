package rsa.service;

import java.util.Set;

import rsa.shared.Location;
import rsa.shared.PreferredMatch;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;
import rsa.shared.RideSharingAppException;
import rsa.shared.UserStars;

public class Manager extends java.lang.Object {
	private Users allUsers;
	private Matcher matcher;
	private static Manager manager = null;

//	DESLOCAR POINTqUADtREE E CONSTANTES PARA O MACHER
	


	private Manager() {
//		pointQuadtree = new PointQuadtree<Location>(TOP_LEFT_X, TOP_LEFT_Y, BOTTOM_RIGHT_X, BOTTOM_RIGHT_Y);
		matcher = new Matcher();
		allUsers = Users.getInstance();
	}

//	Accept a match
	void acceptMatch(long rideId, long matchId) {
		matcher.acceptMatch(rideId, matchId);
	}

//	Add a ride for user with given nick, from and to the given locations.
	public long addRide(String nick, String password, Location from, Location to, String plate, float cost) throws RideSharingAppException {
		if(authenticate(nick, password))
			return matcher.addRide(allUsers.getUser(nick), from, to, plate, cost);
		else
			throw new RideSharingAppException("Error: authenticate");
	}

//	Authenticates user given nick and password
	public boolean authenticate(String nick, String password) {
		return this.allUsers.getUser(nick) != null && this.allUsers.getUser(nick).getPassword().equals(password);
	}

//	Conclude a ride and provide feedback on the other partner
	public void concludeRide(long rideId, UserStars classification) {
		matcher.concludeRide(rideId, classification);
	}

//	The average number of stars of given user in given role
	public double getAverage(String nick, RideRole role) throws RideSharingAppException{
		User user = this.allUsers.getUser(nick);
		if(user != null) {
			return user.getAverage(role);
		}
		throw new RideSharingAppException("Error: User does't exist");
	}

//	Returns the single instance of this class as proposed in the singleton design pattern.
	public static Manager getInstance() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

//	Current preferred match for given authenticated user
	public PreferredMatch getPreferredMatch(String nick, String password) throws  RideSharingAppException {
		if(authenticate(nick, password))
			return this.allUsers.getUser(nick).getPreferredMatch();
		throw new RideSharingAppException("Error: authenticate");
	}

//	Register a player with given nick and password.
	public boolean register(String nick, String name, String password) throws RideSharingAppException{
		if(this.allUsers.getUser(nick) == null && this.allUsers.getUser(nick).getPassword().equals(password))
			return this.allUsers.register(nick, name, password);
		throw new RideSharingAppException("Error: user already exists");
		
	}

//	Resets singleton for unit testing purposes.
	void reset() {
		manager = new Manager();
	}

//	Set preferred match for given authenticated user
	void setPreferredMatch(String nick, String password, PreferredMatch preferred) throws RideSharingAppException{
		if(authenticate(nick, password))
			this.allUsers.getUser(nick).setPreferredMatch(preferred);
		else
			throw new RideSharingAppException("Error: authenticate");
	}

//	Change password if old password matches the current one
	boolean updatePassword(String nick, String oldPassword, String newPassword) throws RideSharingAppException {
		return this.allUsers.updatePassword(nick, oldPassword, newPassword);
	}

//	Update current location of user and receive a set of proposed ride matches
	public Set<RideMatchInfo> updateRide(long rideId, Location current) throws RideSharingAppException{
		return matcher.updateRide(rideId, current);
	}

	@Override
	public String toString() {
		return "Manager [allUsers=" + allUsers + ", matcher=" + matcher + "]";
	}
	
	
}
