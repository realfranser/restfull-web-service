package jersey.booknet.database;


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
	
	
	
	

}
