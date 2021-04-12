package jersey.booknet.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import jersey.booknet.dao.BookDao;
import jersey.booknet.model.Book;


// Book resource class. Key -> user_id
@Path("users/{username}/friends")
public class BookResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;
 
  // Devuelve la lista de amigos al usuario (para uso desde navegador)
  @GET
  @Produces(MediaType.TEXT_XML)
  public List<User> getFriends() {
    List<User> users = new ArrayList<User>();
    //users.addAll(UserDao.instance.getModel().values());
    users.addAll(UserDao.getInstance().getModel().values()); // No se de donde coger los amigos
    
    return users;
  }

  // Devuelve a las aplicaciones cliente la lista de clientes
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public List<User> getUsers() {
    List<User> users = new ArrayList<User>();
    //users.addAll(UserDao.instance.getModel().values());
    users.addAll(UserDao.getInstance().getModel().values());

    return users;
  }

  /*
  // Devuelve el número de usuarios registrados
  @GET
  @Path("count")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCount() {
    //int count = TodoDao.instance.getModel().size();
    int count = TodoDao.getInstance().getModel().size();

    return String.valueOf(count);
  }
  */

  
  @DELETE
  @Path("{id}")
  public Response deleteBook(@PathParam("id") String id) {
	  Response res;
	  if(UserDao.getInstance().getModel().containsKey(id)) {
		  UserDao.getInstance().getModel().remove(id);
	      res = Response.ok().build();
	  } else {
		  //throw new RuntimeException("Delete: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.noContent().build();
	  }
	  return res;
  }

  // Change -> difference between put and post?
  @POST
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_XML)
  public Restponse postBook(JAXBElement<User> user) {
    User c = book.getValue();
    return putAndGetResponse(c);
  }
  
  private Response putAndGetResponse(Book id) {
    Response res;
    if(UserDao.getInstance().getModel().containsKey(user.getId())) {
      res = Response.noContent().build();
    } else {
      UserDao.getInstance().getModel().put(user.getId(), id); // put(1, 2), arg 2 ?
      res = Response.created(uriInfo.getAbsolutePath()).header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
    return res;
  }
} 

