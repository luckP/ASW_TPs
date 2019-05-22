package rsa.quad;

public class PointOutOfBoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3498441854753139027L;

	public PointOutOfBoundException() {
		
	}
	
	@Override
	public String toString() {
		return "Error: Point outside the boundaries";
	}
	
	

}
