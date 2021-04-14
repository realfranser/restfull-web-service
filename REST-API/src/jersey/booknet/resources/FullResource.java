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
    private static restRecursos rec = new restRecursos();
    @Context
    Request request;
    String read_id;

    public class FullResource(){}
    /*
     * String current_date = "20220000"; // Fecha actual provisional int default_int
     * = -1;
    */

    // Returns all read_books from our friends using theese filters -> rating, auth_name, category
    @Path("{user_id}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecomendedBooks( @PathParam("user_id") String user_id,
                                        @QueryParam("user_rating")  @DefaultValue("0") String user_rating,
                                        @QueryParam("author_name")  @DefaultValue("null") String author_name,
                                        @QueryParam("category") @DefaultValue("null") String category){
        ArrayList <Book> books = filtrarLibrosRecomendados(Integer.parseInt(user_id), Integer.parseInt(user_rating), auth_name, category);
        if (books.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(books).build();
    }

    
    // Returns the last books read by our frineds with theese filters -> date, first_row, last_row
    @Path("{user_id}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getLastReadBooksFriends(   @PathParam("user_id") String user_id,
                                        @QueryParam("date") @DefaultValue("20220000") String date,
                                        @QueryParam("first") @DefaultValue("-1") String first,
                                        @QueryParam("last") @DefaultValue("-1") String last){
        ArrayList <Book> books = getLastReadBooks(Integer.parseInt(user_id), Integer.parseInt(date), Integer.parseInt(first), Integer.parseInt(last));
        if (books.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(books).build();
    }

    // Returns the friends of a user
    @Path("{user_id}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFriendsInfo( @PathParam("user_id") String user_id,
                                    @QueryParam("first") @DefaultValue("-1") String first,
                                    @QueryParam("last") @DefaultValue("-1") String last){
        ArrayList <Integers> friends_ids = getFriendsId(Integer.parseInt(user_id), Integer.parseInt(first), Integer.parseInt(last));
        ArrayList <User> friends_profiles = new ArrayList<User>();
        // Call getUser to get the info of each friend
        for (int friend_id : friends_ids){
            friends_profiles.add(rec.getUser(friend_id));
        }
        // Check for empty list (Yo have no friends xDD)
        if (friends_profiles.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(friends_profiles).build();
    }

    // Returns the full info of a user (Basic info + last read book + number of friends + last book read by its friends)
    @Path("{user_id")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFullInfo( @PathParam("user_id") String user_id ){
        FullUser full_user = rec.getFullUserInfo(user_id);
        if (full_user == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(full_user).build();
    }
}