package org.test.WS.service;

import java.util.ArrayList;
import java.util.List;

import org.test.WS.model.Message;

// Used for returning messages
// This is the class that will be used for DB communication
public class MessageService {
	List<Message> messages;
	
	public MessageService() {
		Message m1 = new Message("Helloasdfasfsd!", 1);
		Message m2 = new Message("Hello again!", 2);
		messages = new ArrayList<>();
		messages.add(m1);
		messages.add(m2);
	}
	
	// Creates messages and returns them
	public List<Message> getMessages() {
		return messages;
	}
	
	public Message getMessage(int id) {
			
		return messages.get(id-1);
	}
	
	
	
	
}
