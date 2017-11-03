package org.test.WS.resources;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.test.WS.model.Employee;
import org.test.WS.service.EmployeeService;

// The "user interface" that user connects to
//http://localhost:8080/WS/webapi/messages
@Path("employees")
public class EmployeeResource {
	EmployeeService employeeService = new EmployeeService();
	List<Employee> employees = null;
	Employee employee = null;

	// Returns all employees
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployees() throws ClassNotFoundException, SQLException {
		try {
			employees = employeeService.getEmployees();
			
		} catch(SQLException|ClassNotFoundException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {}).build();
	}

	// Returns employee with a certain ID
	@GET
	@Path("userid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeById(@PathParam("id") String id) throws ClassNotFoundException, SQLException {
		int theid = Integer.parseInt(id);
		try {
			employee = employeeService.getEmployeeById(theid);			
		} catch(ClassNotFoundException|SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();			
		}
		
		return Response.ok(employee).build();
	}

	// Returns employee with a certain username
	@GET
	@Path("username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByUsername(@PathParam("username") String username)
			throws ClassNotFoundException, SQLException {
		try {
			employee = employeeService.getEmployeeByUsername(username);
			
		} catch(ClassNotFoundException|SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();			
		}
		
		return Response.ok(employee).build();
	}

	// Insert an employee in database
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		Boolean success;
		try {
			success = employeeService.addEmployee(employee);
			if(success) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("The user already exists.").build();	
			}
		} catch(NoSuchAlgorithmException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Could not successfully hash the password").build();
		} catch(SQLException|ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();			
		}

	}

	// Delete an employee in database, returns employee
	@DELETE
	@Path("{id}")
	public Response deleteEmployeeById(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		try {
			employeeService.deleteEmployeeById(id);			
		} catch(ClassNotFoundException|SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();			
		}
		
		return Response.ok().build();
	}

	// Returns all employees in a company
	@GET
	@Path("company/{company}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesInCompany(@PathParam("company") String company)
			throws ClassNotFoundException, SQLException {
			
		try {
			employees = employeeService.getEmployeesInCompany(company);
			
		} catch(SQLException|ClassNotFoundException e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {}).build();
		
		
	}

}