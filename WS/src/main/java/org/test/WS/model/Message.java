package org.test.WS.model;

import javax.xml.bind.annotation.XmlRootElement;

// Defines the structure of the message
@XmlRootElement
public class Message {

	private String message;
	private int id;

	public Message() {
		
	}
	
	public Message(String message, int id) {
		super();
		this.message = message;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
