package jersey.booknet.database;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.ArrayList;

// añadir al sql dump permisos de admin para user booknet
@Path("/users")
public class resources {
	@Context
	private UriInfo uri;
	private static restRecursos rec = new restRecursos();
	
	public resources() {
		
	}
	
	@GET
	@Path("{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("user_id") String user_id) {
		User u = new User();
		int id = Integer.parseInt(user_id);
		u = rec.getUser(id);
		if(u!=null) 
		return Response.ok(u).build();
		else
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUsuario(User u) {
		try {
			User aux = rec.insertUser(u);
			String location = uri.getAbsolutePath() + "/" +aux.getId();
			return Response.status(Response.Status.CREATED).entity(aux).header("Location", location.toString()).build();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	@DELETE
	@Path("{user_id}")
	public Response deleteUser(@PathParam("user_id") int id_usuario) {
		
			boolean aux = rec.removeUser(id_usuario);
			if(!aux) { // hay que cambiar esto yo creo , lo elimina per no se que deberia devolver
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("EL USUARIO TIENE CUENTAS ABIERTAS").build();
			}
			return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	@POST
	@Path("{user_id}/friends")
	@Consumes(MediaType.APPLICATION_JSON)// cambiar bbdd para que no pueda haber varias amistades iguales y que se añada en dos sentido
	public Response addFriend(@PathParam("user_id") String user_id ,Friendship friendship) {
		try {
			int user_id_int = Integer.parseInt(user_id);
			friendship.setId_user1(user_id_int);
			Friendship aux = rec.addFriendship(friendship);
			String location = uri.getAbsolutePath() + "/" +aux.getFriendship_id();
			return Response.status(Response.Status.CREATED).entity(aux).header("Location", location.toString()).build();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	@DELETE
	@Path("{user_id}/friends")
	@Consumes(MediaType.APPLICATION_JSON) // comprobar que devuelbe false o lo que sea si no exite 
	public Response removeFriend(@PathParam("user_id") String user_id ,Friendship friendship) {
		try {
			int user_id_int = Integer.parseInt(user_id);
			friendship.setId_user1(user_id_int);
			boolean state = rec.removeFriendship(friendship);
			String location = uri.getAbsolutePath() + "/" +state;
			return Response.status(Response.Status.CREATED).entity(state).header("Location", location.toString()).build();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}

	// Returns all read_books from our friends using theese filters -> rating, auth_name, category
	@Path("{user_id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRecomendedBooks( @PathParam("user_id") String user_id,
										@QueryParam("user_rating")  @DefaultValue("0") String user_rating,
										@QueryParam("author_name")  @DefaultValue("null") String author_name,
										@QueryParam("category") @DefaultValue("null") String category){
		ArrayList <Book> books = rec.filtrarLibrosRecomendados(Integer.parseInt(user_id), Integer.parseInt(user_rating), author_name, category);
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
		ArrayList <Book> books = rec.getLastReadBooks(Integer.parseInt(user_id), Integer.parseInt(date), Integer.parseInt(first), Integer.parseInt(last));
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
		ArrayList <Integer> friends_ids = rec.getFriendsId(Integer.parseInt(user_id), Integer.parseInt(first), Integer.parseInt(last));
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
		FullUser full_user = rec.getFullUserInfo(Integer.parseInt(user_id));
		if (full_user == null) return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(full_user).build();
	}	
}
