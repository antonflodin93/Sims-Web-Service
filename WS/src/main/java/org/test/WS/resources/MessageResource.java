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

	private MessageService messageService = new MessageService();
	private List<Message> messages = null;

	/*
	 * 
	 * BROADCAST MESSAGES
	 * 
	 */

	// Return regular broadcast messages
	@GET
	@Path("/regular")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegularBroadCastMessages() throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getBroadCastMessages("regular");

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Return warning broadcast messages
	@GET
	@Path("/warning")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWarningBroadCastMessages() throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getBroadCastMessages("warning");

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
	public Response addBroadcastRegularMessage(Message message) throws ClassNotFoundException, SQLException {
		try {
			messageService.addBroadcastRegularMessage(message);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	/*
	 * 
	 * EMPLOYEE MESSAGES
	 * 
	 */

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

	/*
	 * 
	 * COMPANY MESSAGES
	 * 
	 */

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

	/*
	 * 
	 * FACTORY OBJECT MESSAGES
	 * 
	 */

	// Insert regular message for a factoryobject
	@POST
	@Path("/regular/factoryobject/{factoryobjectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addObjectRegularMessage(@PathParam("factoryobjectId") int factoryobjectId, Message message)
			throws ClassNotFoundException, SQLException {
		try {
			messageService.addFactoryObjectRegularMessage(message, factoryobjectId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Get regular message for a given object
	@GET
	@Path("/regular/factoryobject/{factoryobjectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFactoryObjectRegularMessage(@PathParam("factoryobjectId") int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getFactoryObjectRegularMessage(factoryobjectId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Insert warning message for a factoryobject
	@POST
	@Path("/warning/factoryobject/{factoryobjectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addObjectWarningMessage(@PathParam("factoryobjectId") int factoryobjectId, Message message)
			throws ClassNotFoundException, SQLException {
		try {
			messageService.addFactoryObjectWarningMessage(message, factoryobjectId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Get warning message for a given object
	@GET
	@Path("/warning/factoryobject/{factoryobjectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFactoryObjectWarningMessage(@PathParam("factoryobjectId") int factoryobjectId)
			throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getFactoryObjectWarningMessage(factoryobjectId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Get regular messages for all object in a given floor
	@GET
	@Path("/regular/floor/{floorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFloorRegularMessage(@PathParam("floorId") int floorId)
			throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getFactoryObjectFloorRegularMessages(floorId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	/*
	 * 
	 * BUILDING MESSAGES
	 * 
	 */

	// Get all warning messages for buildings
	@GET
	@Path("/warning/building")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBuildingWarningMessages() throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getAllBuildingWarningMessages();

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Insert warning message for a building
	@POST
	@Path("/warning/building/{buildingId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBuildingWarningMessage(@PathParam("buildingId") int buildingId, Message message)
			throws ClassNotFoundException, SQLException {
		try {
			messageService.addBuildingWarningMessage(message, buildingId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	// Get warning message for a building for a employee that is not acknowledged
	@GET
	@Path("/warning/building/{buildingId}/employee/{employeeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuildingWarningMessageNotAcked(@PathParam("buildingId") int buildingId,
			@PathParam("employeeID") int employeeID) throws ClassNotFoundException, SQLException {
		try {
			messages = messageService.getBuildingWarningMessageNotAcked(buildingId, employeeID);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Message>>(messages) {
		}).build();
	}

	// Insert acknowledge message for a warning message in a building
	@POST
	@Path("/warning/{messageId}/employee/{employeeID}")
	public Response acknowledgeFloorWarningMessage(@PathParam("messageId") int messageId,
			@PathParam("employeeID") int employeeID) throws ClassNotFoundException, SQLException {
		try {
			messageService.acknowledgeBuildingWarningMessage(messageId, employeeID);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	/*
	 * 
	 * FLOOR MESSAGES
	 * 
	 */

}
