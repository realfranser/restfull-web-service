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
	@Path("{id_usuario}")// editar datos usuario 3 - OK
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateData(@PathParam("id_usuario") int user_id, jersey.booknet.database.User u) {
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
	public Response deleteReadBook(@PathParam("user_id") int user_id,@PathParam("isbn")String isbnString) {
			int isbn = Integer.parseInt(isbnString);
			if(isbnString == "") {
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("NOT A VALID ISBN").build();
			}
			boolean aux = rec.removeReadBook(user_id, isbn);
			if(!aux) { // hay que cambiar esto yo creo , lo elimina pero no se que deberia devolver
				return Response.status(Response.Status.PRECONDITION_FAILED).entity("NO SE PUDO ELIMINAR EL LIBRO").build();
			}
			return Response.status(Response.Status.OK).build();
	}
	

	
	
	
	
	
	
	/////// end_lectura //////////////
	
	@POST
	@Path("{user_id}/friends") // crear amistad entre usuarios - OK
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
	@Path("{user_id}/friends") // eliminar amistad - OK !!!! comprobar que deberia devolver no lo tengo claro
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
	
	
	
	
	

}
