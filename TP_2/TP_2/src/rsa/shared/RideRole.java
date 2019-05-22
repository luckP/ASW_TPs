package rsa.shared;

import java.io.Serializable;

public enum RideRole implements Serializable{
//	This user is driving the car
	DRIVER,
//	This user is the passenger
	PASSENGER;
	

//	The other role: if driver then passenger, otherwise driver
	public RideRole other() {
		return (this.equals(DRIVER))? PASSENGER : DRIVER;
	}
	
	
	
}
