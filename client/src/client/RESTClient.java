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
		 
		 //CREAR USER
	    ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    WebTarget target = client.target(getBaseURI());
	    Response r;
	    
	    User u = new client.User();
	    u.setNick("pepe");
	    u.setEdad(19);
	    u.setEmail("pepe@mail.com");
	    r = target.path("api").path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(u),Response.class);
	    System.out.println("CREAR USER:"+r.getStatus());
		System.out.println("Location: " + r.getHeaders().get("Location").get(0).toString());
		
		
		 //VER DATOS DE UN USER
	    System.out.println("DATOS DE UN USER:"+target.path("api").path("users/1").request()
	            .accept(MediaType.APPLICATION_JSON).get(String.class));
	    
	    //ACTUALIZAR DATOS DE UN USER
	    User u2 = new client.User();
	    u2.setEdad(25);
	    u2.setEmail("pepe23@mail.com");
	    System.out.println("ACTUALIZAR DATOS DE UN USER:"+target.path("api").path("users/1").request()
	           .accept(MediaType.APPLICATION_JSON).put(Entity.json(u2),Response.class).getStatus());
	           
	    //BORRAR USER
	    r = target.path("api").path("users/4").request().delete();
	    System.out.println("BORRAR DATOS DE UN USER:"+r.getStatus());
	    
	    // Obtener usuarios existentes
	    
	    System.out.println("LISTADO USUARIOS:"+target.path("api").path("users/").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
		           
	    // Obtener usuarios existentes POR NOMBRE
	    System.out.println("LISTADO USERS POR NOMBRE:"+target.path("api").path("users/").queryParam("user_name", "user").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
		           
	    //Añadir lectura a un usuario con calificacion 
	    ReadBook readBook = new ReadBook();
	    readBook.setIsbn(3);
	    readBook.setRating(8);
	    readBook.setReadDate(20201102);//yyyymmdd
	    Response r1 = target.path("api").path("users/3").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(readBook),Response.class);
	    System.out.println("CREAR LECTURA:"+r1.getStatus());
		System.out.println("Location: " + r1.getHeaders().get("Location").get(0).toString());
		
		//eliminar lectura usuario 3 isbn 3
		r = target.path("api").path("users/3/3").request().delete();
	    System.out.println("BORRAR LECTURA DE USUSARIO:"+r.getStatus());
		
		//editar lectura libro usuario
	    ReadBook readBook2 = new ReadBook();
	    readBook2.setRating(8);
	    readBook2.setReadDate(20210730);//yyyymmdd
	    System.out.println("ACTUALIZAR DATOS DE UNA LECTURA:"+target.path("api").path("users/1/2").request()
	           .accept(MediaType.APPLICATION_JSON).put(Entity.json(readBook2),Response.class).getStatus());
		
	    // consulta libros leidos 
	    System.out.println("LISTADO LIBROS:"+target.path("api").path("users/1/readings").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // consulta limitada por fecha
	    System.out.println("LISTADO LIBROS LIMITE FECHA:"+target.path("api").path("users/1/readings").queryParam("date",20210101).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //consulta limitada por fecha y numero listado
	    System.out.println("LISTADO LIBROS LIMITE FECHA Y CANTIDAD:"+target.path("api").path("users/1/readings").queryParam("date",20210101).queryParam("from",1).queryParam("to",2).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //Añadir amigo
	    Friendship f = new client.Friendship();
	    f.setId_user2(2);
	    r = target.path("api").path("users/3/friends").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(f),Response.class);
	    System.out.println("AÑADIR AMIGO:"+r.getStatus());
		System.out.println("Location: " + r.getHeaders().get("Location").get(0).toString());
	 	
	    // borrar amistad
	    
	    r = target.path("api").path("users/3/friends").queryParam("friend",1).request().accept(MediaType.APPLICATION_JSON).delete();
	    System.out.println("BORRAR DATOS DE UN USER:"+r.getStatus());
	    
	 // consulta amigos
	    System.out.println("LISTADO AMIGOS:"+target.path("api").path("users/1/friends").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // consulta limitada por nombre
	   System.out.println("LISTADO AMIGOS LIMITE NOMBRE:"+target.path("api").path("users/1/friends").queryParam("friend_name","user2").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //consulta limitada por nombre y numero listado
	    System.out.println("LISTADO AMIGOS LIMITE NOMBRE Y CANTIDAD:"+target.path("api").path("users/1/friends").queryParam("friend_name","user").queryParam("from",1).queryParam("to",2).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    
	   //consulta libros amigos
	    System.out.println("LISTADO LIBROS AMIGOS:"+target.path("api").path("users/1/friendsreadings").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // consulta libros amigos  limitada por fecha
	   System.out.println("LISTADO LIBROS AMIGOS LIMITE FECHA:"+target.path("api").path("users/1/friendsreadings").queryParam("date",20201202).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //consulta libros amigos limitada por fecha y numero listado
	    System.out.println("LISTADO LIBROS AMIGOS LIMITE FECHA Y CANTIDAD:"+target.path("api").path("users/1/friendsreadings").queryParam("date",20200102).queryParam("from",1).queryParam("to",2).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    
	    // consulta libros recomendados
	    System.out.println("LISTADO LIBROS RATING:"+target.path("api").path("users/1/friendsrecomendations").queryParam("rating",4).request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // consulta libros recomendsados limitada por Autor
	   System.out.println("LISTADO LIBROS RATING LIMITE AUTOR:"+target.path("api").path("users/1/friendsrecomendations").queryParam("rating",2).queryParam("author","autor2").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    //consulta libros RECOMENDADOS limitada por autor y categoria
	    System.out.println("LISTADO LIBROS AMIGOS LIMITE AUTOR Y CATEGORIA:"+target.path("api").path("users/1/friendsrecomendations").queryParam("rating",4).queryParam("author","autor").queryParam("category","categoria1").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));
	    // consulta aplicacion movil
	    System.out.println("LISTADO APPDATA:"+target.path("api").path("users/1/appData").request()
		           .accept(MediaType.APPLICATION_JSON).get(String.class));

	}
	 private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://localhost:8080/REST-API/").build();
		  }

}
