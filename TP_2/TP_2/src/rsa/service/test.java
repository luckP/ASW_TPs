package rsa.service;

import rsa.quad.PointQuadtreeTest;
import rsa.shared.Location;

public class test {
	public static void main(String args[]) {

//		Manager manager = Manager.getInstance();
//		manager.register("nick1", "name1", "password1");
//		manager.register("nick2", "name2", "password2");
//		
//		
//		Location from = new Location(0, 0);
//		Location to = new Location(10, 10);
//		
//		String plate = "123";
//		float cost = 10;
//		
//		
//		long rideId = manager.addRide("nick1", "password1", from, to, plate, cost);
//		manager.addRide("nick2", "password2", from, to, "", cost);
//		
//		manager.acceptMatch(rideId, 1);
//		System.out.println(manager);
		Users us = Users.getInstance();
		us.register("luc", "lucas", "123");
		us.register("luc2", "lucas", "123");
//		us.register("luc3", "lucas", "123");
//		us.register("luc4", "lucas", "123");
		
		PointQuadtreeTest tree = new PointQuadtreeTest();
		tree.testFindAllPotugueseLocations();
//		System.out.println(us);
		
	}
}
