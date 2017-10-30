package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Company;


public class CompanyService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Company> companies;

	// Returns all companies
	public List<Company> getAllCompanies() throws SQLException {
		try {
			companies = new ArrayList<Company>();
			connection = DBConnection.setDBConnection();
			String sql = "SELECT * from companies";
			PreparedStatement pst = connection.prepareStatement(sql);
			resultSet = pst.executeQuery();

			while (resultSet.next()) {

				Company company = new Company(resultSet.getString("companyName"));
				companies.add(company);
			}

			pst.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			connection.close();

		}

		return companies;
	}

}
