package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Floor;
import org.test.WS.service.FloorService;

@Path("floors")
public class FloorResource {
	private FloorService floorService = new FloorService();
	private List<Floor> floors = null;
	private Floor floor;

	// Return all the floors
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFloors() {
		try {
			floors = floorService.getAllFloors();
		} catch (ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok(new GenericEntity<List<Floor>>(floors) {
		}).build();
	}

	// Get floor by Id
	@GET
	@Path("/{floorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeMessage(@PathParam("floorId") int floorId)
			throws ClassNotFoundException, SQLException {
		try {
			floor = floorService.getFloorById(floorId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity(floor).build();
	}

}
