package org.test.WS.service;

import java.util.ArrayList;
import java.util.List;

import org.test.WS.model.Message;

public class MessageService {
	
	public List<Message> getMessages(){
		Message m1 = new Message("Hello!");
		Message m2 = new Message("Hello again!");
		List<Message> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		return list;
		
	}
}
