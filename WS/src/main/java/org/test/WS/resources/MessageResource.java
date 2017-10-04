package org.test.WS.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.test.WS.service.MessageService;


@Path("messages")
public class MessageResource {
	MessageService ms = new MessageService();
	
	/*
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getIt() {
        return ms.getMessages();
    }
    */
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hej!";
    }
}
