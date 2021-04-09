import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import jaxbserialization.JAXBModel;

public class Test {

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		System.out.println(target.path("rest").path("jaxb").request().
				accept(MediaType.TEXT_XML).get(String.class));
		System.out.println(target.path("rest").path("jaxb").request().
				accept(MediaType.APPLICATION_XML).get(String.class));
		JAXBModel m = target.path("rest").path("jaxb").request().
				accept(MediaType.APPLICATION_XML).get(JAXBModel.class);
		System.out.println(m.getAtrib1());
		System.out.println(target.path("rest").path("jaxb").request().
				accept(MediaType.APPLICATION_JSON).get(String.class));	
		//System.out.println(target.path("rest").path("jaxb").request().
		//		accept(MediaType.APPLICATION_JSON).get(JAXBModel.class).getAtrib1());
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/REST2JAXRS/").build();
	}
}