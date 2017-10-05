package org.test.WS.resources;

import java.sql.*;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.test.WS.model.Message;
import org.test.WS.service.MessageService;

// Test class for db connection 

@Path("db")
public class DBTest {
	
	// Default return messages from messageservice class
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Works!";
	}
	
	

}
