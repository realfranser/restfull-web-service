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
@Path("users")
public class BookResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;
 
  // Web API     
  @Path("{id}")
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getBook(@PathParam("id") String id) {
	  Response res;
	  User user;
	  if(UserDao.getInstance().getModel().containsKey(id)) {
		  user = UserDao.getInstance().getModel().get(id);
	      res = Response.ok(user).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  // Para testing (Navegador como cliente)
  @Path("{id}")
  @GET
  @Produces(MediaType.TEXT_XML)
  public Response getTodoHTML(@PathParam("id") String id) {
	  Response res;
    User user;
	  if(UserDao.getInstance().getModel().containsKey(id)) {
		  user = UserDao.getInstance().getModel().get(id);
	      res = Response.ok(user).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  */
  
  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_XML)
  public Response putBook(JAXBElement<User> user) {
    User c = user.getValue();
    return putAndGetResponse(c);
  }
  
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

