package org.test.WS.resources;


import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("secured")
public class SecureResource {

	// Returns all employees
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEmployees() throws ClassNotFoundException, SQLException {
		return "This API is secured";
	}

}
