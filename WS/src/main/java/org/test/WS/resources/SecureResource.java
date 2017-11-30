package org.test.WS.resources;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("secured")
public class SecureResource {

	// Login as master
	@GET
	@Path("login/master")
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginMaster() throws ClassNotFoundException, SQLException {
		Response response = Response.status(Response.Status.ACCEPTED).build();
		return response;
	}

	// Login as employee
	@GET
	@Path("login/employee")
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginEmployee(@HeaderParam("employeeID") String employeeID) throws ClassNotFoundException, SQLException {
		
		Response response = Response.status(Response.Status.ACCEPTED).entity(employeeID).build();
		return response;
	}
	
}
