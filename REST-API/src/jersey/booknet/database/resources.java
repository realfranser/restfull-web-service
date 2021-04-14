package jersey.booknet.database;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

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
		jersey.booknet.database.User u = new jersey.booknet.database.User();
		int id = Integer.parseInt(user_id);
		u = rec.getUser(id);
		if(u!=null) 
		return Response.ok(u).build();
		else
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR ACCESO BBDD").build();
	}
	
	
	

}
