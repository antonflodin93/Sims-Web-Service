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
		String sql = "SELECT * from employees WHERE employeeRole = 'EMPLOYEE' ";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));

			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}

	// Returns employee with id
	public Employee getEmployeeById(int id) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeID = ? AND employeeRole = 'EMPLOYEE'";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));
		} else {
			employee = null;
		}
		pst.close();
		connection.close();
		return employee;
	}

	// Returns employee with username
	public Employee getEmployeeByUsername(String username) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "Select * from employees where employeeUsername = ? AND employeeRole = 'EMPLOYEE'";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		resultSet = pst.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next() == true) {
			employee = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));
		} else {
			employee = null;
		}
		pst.close();
		connection.close();
		return employee;
	}

	// Inserts employee into db
	public boolean addEmployee(Employee employee)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// Check if the company of the employee exists
		String company = employee.getEmployeeCompany();
		connection = DBConnection.setDBConnection();
		String sql = "Select * from companies where companyName = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, company);
		resultSet = pst.executeQuery();

		// If the company does not exists
		if (resultSet.next() == false) {
			pst.close();
			connection.close();
			// Insert the company in DB
			connection = DBConnection.setDBConnection();
			sql = "INSERT INTO companies (companyName) values(?)";
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

		// Hash it
		md = MessageDigest.getInstance("SHA-256");
		md.update(passwordandsalt.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String hash = sb.toString();

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
			// User already exists, return false
			return false;
		}

		return true;
	}

	public void deleteEmployeeById(int id) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "DELETE FROM employees WHERE employeeId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, id);
		pst.executeUpdate();
		pst.close();
		connection.close();

	}

	public List<Employee> getEmployeesInCompany(String company) throws ClassNotFoundException, SQLException {
		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from employees where employeeCompany = '" + company + "' AND employeeRole = 'EMPLOYEE'";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));

			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}

	public List<Employee> getEmployeesInBuilding(int buildingId) throws ClassNotFoundException, SQLException {
		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM employees INNER JOIN employeebuilding ON employees.employeeID = employeebuilding.employeeID AND employeebuilding.buildingId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, buildingId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));

			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}

	public List<Employee> getEmployeesInFloor(int floorId) throws ClassNotFoundException, SQLException {
		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM employees INNER JOIN employeefloor ON employees.employeeID = employeefloor.employeeID AND employeefloor.floorId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeeUsername"), resultSet.getString("employeeEmail"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"));

			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}

	public void deleteEmployeeInBuilding(int employeeId) throws ClassNotFoundException, SQLException {
		connection = DBConnection.setDBConnection();
		String sql = "DELETE FROM employeebuilding WHERE employeeId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, employeeId);
		pst.executeUpdate();
		pst.close();
		connection.close();

	}

	public void updateEmployeeFloor(int floorId, int employeeID) throws SQLException, ClassNotFoundException {
		connection = DBConnection.setDBConnection();
		String sql = "UPDATE employeefloor SET floorId = ? WHERE employeeID = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		pst.setLong(2, employeeID);
		pst.executeUpdate();
		pst.close();
		connection.close();

	}

	// Employee enters a building
	public void enterBuildingEmployee(int buildingId, int employeeID) throws ClassNotFoundException, SQLException {
		connection = DBConnection.setDBConnection();
		String sql = "INSERT IGNORE INTO employeebuilding (buildingId, employeeID) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, buildingId);
		pst.setLong(2, employeeID);
		pst.executeUpdate();
		pst.close();
		connection.close();
	}

	public void enterFloorEmployee(int floorId, int employeeID) throws ClassNotFoundException, SQLException {
		connection = DBConnection.setDBConnection();
		String sql = "INSERT IGNORE INTO employeefloor (floorId, employeeID) values(?, ?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, floorId);
		pst.setLong(2, employeeID);
		pst.executeUpdate();
		pst.close();
		connection.close();
		
	}
	
	// Get all employees that has acknowledged a message
	public List<Employee> getEmployeesAcknowledged(int messageId) throws ClassNotFoundException, SQLException {
		employees = new ArrayList<Employee>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM employees INNER JOIN messageacknowledged ON employees.employeeID = messageacknowledged.employeeID AND messageacknowledged.messageId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, messageId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Employee m = new Employee(Integer.parseInt(resultSet.getString("employeeId")),
					resultSet.getString("employeeFirstName"), resultSet.getString("employeeLastName"),
					resultSet.getString("employeePhonenumber"), resultSet.getString("employeeCompany"), resultSet.getLong("acknowledgeCurrentMillis"));

			employees.add(m);
		}
		pst.close();
		connection.close();

		return employees;
	}
}
