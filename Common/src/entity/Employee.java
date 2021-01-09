package entity;

import java.io.Serializable;
/*
 * Employee class for the entity of an employee that is sent between the client and server
 */
public class Employee extends Person implements Serializable {

	private String employeeNumber;
	private EntityConstants.EmployeeRole role;
	private String parkName;

	public Employee(String employeeNumber, String id, String firstName, String lastName, String email, EntityConstants.EmployeeRole role,String parkName) {
		super(id, firstName, lastName,email);
		this.role = role;
		this.employeeNumber = employeeNumber;
		this.parkName=parkName;
	}

	@Override
	public String toString() {
		return ("Employee " + employeeNumber + " " + super.toString() + " " + getEmail() + " "
				+ role);
	}


	public String getEmployeeNumber() {
		return employeeNumber;
	}


	public String getRole() {
		return role.toString();
	}
	public String getParkName() {
		return parkName;
	}

	public EntityConstants.EmployeeRole getRoleEnum() {
		return role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
