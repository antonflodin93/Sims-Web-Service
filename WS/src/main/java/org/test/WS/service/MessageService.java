package org.test.WS.service;

import java.util.ArrayList;
import java.util.List;

import org.test.WS.model.Message;

// Used for returning messages
// This is the class that will be used for DB communication
public class MessageService {
	
	// Creates messages and returns
	public List<Message> getMessages() {
		Message m1 = new Message("Hello!", 1);
		Message m2 = new Message("Hello again!", 2);
		List<Message> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		return list;

	}
}
