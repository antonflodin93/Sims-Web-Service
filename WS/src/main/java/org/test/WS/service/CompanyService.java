package org.test.WS.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Company;
import javax.ws.rs.core.Response;

public class CompanyService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Company> result;
	
	
	public Response getAllCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

}
