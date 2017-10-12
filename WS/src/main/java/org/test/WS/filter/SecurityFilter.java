package org.test.WS.filter;


import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

// Used to check if client has basic authentification
@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			// Get the header values for authentification header
			List<String> authorizationHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

			// Check if the authentification is in header
			if (authorizationHeader != null && authorizationHeader.size() > 0) {
				String authorizationToken = authorizationHeader.get(0);
				authorizationToken = authorizationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = Base64.decodeAsString(authorizationToken);
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();

				if ("user".equals(username) && "password".equals(password)) {
					return;
				}

			}

		}
		Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED)
				.entity("User cannot access the resource").build();

		// If the user is not authenticated
		requestContext.abortWith(unauthorizedResponse);

	}

}
