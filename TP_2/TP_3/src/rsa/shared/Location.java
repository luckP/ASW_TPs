package rsa.shared;

import rsa.quad.HasPoint;

public class Location implements HasPoint {
	private double x;
	private double y;

	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;

		return ((new Double(this.getX())).equals(new Double(other.getX()))
				&& (new Double(this.getY())).equals(new Double(other.getY())));
	}

//	The X coordinate of this location
	@Override
	public double getX() {
		return x;
	}

//	The Y coordinate of this location
	@Override
	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Location: [x=" + (int) x + ", y=" + (int) y + "]";
	}

}
