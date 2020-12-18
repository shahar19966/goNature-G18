package entity;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public abstract class Person implements Serializable {
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty id;
	private SimpleStringProperty email;

	public Person(String id, String firstName, String lastName ,String email) {
		this.id = new SimpleStringProperty(id);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email=new SimpleStringProperty(email);
	}

	public String getEmail() {
		return email.getValue();
	}

	public SimpleStringProperty getEmailProperty( ) {
		return email;
	}

	public String getID() {
		return id.getValue();
	}

	public SimpleStringProperty getIDProperty() {
		return id;
	}

	public String getFirstName() {
		return firstName.getValue();
	}

	public SimpleStringProperty getFirstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.getValue();
	}

	public SimpleStringProperty getLastNameProperty() {
		return lastName;
	}
	@Override
	public String toString() {
		return id.getValue() + " " + firstName.getValue() + " " + lastName.getValue();
	}
}
