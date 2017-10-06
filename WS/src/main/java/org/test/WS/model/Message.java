package org.test.WS.model;

import javax.xml.bind.annotation.XmlRootElement;

// Defines the structure of the message
@XmlRootElement
public class Message {

	private String employeeName;
	private int employeeId;

	public Message() {
		
	}
	
	public Message(int id, String name) {
		super();
		this.employeeName = name;
		this.employeeId = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	

}
