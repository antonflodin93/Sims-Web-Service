package org.test.WS.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.test.WS.model.Message;
import org.test.WS.service.MessageService;

// The "user interface" that user connects to
//http://localhost:8080/WS/webapi/messages
@Path("messages")
public class MessageResource {
	MessageService ms = new MessageService();
	
	// Default return messages from messageservice class
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getIt() {
		return ms.getMessages();
	}
	
	

}


/* Authentification:
 * 	* Sending password and username every time, encode password. Send over https to keep info secure
 *  * 
 * */
