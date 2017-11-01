package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Company;
import org.test.WS.service.CompanyService;

@Path("companies")
public class CompanyResource {
	private CompanyService companyService = new CompanyService();
	private List<Company> companies = null;

	// Return all the companies
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCompanies() {
		try {
			companies = companyService.getAllCompanies();
		} catch(ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
			
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(e.getMessage()).build();
		}
		
	    return Response.ok(new GenericEntity<List<Company>>(companies) {}).build();
	}
}
