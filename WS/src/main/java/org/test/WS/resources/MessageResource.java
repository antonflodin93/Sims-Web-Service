package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Employee;
import org.test.WS.model.Message;
import org.test.WS.service.EmployeeService;
import org.test.WS.service.MessageService;

@Path("messages")
public class MessageResource {
	MessageService messageService = new MessageService();

	// Return messages
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages() throws ClassNotFoundException, SQLException {

		return messageService.getMessages();
	}

}
