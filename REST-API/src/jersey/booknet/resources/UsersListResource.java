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

@Path("/users")
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
  public List<User> getUserssBrowser() {
    List<User> users = new ArrayList<User>();
    //users.addAll(UserDao.instance.getModel().values());
    users.addAll(UserDao.getInstance().getModel().values());
    
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

  // Debe recibir estructuras de tipo XML de tipo user para usarlas
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void newUser(@FormParam("id") String id,
      @FormParam("nick") String nick,
      @FormParam("read_books") List<String> read_books,// <Strings> (isbn) o <Book> (objeto Book)
      @FormParam("email") String email,
      @FormParam("born_date") String born_date,
      @FormParam("reg_date") String reg_date,
      @Context HttpServletResponse servletResponse) throws IOException {
    User user = new User(id, nick, read_books, email, born_date, reg_date);
    // Here we initialize atributes calling set functions in class user
    if (nick != null) {
      user.setNick(nick);
    }
    //UserDao.instance.getModel().put(id, nick); more atributes?
    UserDao.getInstance().getModel().put(id, nick);
    servletResponse.sendRedirect("../create_user.html");
  }

} 