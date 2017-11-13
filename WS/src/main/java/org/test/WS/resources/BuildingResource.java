package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Building;
import org.test.WS.service.BuildingService;


@Path("buildings")
public class BuildingResource {
	private BuildingService buildingService = new BuildingService();
	private List<Building> buildings = null;

	// Return all the buildings
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBuildings() {
		try {
			buildings = buildingService.getAllBuildings();
		} catch(ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
			
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
		
	    return Response.ok(new GenericEntity<List<Building>>(buildings) {}).build();
	}

}
