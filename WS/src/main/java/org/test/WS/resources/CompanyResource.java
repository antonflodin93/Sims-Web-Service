package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.test.WS.model.Company;
import org.test.WS.service.CompanyService;

@Path("companies")
public class CompanyResource {
	private CompanyService companyService = new CompanyService();

	// Return all the companies
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getAllCompanies() throws ClassNotFoundException, SQLException {
		return companyService.getAllCompanies();
	}
}
