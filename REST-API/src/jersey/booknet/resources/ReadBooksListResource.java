package jersey.booknet.resources;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import jersey.todo.dao.TodoDao;
import jersey.todo.model.Todo;

// Is this okay?
@Path("/users/{username}/read_books")
public class ReadBooksListResource {

  // Permite instertar objetos de contexto en la clase,
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  // Devuelve la lista de libros al usuario (para uso desde navegador)
  @GET
  @Produces(MediaType.TEXT_XML)
  public List<Book> getBooksBrowser() {
    List<Book> books = new ArrayList<Book>();
    //todos.addAll(TodoDao.instance.getModel().values());
    books.addAll(BookDao.getInstance().getModel().values());
    
    return books;
  }

  // Devuelve a las aplicaciones cliente la lista de todos
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public List<Book> getBooks() {
    List<Book> books = new ArrayList<Book>();
    //books.addAll(BookDao.instance.getModel().values());
    books.addAll(BookDao.getInstance().getModel().values());

    return books;
  }

  /*
  // Devuelve el número de tareas registradas
  @GET
  @Path("count")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCount() {
    //int count = TodoDao.instance.getModel().size();
    int count = TodoDao.getInstance().getModel().size();

    return String.valueOf(count);
  }
  */

  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void newBook(@FormParam("isbn") String isbn,
      @FormParam("book") String name,
      @FormParam("auth_name") String auth_name,
      @FormParam("category") String category,
      @Context HttpServletResponse servletResponse) throws IOException {
    Book book = new Book(isbn, name, auth_name, category);
    if (description != null) {
      name.setName(name);
    }
    //BookDao.instance.getModel().put(isbn, book);
    BookDao.getInstance().getModel().put(isbn, book);
    servletResponse.sendRedirect("../create_book.html");
  }

} 