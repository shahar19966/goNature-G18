package entity;

import javafx.beans.property.SimpleStringProperty;

public class Employee extends Person {
	private SimpleStringProperty employeeNumber;
	private SimpleStringProperty role;

	public Employee(String employeeNumber, String id, String firstName, String lastName, String email, String role) {
		super(id, firstName, lastName,email);
		this.role = new SimpleStringProperty(role);
		this.employeeNumber = new SimpleStringProperty(employeeNumber);
	}

	@Override
	public String toString() {
		return ("Employee " + employeeNumber.getValue() + " " + super.toString() + " " + getEmail() + " "
				+ role.getValue());
	}

	public String getEmployeeNumber() {
		return employeeNumber.getValue();
	}


	public String getRole() {
		return role.getValue();
	}

	public SimpleStringProperty getEmployeeNumberProperty() {
		return employeeNumber;
	}


	public void setEmail(String newEmail) {
		getEmailProperty().set(newEmail);
	}

	public SimpleStringProperty getRoleProperty() {
		return role;
	}

}
