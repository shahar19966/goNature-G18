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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.getValue().equals(other.id.getValue()))
			return false;
		return true;
	}


}
