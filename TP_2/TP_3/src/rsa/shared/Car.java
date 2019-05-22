package rsa.shared;

public class Car extends java.lang.Object implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String plate, make, model, color;

	public Car(String plate, String make, String model, String color) {
		super();
		this.plate = plate;
		this.make = make;
		this.model = model;
		this.color = color;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Car [plate=" + plate + ", make=" + make + ", model=" + model + ", color=" + color + "]";
	}

	
}
