package jersey.booknet.database;


import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
// añadir al sql dump permisos de admin para user booknet
@Path("/users")
public class resources {
	@Context
	private UriInfo uri;
	private static restRecursos rec = new restRecursos();
	
	public resources() {
		
	}
	
	///// users //////////
	@POST // añádir usuario a la red  1 - OK   
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
	
	@GET // devuelve datos de un usuario 2 - OK
	@Path("{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("user_id") String user_id) {
		User u = new User();
		int id = Integer.parseInt(user_id);
		try {
			u = rec.getUser(id);
			return Response.ok(u).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	@PUT
	@Path("{user_id}")// editar datos usuario 3 - OK
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateData(@PathParam("user_id") int user_id, jersey.booknet.database.User u) {
		try {
			u.setId(user_id);
			jersey.booknet.database.User aux = rec.changeUser(u);
			String location = uri.getAbsolutePath() + "";
			return Response.status(Response.Status.OK).entity(aux).header("Location", location.toString()).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	@DELETE // eliminar ususario de la red  4 - OK
	@Path("{user_id}")
	public Response deleteUser(@PathParam("user_id") int user_id) {
		
			boolean aux = rec.removeUser(user_id);
			if(!aux) { // hay que cambiar esto yo creo , lo elimina pero no se que deberia devolver
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("NO SE PUDO ELIMINAR EL USUARIO").build();
			}
			return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	@GET // con este get vale para cuando te dan usuario o no (punto 5 completo de las operaciones) 5 - Ok
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios(@QueryParam("user_name") @DefaultValue("")String user_name) {
		try {
			ArrayList<jersey.booknet.database.User> users = new ArrayList<jersey.booknet.database.User>();
			users = rec.getUsersWithName(user_name);
			return Response.ok(users).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	//////// end_users ////////////////
	
	//////// lectura /////////////////
	
	@POST // añádir libro a usuario a la red  6 - OK  
	@Path("{user_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReadBook(@PathParam("user_id") int user_id,ReadBook readbook) {
		try {
			ReadBook aux = rec.readBook(user_id, readbook);
			String location = uri.getAbsolutePath() + "/" +aux.getId();
			return Response.status(Response.Status.CREATED).entity(aux).header("Location", location.toString()).build();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD, isbn no encontrado probablemente").build();
		}
	}
	
	@DELETE // eliminar libro de ususario de la red  7 - OK  //se podria añádir un metodo que cheque si existe antes de ejecutar lo mismo para los ususarios y amistades
	@Path("{user_id}/{isbn}")
	public Response deleteReadBook(@PathParam("user_id") int user_id,@PathParam("isbn")int isbn) {
			//int isbn = Integer.parseInt(isbnString);
			if(isbn == 0) {
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("NOT A VALID ISBN").build();
			}
			boolean aux = rec.removeReadBook(user_id, isbn);
			if(!aux) { // hay que cambiar esto yo creo , lo elimina pero no se que deberia devolver
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("NO SE PUDO ELIMINAR EL LIBRO").build();
			}
			return Response.status(Response.Status.OK).build();
	}
	

	@PUT
	@Path("{user_id}/{isbn}")// editar datos libro leido ususario 8 - OK
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReadBook(@PathParam("user_id") int user_id,@PathParam("isbn") int isbn, ReadBook read_book) {
		try {
			read_book.setIsbn(isbn);
			ReadBook aux = rec.editReadBook(user_id, read_book);
			String location = uri.getAbsolutePath() + "";
			return Response.status(Response.Status.OK).entity(aux).header("Location", location.toString()).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	@GET // consultar ultimos libros leidos por usuario 9 - Ok
	@Path("{user_id}/readings")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response getReadBooks(@PathParam("user_id") int user_id,@QueryParam("date") @DefaultValue("99999999")String date ,@QueryParam("from") @DefaultValue("0")int from ,@QueryParam("to") @DefaultValue("10")int to) {
		try {
			ArrayList<ReadBook> read_books = new ArrayList<ReadBook>();
			int dateInt = Integer.parseInt(date);
			read_books = rec.getReadBooks(user_id, dateInt, from, to);
			return Response.ok(read_books).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	
	
	
	
	/////// end_lectura //////////////
	
	@POST
	@Path("{user_id}/friends") // crear amistad entre usuarios 10 - OK
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
	@Path("{user_id}/friends") // eliminar amistad 11 - OK !!!! comprobar que deberia devolver no lo tengo claro
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
	
	@GET // consultar AMIGOS DE  usuario 12 - Ok
	@Path("{user_id}/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFriendship(@PathParam("user_id") int user_id,@QueryParam("friend_name") @DefaultValue("")String friend_name ,@QueryParam("from") @DefaultValue("0")int from ,@QueryParam("to") @DefaultValue("10")int to) {
		try {
			ArrayList<jersey.booknet.database.User> friends = new ArrayList<jersey.booknet.database.User>();
			
			friends = rec.listFriends(user_id, friend_name, from, to);
			return Response.ok(friends).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	@GET// consultar lecturas de AMIGOS DE  usuario 13 - Ok
	@Path("{user_id}/friendsreadings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReadingsFriends(@PathParam("user_id") int user_id,@QueryParam("date") @DefaultValue("99999999")String date ,@QueryParam("from") @DefaultValue("0")int from ,@QueryParam("to") @DefaultValue("10")int to) {
		try {
			ArrayList<ReadBook> readingsFriends = new ArrayList<ReadBook>();
			int dateInt = Integer.parseInt(date);
			readingsFriends = rec.getReadBooks(user_id,dateInt, from, to);
			return Response.ok(readingsFriends).build();
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	@GET 
	@Path("{user_id}/friendsrecomendations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFriendsRecomendations(@PathParam("user_id") int user_id,@QueryParam("rating") @DefaultValue("0")String rating,@QueryParam("author") @DefaultValue("")String author,@QueryParam("category") @DefaultValue("")String category) {
		try {
			ArrayList<Book> recomendationsFriends = new ArrayList<Book>();
			int ratingInt = Integer.parseInt(rating);
			recomendationsFriends = rec.getFriendsRecomendations(user_id,ratingInt, author,category);
			return Response.ok(recomendationsFriends).build();
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
		}
	}
	
	
	

}
