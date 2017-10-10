package org.test.WS.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.test.WS.model.Employee;
import org.test.WS.service.EmployeeService;

// The "user interface" that user connects to
//http://localhost:8080/WS/webapi/messages
@Path("employees")
public class EmployeeResource {
	EmployeeService ms = new EmployeeService();

	// Returns all employees
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getUsers() throws ClassNotFoundException, SQLException {
		return ms.getEmployees();
	}

	// Returns employee with a certain ID
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getUserById(@PathParam("id") String id) throws ClassNotFoundException, SQLException {
		int theid = Integer.parseInt(id);
		return ms.getEmployee(theid);
	}

	// Insert an employee in database, returns employee
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Employee addUser(Employee employee) throws ClassNotFoundException, SQLException {

		return ms.addEmployee(employee);
	}
}

/*
 * Authentification: * Sending password and username every time, encode
 * password. Send over https to keep info secure *
 */
