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
	public enum MessageType {
	    REGULAR,
	    WARNING;
	}
	private MessageType messageType;
	private MessageService messageService = new MessageService();

	// Return regular messages
	@GET
	@Path("/regular")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getRegularMessages() throws ClassNotFoundException, SQLException {
		messageType = MessageType.REGULAR;
		return messageService.getMessages(messageType);
	}

	// Return messages
	@GET
	@Path("/warning")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getWarningMessages() throws ClassNotFoundException, SQLException {
		messageType = MessageType.WARNING;
		return messageService.getMessages(messageType);
	}

}
