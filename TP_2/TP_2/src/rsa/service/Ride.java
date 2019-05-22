package rsa.service;

import java.util.Comparator;

import rsa.quad.HasPoint;
import rsa.shared.Location;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;

public class Ride extends Object implements HasPoint, RideMatchInfoSorter {

	private User user;
	private Location from;
	private Location to;
	private String plate;
	private float cost;

	private Matcher.RideMatch match;
	private Location current;
	private long id;
	
	static long lastId = 1; 

	public Ride(User user, Location from, Location to, String plate, float cost) {
		this.user = user;
		this.from = from;
		this.to = to;
		this.plate = plate;
		this.cost = cost;
		this.current = from;
		this.id = lastId++;
	}

	// Get a comparator of RideMatchInfo instances for the given ride.
	@Override
	public Comparator<RideMatchInfo> getComparator() {
		return new Comparator<RideMatchInfo>() {

			@Override
			public int compare(RideMatchInfo o1, RideMatchInfo o2) {
				// TODO Auto-generated method stub
				return 0;
			}
			
	       
	    };
	}

	// User of this ride
	public User getUser() {
		return user;
	}

	// Get the origin of this ride
	public Location getFrom() {
		return from;
	}

	// Get destination of this ride
	public Location getTo() {
		return to;
	}

	// Car's registration plate for this ride
	public String getPlate() {
		return plate;
	}

// Cost of this ride (only meaningful for for driver)
//	@Override
	public float getCost() {
		return cost;
	}

	// Current location
	public Location getCurrent() {
		return this.current;
	}

	// Generated unique identifier of this ride.
	long getId() {
		return this.id;
	}

	// Current match of this ride
	Matcher.RideMatch getMatch() {
		return this.match;
	}

	// Role of user in ride, depending on a car's license plate being registered
	RideRole getRideRole() {
		return (this.plate != null) ? RideRole.DRIVER : RideRole.PASSENGER;
	}

	@Override
	public double getX() {
		return current.getX();
	}

	@Override
	public double getY() {
		return this.current.getY();
	}

	// Is the user the driver in this ride?
	boolean isDriver() {
		return getRideRole().equals(RideRole.DRIVER);
	}

	// This ride was match with another
	boolean isMatched() {
		return (this.match!=null);
	}

	// Is the user the passenger in this ride?
	boolean isPassenger() {
		return getRideRole().equals(RideRole.PASSENGER);
	}

	// Change cost of this ride (only meaningful for for driver)
	void setCost(float cost) {
		this.cost = cost;
	}

	// Change current location
	void setCurrent(Location current) {
		this.current = current;
	}

	// Change the origin of this ride
	void setFrom(Location from) {
		this.from = from;
	}

	// Assign a match to this ride
	void setMatch(Matcher.RideMatch match) {
		this.match = match;
	}

	// Change car registration plate for this ride
	void setPlate(String plate) {
		this.plate = plate;
	}

	// Change destination of this ride
	void setTo(Location to) {
		this.to = to;
	}

	// Change user of this ride
	void setUser(User user) {
		this.user = user;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ride other = (Ride) obj;
		if (Float.floatToIntBits(cost) != Float.floatToIntBits(other.cost))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id != other.id)
			return false;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		if (plate == null) {
			if (other.plate != null)
				return false;
		} else if (!plate.equals(other.plate))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
