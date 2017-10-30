package org.test.WS.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.test.WS.model.Employee;
import org.test.WS.database.DBConnection;

// Used for returning messages using DB

public class EmployeeService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Employee> employees;

	public EmployeeService() {

	}

	// Returns all employees
	public List<Employee> getEmployees() throws SQLException, ClassNotFoundException {

		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from employees";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")), resultSet.getString("employeeFirstName"),
					resultSet.getString("employeeLastName"), resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"), resultSet.getString("employeePhonenumber"),
					resultSet.getString("employeeCompany"));			
			
			employees.add(m);
		}
		pst.close();
		connection.close();
		
		return employees;
	}

	// Returns employee with id
	public Response getEmployeeById(int id) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeID = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString("employeeId")), resultSet.getString("employeeFirstName"),
					resultSet.getString("employeeLastName"), resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"), resultSet.getString("employeePhonenumber"),
					resultSet.getString("employeeCompany"));	
		} else {
			employee = null;
		}
		pst.close();
		connection.close();
		return Response.ok().entity(employee).build();
	}

	// Returns employee with username
	public Response getEmployeeByUsername(String username) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeUsername = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString("employeeId")), resultSet.getString("employeeFirstName"),
					resultSet.getString("employeeLastName"), resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"), resultSet.getString("employeePhonenumber"),
					resultSet.getString("employeeCompany"));
		} else {
			employee = null;
		}
		pst.close();
		connection.close();
		return Response.ok().entity(employee).build();
	}

	// Inserts employee into db
	public Response addEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		// Check if the company of the employee exists
		String company = employee.getEmployeeCompany();
		connection = DBConnection.setDBConnection();
		String sql = "Select * from company where companyName = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, company);
		resultSet = pst.executeQuery();

		// If the company does not exists
		if (resultSet.next() == false) {
			pst.close();
			connection.close();
			// Insert the company in DB
			connection = DBConnection.setDBConnection();
			sql = "INSERT INTO company (companyName) values(?)";
			pst = connection.prepareStatement(sql);
			pst.setString(1, company);
			pst.executeUpdate();

		}


		pst.close();
		connection.close();

		// Generate salt
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);

		// Convert salt to string type
		StringBuffer saltBuffer = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			saltBuffer.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
		}
		String saltString = saltBuffer.toString();

		// Add the salt to the password
		String passwordandsalt = employee.getEmployeePassword() + saltString;
		MessageDigest md = null;

		try {
			// Hash it
			md = MessageDigest.getInstance("SHA-256");
			md.update(passwordandsalt.getBytes());

		} catch (NoSuchAlgorithmException e) {
			// Return status 500 if algorithm not found
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Could not successfully hash the password").build();
		}

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String hash = sb.toString();

		try {
			connection = DBConnection.setDBConnection();
			// Check if user exists
			String checkIfExistsSql = "Select * from employees where employeeUsername = ? || employeeEmail = ?";
			PreparedStatement ps = connection.prepareStatement(checkIfExistsSql);
			ps.setString(1, employee.getEmployeeUsername());
			ps.setString(2, employee.getEmployeeEmail());
			resultSet = ps.executeQuery();

			// If user do not exists
			if (resultSet.next() == false) {
				// Insert into db
				sql = "INSERT INTO employees (employeeId, employeeFirstName, employeeLastName, employeeUsername, "
						+ "employeeEmail, employeePassword, employeePhonenumber, employeeCompany, employeeSalt) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pst = connection.prepareStatement(sql);
				pst.setLong(1, employee.getEmployeeId());
				pst.setString(2, employee.getEmployeeFirstName());
				pst.setString(3, employee.getEmployeeLastName());
				pst.setString(4, employee.getEmployeeUsername());
				pst.setString(5, employee.getEmployeeEmail());
				pst.setString(6, hash);
				pst.setString(7, employee.getEmployeePhonenumber());
				pst.setString(8, employee.getEmployeeCompany());
				pst.setString(9, saltString);
				pst.executeUpdate();
				pst.close();
				connection.close();
			} else {
				connection.close();
				// User already exists, return 403
				return Response.status(Response.Status.FORBIDDEN)
						.entity("User already exists, change username and/or email.").build();
			}

		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Could not successfully store the user information: " + e.getMessage()).build();
		}

		return Response.ok().build();
	}

	public Response deleteEmployeeById(int id) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "DELETE FROM employees WHERE employeeId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		pst.executeUpdate();
		pst.close();
		connection.close();
		return Response.ok().build();
	}

	public List<Employee> getEmployeesInCompany(String company) throws ClassNotFoundException, SQLException {
		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from employees where employeeCompany = '" + company + "'";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")), resultSet.getString("employeeFirstName"),
					resultSet.getString("employeeLastName"), resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"), resultSet.getString("employeePhonenumber"),
					resultSet.getString("employeeCompany"));			
			
			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}

}
