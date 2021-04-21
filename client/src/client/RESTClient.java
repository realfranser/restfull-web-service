package client;


import java.io.UnsupportedEncodingException;
import java.net.URI;
//import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
//import javax.ws.rs.client.config.DefaultClientConfig;
//import javax.ws.rs.representation.Form;

public class RESTClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 /*
		 //CREAR USER
	    ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    WebTarget target = client.target(getBaseURI());
	    User u = new client.User();
	    u.setNick("pepe");
	    u.setEdad(19);
	    u.setEmail("pepe@mail.com");
	    Response r = target.path("api").path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(u),Response.class);
	    System.out.println("CREAR USUARIO:"+r.getStatus());
		System.out.println("Location: " + r.getHeaders().get("Location").get(0).toString());
		
		
		 //VER DATOS DE UN USER
	    System.out.println("DATOS DE UN USUARIO:"+target.path("api").path("users/1").request()
	            .accept(MediaType.APPLICATION_JSON).get(String.class));
	    
	    //ACTUALIZAR DATOS DE UN USER
	    User u2 = new client.User();
	    u2.setEdad(25);
	    u2.setEmail("pepe23@mail.com");
	    System.out.println("ACTUALIZAR DATOS DE UN CLIENTE:"+target.path("api").path("users/1").request()
	           .accept(MediaType.APPLICATION_JSON).put(Entity.json(u2),Response.class).getStatus());
	    //BORRAR USER
	    r = target.path("api").path("users/4").request().delete();
	    System.out.println("BORRAR DATOS DE UN CLIENTE:"+r.getStatus());
	    
	    // Obtener usuarios existentes
	    
	    System.out.println("LISTADO USUARIOS:"+target.path("api").path("users/").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // Obtener usuarios existentes POR NOMBRE
	    System.out.println("LISTADO USUARIOS POR NOMBRE:"+target.path("api").path("users/").queryParam("user_name", "user").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //Añadir lectura a un usuario con calificacion 
	    ReadBook readBook = new ReadBook();
	    readBook.setIsbn(3);
	    readBook.setRating(8);
	    readBook.setReadDate(20201102);//yyyymmdd
	    Response r1 = target.path("api").path("users/3").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(readBook),Response.class);
	    System.out.println("CREAR Lectura:"+r1.getStatus());
		System.out.println("Location: " + r1.getHeaders().get("Location").get(0).toString());
		//eliminar lectura usuario 3 isbn 3
		r = target.path("api").path("users/3/3").request().delete();
	    System.out.println("BORRAR DATOS DE UN CLIENTE:"+r.getStatus());
		*/
		
	    

	}
	 private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://localhost:8080/REST-API/").build();
		  }

}
