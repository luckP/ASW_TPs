package rsa.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import rsa.quad.PointOutOfBoundException;
import rsa.quad.PointQuadtree;
import rsa.shared.Car;
import rsa.shared.Location;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;
import rsa.shared.UserStars;

public class Matcher implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Long, RideMatch> matcheMap;
	private Map<Long, Ride> rideMap;
	private Map<Long, RideMatchInfo> rideMatchInfoMap;

	private static Location topLeft = new Location(-1000, 1000);
	private static Location bottomRight = new Location(1000, -1000);
	private static long lastIdRideMatch = 0;
	private static double radius;
	
	static PointQuadtree<Ride> pointQuadtree;

	public Matcher() {
		matcheMap = new HashMap<Long, RideMatch>();
		rideMap = new HashMap<Long, Ride>();
		this.rideMatchInfoMap = new HashMap<Long, RideMatchInfo>();
		pointQuadtree = new PointQuadtree<>(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());

	}

	// Accept the proposed match (identified by matchId) for given ride (identified
	// by rideId)
	void acceptMatch(long rideId, long matchId) {
		Ride ride = rideMap.get(rideId);
		Matcher.RideMatch rideMatch = this.matcheMap.get(matchId);
		if( rideMatch != null) {
			ride.setMatch(rideMatch);
		}
	}

	// Add a ride to the matcher
	long addRide(User user, Location from, Location to, String plate, float cost) {

		Ride ride = new Ride(user, from, to, plate, cost);
		rideMap.put(ride.getId(), ride);
		
		try {
			pointQuadtree.insert(ride);
		}catch(PointOutOfBoundException e) {
			e.printStackTrace();
		}
		return ride.getId();
	}

	// Mark ride as concluded and classify other using stars
	void concludeRide(long rideId, UserStars stars) {
//		rideMap.get(rideId).getUser().addStars(stars, rideMap.get(rideId).getRideRole().other());
		Ride ride = rideMap.get(rideId);
		RideRole rideRole = ride.getRideRole().other();
		RideMatch rideMatch = ride.getMatch();
		
		if(rideMatch != null) {
			Ride pairedRide = rideMatch.getRide(rideRole);
			User user = pairedRide.getUser();
			user.addStars(stars, rideRole);
			matcheMap.remove(rideId);
			matcheMap.remove(pairedRide.getId());
		}
		
	}

	// Location of bottom right corner of matching region
	static Location getBottomRight() {
		return bottomRight;
	}

	// Maximum distance between two users eligible for a match
	static double getRadius() {
		return radius;
	}

	// Location of top left corner of matching region
	static Location getTopLeft() {
		return topLeft;
	}

	// Change location of bottom right corner of matching region
	static void setBottomRight(Location bottomRight) {
		Matcher.bottomRight = bottomRight;
		resetQuadTree();
	}

	// Set distance to consider a match
	static void setRadius(double radius) {
		Matcher.radius = radius;
	}

	// Change location of top left corner of matching region
	static void setTopLeft(Location topLeft) {
		Matcher.topLeft = topLeft;
		resetQuadTree();
	}
	
	private static void resetQuadTree() {
		if(Matcher.topLeft!=null && Matcher.bottomRight!=null)
			pointQuadtree = new PointQuadtree<>(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
	}

	// Update current location of ride with given id.
	public SortedSet<RideMatchInfo> updateRide(long rideId, Location current){
		Ride ride = rideMap.get(rideId);
		ride.setCurrent(current);
		if (!ride.isMatched()) {
//			SortedSet<RideMatchInfo> set = new TreeSet<RideMatchInfo>(ride.getComparator());
//			for (Ride aux : rideMap.values()) {
//				RideMatch rm = new RideMatch(ride,aux);
//				if (rm.matchable() == true) {
//					matcheMap.put(rm.getId(), rm);
//					RideMatchInfo rminfo = new RideMatchInfo(rm);
//					set.add(rminfo);
//				}
//			}
//			return set;
			
			
			
			Set<Ride> nearRides = pointQuadtree.findNear(current.getX(), current.getY(), radius);
			
			SortedSet<RideMatchInfo> sortedSet = new TreeSet<RideMatchInfo>(ride.getComparator());
			
			Iterator<Ride> iterator = nearRides.iterator();
			
			while(iterator.hasNext()) {
				Ride info = iterator.next();
				if(info.getRideRole() != ride.getRideRole() && info.getTo().equals(ride.getTo())) {
					RideMatch match = new RideMatch(ride, info);
					sortedSet.add(new RideMatchInfo(match));
					matcheMap.put(match.getId(), match);
				}
			}
			
			return sortedSet;
			
		}
		return null;

		
		
	}

	public static long getId() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Matcher [matcheMap=" + matcheMap + ", rideMap=" + rideMap + ", rideMatchInfoMap=" + rideMatchInfoMap
				+ "]";
	}
	
	public class RideMatch {
		long id;
		Map<RideRole, Ride> rides = new HashMap<RideRole, Ride>();

		public RideMatch(Ride left, Ride right) {

			this.rides.put(left.getRideRole(), left);
			this.rides.put(right.getRideRole(), right);
			
			this.id = lastIdRideMatch++;
		}

		// Generated unique identifier of this ride match.
		public long getId() {
			return id;
		}

		// Ride of user with given role
		public Ride getRide(RideRole role) {
			return rides.get(role);
		}
		

		// Are these rides matchable? Do they fill both roles (user and passenger)? Are
		// they both unmatched? Are they currently in (roughly) the same place? Are they
		// both going to (roughly) the same destination? Locations are considered
		// different if their distance exceeds radius Matcher.getRadius()
		boolean matchable() {
			if(!rides.containsKey(RideRole.DRIVER) || !rides.containsKey(RideRole.PASSENGER))
				return false;
			
			Ride passenger = rides.get(RideRole.PASSENGER);
			Ride driver = rides.get(RideRole.DRIVER);
			
			if(driver.isMatched() || passenger.isMatched())
				return false;
			
			if(tooFar(passenger.getFrom(), driver.getFrom()) || tooFar(passenger.getTo(), driver.getTo()))
				return false;
			
			return true;
		}
		
		private boolean tooFar(Location passLocation, Location drivLocation) {
			return Math.sqrt(Math.pow(passLocation.getX()-drivLocation.getX(), 2) + Math.pow(passLocation.getY() - drivLocation.getY(), 2))>getRadius();
		}


		private Matcher getOuterType() {
			return Matcher.this;
		}
		
		public Car getCar() {
			if(rides.get(RideRole.DRIVER)!=null)
				return rides.get(RideRole.DRIVER).getUser().getCar(rides.get(RideRole.DRIVER).getPlate());
			return null;
		}
	}

	
	

}
