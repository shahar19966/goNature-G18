package entity;

import javafx.beans.property.SimpleStringProperty;

public class Employee extends Person {
	private SimpleStringProperty employeeNumber;
	private EntityConstants.EmployeeRole role;

	public Employee(String employeeNumber, String id, String firstName, String lastName, String email, EntityConstants.EmployeeRole role) {
		super(id, firstName, lastName,email);
		this.role = role;
		this.employeeNumber = new SimpleStringProperty(employeeNumber);
	}

	@Override
	public String toString() {
		return ("Employee " + employeeNumber.getValue() + " " + super.toString() + " " + getEmail() + " "
				+ role);
	}

	public String getEmployeeNumber() {
		return employeeNumber.getValue();
	}


	public String getRole() {
		return role.toString();
	}

	public SimpleStringProperty getEmployeeNumberProperty() {
		return employeeNumber;
	}


	public void setEmail(String newEmail) {
		getEmailProperty().set(newEmail);
	}

	public EntityConstants.EmployeeRole getRoleEnum() {
		return role;
	}

}
