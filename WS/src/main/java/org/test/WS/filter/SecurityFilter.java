package org.test.WS.filter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	private static final String SECURED_MASTER_LOGIN_URL_PREFIX = "secured/login/master";
	private static final String SECURED_EMPLOYEE_LOGIN_URL_PREFIX = "secured/login/employee";
	private String employeeID;

	private enum UserType {
		MASTER, EMPLOYEE;
	}

	UserType userType;

	private String username, password;
	//private static final Logger logger = Logger.getLogger("SecurityFilter");

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//FileHandler fh;
		// This block configure the logger with handler and formatter
		//fh = new FileHandler("C:/Users/Student/Desktop/DEV/Logs/MyLogFile.log");
		//SimpleFormatter formatter = new SimpleFormatter();
		//fh.setFormatter(formatter);
		//logger.addHandler(fh);
		
		// Check if the url consists of the secured url prefix
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			

			// Check if the user is employee or master
			if (requestContext.getUriInfo().getPath().contains(SECURED_MASTER_LOGIN_URL_PREFIX)) {
				userType = UserType.MASTER;
				//logger.info("MASTER");
				
			} else if (requestContext.getUriInfo().getPath().contains(SECURED_EMPLOYEE_LOGIN_URL_PREFIX)) {
				userType = UserType.EMPLOYEE;
				//logger.info("EMPLOYEE");
			}

			// Get list of the authorization headers
			List<String> authorizationHeaders = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

			// Check if there is any headers
			if (authorizationHeaders != null && authorizationHeaders.size() > 0) {
				// Get the authorization token
				String authorizationToken = authorizationHeaders.get(0);
				authorizationToken = authorizationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

				// Decode the authorization token with base64 coding
				String decodedString = Base64.decodeAsString(authorizationToken);

				// Get tokenizer of the decoded strings
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

				// Get the username and password
				username = tokenizer.nextToken();
				password = tokenizer.nextToken();

				

				// Check if user exists
				try {
					if (correctCredentials()) {
						if(userType == UserType.EMPLOYEE) {
							requestContext.getHeaders().add("employeeID", employeeID);
						}
						return;
					}
				} catch (ClassNotFoundException | SQLException e) {
					//logger.info("ERROR: " + e.getMessage());
				}

			}

			Response response = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Username and/or password do not match, " + username + ", " + password).build();

			requestContext.abortWith(response);
		} else {
			return;
		}

	}

	private boolean correctCredentials() throws ClassNotFoundException, SecurityException, IOException, SQLException {
		boolean correctcredentials = false;
		Connection connection = null;
		connection = DBConnection.setDBConnection();

		String sql = null;
		// Get the hashed password and the salt for the username or email for the
		// specific role
		if (userType == UserType.MASTER) {
			sql = "SELECT employeePassword, employeeSalt FROM employees WHERE (employeeUsername = (?) OR employeeEmail = (?)) AND employeeRole = 'MASTER'";
			//logger.info("MASTER SQL");

		} else if (userType == UserType.EMPLOYEE) {
			//logger.info("EMPLOYEE SQL");
			sql = "SELECT employeePassword, employeeSalt, employeeID FROM employees WHERE (employeeUsername = (?) OR employeeEmail = (?)) AND employeeRole = 'EMPLOYEE'";
		}

		PreparedStatement pst = null;
		ResultSet resultSet = null;

		pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		pst.setString(2, username);
		resultSet = pst.executeQuery();

		// Check if the result is empty
		if (resultSet != null) {
			if (resultSet.next() == true) {
				if(userType == UserType.EMPLOYEE) {
					employeeID = String.valueOf(resultSet.getLong("employeeID"));
				}
				String hash = resultSet.getString("employeePassword");
				String salt = resultSet.getString("employeeSalt");
				String passwordAndSalt = password + salt;

				MessageDigest md;

				try {
					// Hash it
					md = MessageDigest.getInstance("SHA-256");
					md.update(passwordAndSalt.getBytes());

				} catch (NoSuchAlgorithmException e) {
					return correctcredentials = false;
				}

				byte byteData[] = md.digest();

				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}

				String hashgenerated = sb.toString();
				// logger.info(hashgenerated + "(generated)");
				// logger.info(hash + "(generated)");

				if (hashgenerated.equals(hash)) {
					correctcredentials = true;
				}

			}
		}
		pst.close();

		return correctcredentials;
	}

}
