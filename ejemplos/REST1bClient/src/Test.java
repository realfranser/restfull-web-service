import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;


public class Test {

	public static void main(String[] args) {
		
		Object location = null;
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response r = target.path("rest").path("holamundo").request().accept(MediaType.TEXT_PLAIN)
				.get(Response.class);
		System.out.println("Status: " + r.getStatus());
		String valor = r.readEntity(String.class);
		System.out.println("Entity: " + valor);
		if(r.getHeaders().containsKey("Location")) {
			location = r.getHeaders().get("Location").get(0);
			System.out.println("Location: " + location.toString());
		}

		System.out.println(target.path("rest").path("holamundo").request().accept(MediaType.TEXT_XML)
				.get(String.class));
		System.out.println(target.path("rest").path("holamundo/saluda/Javier/Soriano").queryParam("apellido2", "Camino").request().accept(MediaType.TEXT_HTML)
				.get(String.class));		
		
//		System.out.println(target.path("rest").path("holamundo").request().accept(MediaType.APPLICATION_JSON)
//		.get(String.class));

	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/REST1bHolaMundo/").build();
	}
}


