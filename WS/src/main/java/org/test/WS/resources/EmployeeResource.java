package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Employee;
import org.test.WS.service.EmployeeService;

// The "user interface" that user connects to
//http://localhost:8080/WS/webapi/messages
@Path("employees")
public class EmployeeResource {
	EmployeeService employeeService = new EmployeeService();

	// Returns all employees
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployees() throws ClassNotFoundException, SQLException {
		return employeeService.getEmployees();
	}

	// Returns employee with a certain ID
	@GET
	@Path("userid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeById(@PathParam("id") String id) throws ClassNotFoundException, SQLException {
		int theid = Integer.parseInt(id);
		return employeeService.getEmployeeById(theid);
	}

	// Returns employee with a certain username
	@GET
	@Path("username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByUsername(@PathParam("username") String username)
			throws ClassNotFoundException, SQLException {
		return employeeService.getEmployeeByUsername(username);
	}

	// Insert an employee in database
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employee employee) throws ClassNotFoundException, SQLException {

		return employeeService.addEmployee(employee);
	}

	// Delete an employee in database, returns employee
	@DELETE
	@Path("{id}")
	public Response deleteEmployeeById(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		return employeeService.deleteEmployeeById(id);
	}

	// Returns all employees in a company
	@GET
	@Path("{company}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployeesInCompany(@PathParam("company") String company)
			throws ClassNotFoundException, SQLException {
		return employeeService.getEmployeesInCompany(company);
	}

}