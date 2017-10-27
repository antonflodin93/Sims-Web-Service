package org.test.WS.model;

import javax.xml.bind.annotation.XmlRootElement;

// Defines the structure of a employee
@XmlRootElement
public class Employee {

	private int employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeeUsername;
	private String employeeEmail;
	private String employeePassword;
	private String employeePhonenumber;
	private String employeeCompany;
	

	public Employee() {
		
	}


	public Employee(int employeeId, String employeeFirstName, String employeeLastName, String employeeUsername,
			String employeeEmail, String employeePhonenumber, String employeeCompany) {
		super();
		this.employeeId = employeeId;
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.employeeUsername = employeeUsername;
		this.employeeEmail = employeeEmail;
		this.employeePassword = employeePassword;
		this.employeePhonenumber = employeePhonenumber;
		this.employeeCompany = employeeCompany;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
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


	public String getEmployeeUsername() {
		return employeeUsername;
	}


	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}


	public String getEmployeeEmail() {
		return employeeEmail;
	}


	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}


	public String getEmployeePassword() {
		return employeePassword;
	}


	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}


	public String getEmployeePhonenumber() {
		return employeePhonenumber;
	}


	public void setEmployeePhonenumber(String employeePhonenumber) {
		this.employeePhonenumber = employeePhonenumber;
	}


	public String getEmployeeCompany() {
		return employeeCompany;
	}


	public void setEmployeeCompany(String employeeCompany) {
		this.employeeCompany = employeeCompany;
	}
	
	
	

}
