package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Message;
import org.test.WS.service.MessageService;

@Path("messages")
public class MessageResource {
	public enum MessageType {
		REGULAR, WARNING;
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

	// Return warning messages
	@GET
	@Path("/warning")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getWarningMessages() throws ClassNotFoundException, SQLException {
		messageType = MessageType.WARNING;
		return messageService.getMessages(messageType);
	}

	// Insert broadcast message
	@POST
	@Path("/regular")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBroadcastMessage(Message message) throws ClassNotFoundException, SQLException {

		return messageService.addBroadcastMessage(message);
	}

	// Insert message for employee
	@GET
	@Path("/regular/employee/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getEmployeeMessage(@PathParam("employeeId") int employeeId)
			throws ClassNotFoundException, SQLException {

		return messageService.getEmployeeMessage(employeeId);
	}
	
	// Get messages for employee
	@POST
	@Path("/regular/employee/{employeeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployeeMessage(@PathParam("employeeId") int employeeId, Message message)
			throws ClassNotFoundException, SQLException {

		return messageService.addEmployeeMessage(message, employeeId);
	}
	
	
	// Insert message for a company
	@POST
	@Path("/regular/company/{companyName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCompanyMessage(@PathParam("companyName") String companyName, Message message)
			throws ClassNotFoundException, SQLException {

		return messageService.addCompanyMessage(message, companyName);
	}

}
