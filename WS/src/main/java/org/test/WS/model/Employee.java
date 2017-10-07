package org.test.WS.model;

import javax.xml.bind.annotation.XmlRootElement;

// Defines the structure of a employee
@XmlRootElement
public class Employee {

	private String employeeFirstName;
	private String employeeLastName;
	private int employeeId;

	public Employee() {
		
	}
	
	
	public Employee(int employeeId, String employeeFirstName, String employeeLastName) {
		super();
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.employeeId = employeeId;
	}


	public String getEmployeeFirstName() {
		return employeeFirstName;
	}


	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}


	public String getEmployeeLastName() {
		return employeeLastName;
	}


	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

}
