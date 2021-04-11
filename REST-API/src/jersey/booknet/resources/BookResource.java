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


	
@Path("books")
public class BookResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;
  
  // Web API     
  @Path("{book}")
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getBook(@PathParam("book") String id) {
	  Response res;
	  Book book;
	  if(BookDao.getInstance().getModel().containsKey(id)) {
		  book= BookDao.getInstance().getModel().get(id);
	      res = Response.ok(book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con id " + id +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  // Para testing (Navegador como cliente)
  @Path("{book}")
  @GET
  @Produces(MediaType.TEXT_XML)
  public Response getTodoHTML(@PathParam("book") String id) {
	  Response res;
	  Book book;
	  if(BookDao.getInstance().getModel().containsKey(id)) {
		  book = BookDao.getInstance().getModel().get(id);
	      res = Response.ok(book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con id " + id +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  @PUT
  @Path("{book}")
  @Consumes(MediaType.APPLICATION_XML)
  public Response putTodo(JAXBElement<Book> book) {
    Book c = book.getValue();
    return putAndGetResponse(c);
  }
  
  @DELETE
  @Path("{book}")
  public Response deleteTodo(@PathParam("book") String id) {
	  Response res;
	  if(BookDao.getInstance().getModel().containsKey(id)) {
		  BookDao.getInstance().getModel().remove(id);
	      res = Response.ok().build();
	  } else {
		  //throw new RuntimeException("Delete: Tarea con id " + id +  " no encontrada");
	      res = Response.noContent().build();
	  }
	  return res;
  }
  
  private Response putAndGetResponse(Book book) {
    Response res;
    if(BookDao.getInstance().getModel().containsKey(book.getIsbn())) {
      res = Response.noContent().build();
    } else {
      BookDao.getInstance().getModel().put(book.getIsbn(), book);
      res = Response.created(uriInfo.getAbsolutePath()).header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
    return res;
  }
} 

