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
import jersey.booknet.database.restRecursos;

// ReadBook resource class. Key -> read_id
@Path("/users")
public class FullResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String read_id;

    /*
     * String current_date = "20220000"; // Fecha actual provisional int default_int
     * = -1;
    */

    // Returns all read_books from our friends using theese filters -> rating, auth_name, category
    @Path("{user_id}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getRecomendedBooks( @PathParam("user_id") int user_id,
                                        @QueryParam("user_rating")  @DefaultValue("0") int user_rating,
                                        @QueryParam("author_name")  @DefaultValue("null") String author_name,
                                        @QueryParam("category") @DefaultValue("null") String category){
        ArrayList <Book> books = filtrarLibrosRecomendados(user_id, user_rating, auth_name, category);
        if (books.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(books).build();
    }

    
    // Returns the last books read by our frineds with theese filters -> date, first_row, last_row
    @Path("{user_id}")
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getLastReadBooksFriends(   @PathParam("user_id") int user_id,
                                        @QueryParam("date") @DefaultValue("20220000") String date,
                                        @QueryParam("first") @DefaultValue("-1") int first,
                                        @QueryParam("last") @DefaultValue("-1") int last){
        ArrayList <Book> books = getLastReadBooks(user_id, Integer.parseInt(date), first, last);
        if (books.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(books).build();
    }
}