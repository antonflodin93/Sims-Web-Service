package org.test.WS.resources;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {
		}).build();
	}

	// Returns employee with a certain ID
	@GET
	@Path("userid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeById(@PathParam("id") String id) throws ClassNotFoundException, SQLException {
		int theid = Integer.parseInt(id);
		try {
			employee = employeeService.getEmployeeById(theid);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok(employee).build();
	}

	// Returns employee with a username
	@GET
	@Path("username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByUsername(@PathParam("username") String username)
			throws ClassNotFoundException, SQLException {
		try {
			employee = employeeService.getEmployeeByUsername(username);

		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
			if (success) {
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("The user already exists.").build();
			}
		} catch (NoSuchAlgorithmException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Could not successfully hash the password").build();
		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	// Delete an employee in database, returns employee
	@DELETE
	@Path("{id}")
	public Response deleteEmployeeById(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		try {
			employeeService.deleteEmployeeById(id);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {
		}).build();

	}

	// Returns all employees in a building
	@GET
	@Path("building/{buildingId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesInBuilding(@PathParam("buildingId") int buildingId)
			throws ClassNotFoundException, SQLException {

		try {
			employees = employeeService.getEmployeesInBuilding(buildingId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {
		}).build();

	}

	// Returns all employees in a floor
	@GET
	@Path("floor/{floorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesInFloor(@PathParam("floorId") int floorId) throws ClassNotFoundException, SQLException {

		try {
			employees = employeeService.getEmployeesInFloor(floorId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {
		}).build();

	}

	// Delete an employee in a building
	@DELETE
	@Path("building/{employeeId}")
	public Response deleteEmployeeInBuilding(@PathParam("employeeId") int employeeId)
			throws ClassNotFoundException, SQLException {
		try {
			employeeService.deleteEmployeeInBuilding(employeeId);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	// Update employees location when switching floor
	@PUT
	@Path("floor/{floorId}/employee/{employeeID}")
	public Response updateEmployeeFloor(@PathParam("floorId") int floorId, @PathParam("employeeID") int employeeID)
			throws ClassNotFoundException, SQLException {
		try {
			employeeService.updateEmployeeFloor(floorId, employeeID);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	// Employee enters a building
	@POST
	@Path("building/{buildingId}/employee/{employeeID}")
	public Response enterBuildingEmployee(@PathParam("buildingId") int buildingId,
			@PathParam("employeeID") int employeeID) throws ClassNotFoundException, SQLException {
		try {
			employeeService.enterBuildingEmployee(buildingId, employeeID);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok().build();

	}

	// Employee enters a floor first time
	@POST
	@Path("floor/{floorId}/employee/{employeeID}")
	public Response enterFloorEmployee(@PathParam("floorId") int floorId, @PathParam("employeeID") int employeeID)
			throws ClassNotFoundException, SQLException {
		try {
			employeeService.enterFloorEmployee(floorId, employeeID);
		} catch (ClassNotFoundException | SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok().build();

	}

	// Get all employees that has acknowledged a message getEmployeesAcknowledged
	// Returns all employees in a floor
	@GET
	@Path("message/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesAcknowledged(@PathParam("messageId") int messageId) throws ClassNotFoundException, SQLException {

		try {
			employees = employeeService.getEmployeesAcknowledged(messageId);

		} catch (SQLException | ClassNotFoundException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok(new GenericEntity<List<Employee>>(employees) {
		}).build();

	}
}