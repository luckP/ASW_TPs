package rsa.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rsa.shared.Car;
import rsa.shared.PreferredMatch;
import rsa.shared.RideRole;
import rsa.shared.UserStars;

public class User extends java.lang.Object implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private String nick, name, password;
	private Map<String, Car> cars;
	private PreferredMatch preferredMatch;
	
	@SuppressWarnings("unchecked")
	private LinkedList<UserStars>[] userStars = (LinkedList<UserStars>[]) new LinkedList[2];

	public User(String nick, String name, String password) {

		this.nick = nick;
		this.name = name;
		this.password = password;
		
		this.cars = new HashMap<String, Car>();
		userStars[0] = new LinkedList<UserStars>();
		userStars[1] = new LinkedList<UserStars>();
	}

//	Bind a car to this user.
	public void addCar(Car car) {
		this.cars.put(car.getPlate(), car); 
	}

//	Add stars to user according to a role.
	void addStars(UserStars moreStars, RideRole role) {
		this.userStars[(role == RideRole.DRIVER)?0:1].add(moreStars);
	}

//	Check the authentication of this player
	boolean authenticate(String password) {
		return (this.password.equals(password));
	}

//	Remove binding between use and car
	void deleteCar(String plate) {
		this.cars.remove(plate);
	}

//	Returns the average number of stars in given role
	public float getAverage(RideRole role) {
		
		
		LinkedList<UserStars> us =  this.userStars[(role == RideRole.DRIVER)?0:1];
		if(us.size() == 0) {
			return 0;
		}
	    float avg = 0;
	    for(UserStars s: us){
	        avg += s.getStars();
	    }
	    return avg/us.size(); 
	}

//	Car with given license plate
	Car getCar(String plate) {
		return this.cars.get(plate);
	}

//	Name of user
	public String getName() {
		return this.name;
	}

//	The nick of this user: Cannot be changed as it a key.
//	É PRA SER PRIVATE OU PUBLIC?
	String getNick() {
		return this.nick;
	}

//	Current password of this user
	String getPassword() {
		return this.password;
	}

//	Current preference for sorting matches.
	PreferredMatch getPreferredMatch() {
		if(this.preferredMatch == null)
			return PreferredMatch.BETTER;
		return this.preferredMatch;
	}

//	Change user's name
	void setName(String name) {
		this.name = name;
	}

//	Change password of this user
	void setPassword(String password) {
		this.password = password;
	}

//	Change preference for sorting matches
	void setPreferredMatch(PreferredMatch preferredMatch) {
		this.preferredMatch = preferredMatch;
	}

	@Override
	public String toString() {
		return "User [nick=" + nick + ", name=" + name + ", password=" + password + ", cars=" + cars
				+ ", preferredMatch=" + preferredMatch + ", userStars=" + Arrays.toString(userStars) + "]";
	}

	
	
	

}
