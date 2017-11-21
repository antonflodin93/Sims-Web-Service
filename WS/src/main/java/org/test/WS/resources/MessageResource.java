package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
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
	private List<Message> messages = null;

	// Return regular broadcast messages
	@GET
	@Path("/regular")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegularMessages() throws ClassNotFoundException, SQLException {
		try {
			messageType = MessageType.REGULAR;
			messages = messageService.getMessages(messageType);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Return warning messages
	@GET
	@Path("/warning")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWarningMessages() throws ClassNotFoundException, SQLException {
		try {
			messageType = MessageType.WARNING;
			messages = messageService.getMessages(messageType);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Insert broadcast regular message
	@POST
	@Path("/regular")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBroadcastMessage(Message message) throws ClassNotFoundException, SQLException {
		try {
			messageService.addBroadcastMessage(message);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Insert messages for employee
	@POST
	@Path("/regular/employee/{employeeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployeeMessage(@PathParam("employeeId") int employeeId, Message message)
			throws ClassNotFoundException, SQLException {
		try {
			messageService.addEmployeeMessage(message, employeeId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Get message for employee
	@GET
	@Path("/regular/employee/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeMessage(@PathParam("employeeId") int employeeId)
			throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getEmployeeMessage(employeeId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Insert message for a company
	@POST
	@Path("/regular/company/{companyName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCompanyMessage(@PathParam("companyName") String companyName, Message message)
			throws ClassNotFoundException, SQLException {
		try {
			messageService.addCompanyMessage(message, companyName);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Get message for company
	@GET
	@Path("/regular/company/{companyName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyMessage(@PathParam("companyName") String companyName)
			throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getCompanyMessage(companyName);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}
}
