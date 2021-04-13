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


// Book resource class. Key -> isbn
@Path("books")
public class BookResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  int isbn;
 
  /* GET call is not necesary in the first implementation
  // Web API     
  @Path("{isbn}")
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getBook(@PathParam("isbn") int isbn) {
	  Response res;
	  Book book;
	  if(BookDao.getInstance().getModel().containsKey(isbn)) {
		  book= BookDao.getInstance().getModel().get(isbn);
	      res = Response.ok(book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + Integer.toString(isbn) +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  // Para testing (Navegador como cliente)
  @Path("{book}")
  @GET
  @Produces(MediaType.TEXT_XML)
  public Response getTodoHTML(@PathParam("book") int isbn) {
	  Response res;
	  Book book;
	  if(BookDao.getInstance().getModel().containsKey(isbn)) {
		  book = BookDao.getInstance().getModel().get(isbn);
	      res = Response.ok(book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + Integer.toString(isbn) +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  */
  
  @PUT
  @Path("{isbn}")
  @Consumes(MediaType.APPLICATION_XML)
  public Response putBook(JAXBElement<Book> book) {
    Book c = book.getValue();
    return putAndGetResponse(c);
  }
  
  @DELETE
  @Path("{isbn}")
  public Response deleteBook(@PathParam("isbn") int isbn) {
	  Response res;
	  if(BookDao.getInstance().getModel().containsKey(isbn)) {
		  BookDao.getInstance().getModel().remove(isbn);
	      res = Response.ok().build();
	  } else {
		  //throw new RuntimeException("Delete: Tarea con isbn " + Integer.toString(isbn) +  " no encontrada");
	      res = Response.noContent().build();
	  }
	  return res;
  }

  // Change -> difference between put and post?
  @POST
  @Path("{isbn}")
  @Consumes(MediaType.APPLICATION_XML)
  public Restponse postBook(JAXBElement<Book> book) {
    Book c = book.getValue();
    return putAndGetResponse(c);
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

