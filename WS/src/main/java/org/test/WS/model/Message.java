package org.test.WS.model;

import javax.xml.bind.annotation.XmlRootElement;

// Defines the structure of a employee
@XmlRootElement
public class Message {

	private int messageId;
	private String messageText;
	private String messageLabel;
	private String messageType;
	private String date;
	private String time;

	public Message() {
		
	}
	
	
	public Message(int messageId, String messageText, String messageLabel, String messageType, String time, String date) {
		super();
		this.messageId = messageId;
		this.messageText = messageText;
		this.messageLabel = messageLabel;
		this.messageType = messageType;
		this.time = time;
		this.date = date;
	}



	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	public int getMessageId() {
		return messageId;
	}


	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}


	public String getMessageText() {
		return messageText;
	}


	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageLabel() {
		return messageLabel;
	}

	
	public void setMessageLabel(String messageLabel) {
		this.messageLabel = messageLabel;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
