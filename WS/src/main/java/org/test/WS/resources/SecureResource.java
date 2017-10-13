package org.test.WS.resources;

import javax.ws.rs.core.Response;
import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("secured")
public class SecureResource {

	// Returns all employees
	@GET
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testLogin() throws ClassNotFoundException, SQLException {
		Response response = Response.status(Response.Status.ACCEPTED).entity("User is granted access").build();
		return response;
	}

}
