package org.test.WS.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.test.WS.database.DBConnection;
import org.test.WS.model.Employee;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	private String username, password;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		// Check if the url consists of the secured url prefix
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			
			// 
			List<String> authorizationHeaders = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			
			//
			if(authorizationHeaders != null && authorizationHeaders.size() > 0 ) {
				//
				String authorizationToken = authorizationHeaders.get(0);
				authorizationToken = authorizationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				
				// 
				String decodedString = Base64.decodeAsString(authorizationToken);
				//String decodedString = authorizationToken;
				
				//
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				
				//
				username = tokenizer.nextToken();
				password = tokenizer.nextToken();
				
				// Check if user exists
				try {
					if(correctCredentials()) {
						return;
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Username and/or password do not match, " + username + ", " + password).build();

			requestContext.abortWith(response);
		} else {
			return;
		}

	}

	private boolean correctCredentials() throws SQLException, ClassNotFoundException {
		boolean correctcredentials = false;
		Connection connection;
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * FROM employees WHERE (employeeUsername = (?) OR employeeEmail = (?)) AND employeePassword = (?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		pst.setString(2, username);
		pst.setString(3, password);
		ResultSet resultSet = pst.executeQuery();
		
		if (resultSet != null) {
			resultSet.beforeFirst();
			resultSet.last();
			if(resultSet.getRow() > 0) {
				correctcredentials = true;
			}
		}
		
		
		pst.close();
		
		return correctcredentials;
	}

}
