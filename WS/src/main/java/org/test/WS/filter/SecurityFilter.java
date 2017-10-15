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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	private String username, password;
	private static final Logger logger = Logger.getLogger("SecurityFilter");

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Check if the url consists of the secured url prefix
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {

			//
			List<String> authorizationHeaders = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

			//
			if (authorizationHeaders != null && authorizationHeaders.size() > 0) {
				//
				String authorizationToken = authorizationHeaders.get(0);
				authorizationToken = authorizationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

				//
				String decodedString = Base64.decodeAsString(authorizationToken);
				// String decodedString = authorizationToken;

				//
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

				//
				username = tokenizer.nextToken();
				password = tokenizer.nextToken();

				// Check if user exists
				try {
					if (correctCredentials()) {
						return;
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			Response response = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Username and/or password do not match, " + username + ", " + password).build();

			requestContext.abortWith(response);
		} else {
			return;
		}

	}

	private boolean correctCredentials() throws SQLException, ClassNotFoundException, SecurityException, IOException {
		FileHandler fh;
		// This block configure the logger with handler and formatter
		fh = new FileHandler("C:/Users/Student/Desktop/DEV/MyLogFile.log");
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);

		// the following statement is used to log any messages
		logger.info("In function");

		boolean correctcredentials = false;
		Connection connection;
		connection = DBConnection.setDBConnection();
		// Get the hashed password and the salt for the username or email
		String sql = "SELECT employeePassword, employeeSalt FROM employees WHERE employeeUsername = (?) OR employeeEmail = (?)";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, username);
		pst.setString(2, username);
		ResultSet resultSet = pst.executeQuery();
		logger.info("sql: " + sql);
		logger.info(password);

		// Check if the result is empty
		if (resultSet != null) {
			if (resultSet.next() == true) {
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
				logger.info(hashgenerated + "(generated)");
				logger.info(hash + "(generated)");

				if (hashgenerated.equals(hash)) {
					correctcredentials = true;
				}

			} else {
				logger.info("null response");
			}
		} else {
			logger.info("null response");
		}

		pst.close();

		return correctcredentials;

		/*
		 * boolean correctcredentials = false; Connection connection; connection =
		 * DBConnection.setDBConnection(); String sql =
		 * "SELECT * FROM employees WHERE (employeeUsername = (?) OR employeeEmail = (?)) AND employeePassword = (?)"
		 * ; PreparedStatement pst = connection.prepareStatement(sql); pst.setString(1,
		 * username); pst.setString(2, username); pst.setString(3, password); ResultSet
		 * resultSet = pst.executeQuery();
		 * 
		 * if (resultSet != null) { resultSet.beforeFirst(); resultSet.last();
		 * if(resultSet.getRow() > 0) { correctcredentials = true; } }
		 * 
		 * 
		 * pst.close();
		 * 
		 * return correctcredentials;
		 */
	}

}
