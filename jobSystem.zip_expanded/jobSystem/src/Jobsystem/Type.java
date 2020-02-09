package Jobsystem;

/**
 *
 */
public class Type {

	private String name;

	/**
	 * Constructor
	 */
	public Type(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * show a message
	 */
	public void printMessage() {
		System.out.println("This " + this.name + " is working");
	}

}
