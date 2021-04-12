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


// ReadBook resource class. Key -> read_id
@Path("/users/{username}/read_books")
public class ReadBooksResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String read_id;
 
  // Web API     
  @Path("{read_id}")
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getBook(@PathParam("read_id") String read_id) {
	  Response res;
	  ReadBook read_book;
	  if(ReadBookDao.getInstance().getModel().containsKey(read_id)) {
		  read_book = ReadBookDao.getInstance().getModel().get(isbn);
	      res = Response.ok(read_book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  // Para testing (Navegador como cliente)
  @Path("{read_id}")
  @GET
  @Produces(MediaType.TEXT_XML)
  public Response getTodoHTML(@PathParam("read_id") String read_id) {
	  Response res;
	  ReadBook read_book;
	  if(ReadBookDao.getInstance().getModel().containsKey(read_id)) {
		  read_book = ReadBookDao.getInstance().getModel().get(read_id);
	      res = Response.ok(read_book).build();
	  } else {
		  //throw new RuntimeException("Get: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.status(Response.Status.NOT_FOUND).build();
	  }
	  return res;
  }
  
  @PUT
  @Path("{read_id}")
  @Consumes(MediaType.APPLICATION_XML)
  public Response putBook(JAXBElement<ReadBook> read_book) {
    ReadBook c = read_book.getValue();
    return putAndGetResponse(c);
  }
  
  @DELETE
  @Path("{read_id}")
  public Response deleteBook(@PathParam("read_id") String read_id) {
	  Response res;
	  if(ReadBookDao.getInstance().getModel().containsKey(read_id)) {
		  ReadBookDao.getInstance().getModel().remove(read_id);
	      res = Response.ok().build();
	  } else {
		  //throw new RuntimeException("Delete: Tarea con isbn " + isbn +  " no encontrada");
	      res = Response.noContent().build();
	  }
	  return res;
  }

  // Change -> difference between put and post?
  @POST
  @Path("{read_id")
  @Consumes(MediaType.APPLICATION_XML)
  public Restponse postBook(JAXBElement<ReadBook> read_book) {
    ReadBook c = read_book.getValue();
    return putAndGetResponse(c);
  }
  
  private Response putAndGetResponse(ReadBook read_book) {
    Response res;
    if(ReadBookDao.getInstance().getModel().containsKey(read_book.getIsbn())) {
      res = Response.noContent().build();
    } else {
      ReadBookDao.getInstance().getModel().put(read_book.getIsbn(), read_book);
      res = Response.created(uriInfo.getAbsolutePath()).header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
    return res;
  }
} 

