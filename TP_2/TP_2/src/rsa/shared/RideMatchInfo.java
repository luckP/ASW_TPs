package rsa.shared;

import rsa.service.Matcher;

public class RideMatchInfo extends java.lang.Object {
	
	private Matcher.RideMatch match;
	public RideMatchInfo(Matcher.RideMatch match) {
		this.match = match;
	}

//	Car used in ride
	public Car getCar() {
		return match.getCar();
	}

//	Cost of this ride, payed by the passenger to the driver
	public float getCost() {
		return match.getRide(RideRole.DRIVER).getCost();
	}

//	Id of match
	public long getMatchId() {
		return match.getId();
	}

//	Get name of user with given role
	public String getName(RideRole role) {
		return match.getRide(role).getUser().getName();
	}

//	Get average number of stars of user with given role
	public float getStars(RideRole role) {
		return match.getRide(role).getUser().getAverage(role);
	}

//	The location of a user with given role
	public Location getWhere(RideRole role) {
//		System.out.println( (match.getRide(role).getCurrent()!=null)?"ok": "error" );
//		return match.getRide(role).getCurrent();
		
		return new Location(0,0);
	}

}
