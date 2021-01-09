package entity;

import java.io.Serializable;
/*
 * Person is made for employee and subscriber to extend it
 */
public abstract class Person implements Serializable {
	protected String firstName;
	protected String lastName;
	protected String id;
	protected String email;

	public Person(String id, String firstName, String lastName ,String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email=email;
	}

	public String getEmail() {
		return email;
	}



	public String getID() {
		return id;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}

	@Override
	public String toString() {
		return id + " " + firstName + " " + lastName;
	}
}
