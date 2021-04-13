package clase.recursos.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.catalina.User;
import org.apache.naming.NamingContext;
import org.glassfish.jersey.inject.hk2.JerseyClassAnalyzer;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import jersey.booknet.model.*;

import java.util.ArrayList;
import java.util.Date;

@Path("/booknet")
public class restRecursos {
	
	private Connection conn;
	private java.sql.PreparedStatement prepStmt;
	private ResultSet rs;
	private UriInfo uri;
	
	
	public void connect() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			String serverAddress = "localhost:3306";
			String user = "booknet";
			String pwd = "booknet";
			String url = "jdbc:mysql://"+serverAddress;
			conn = DriverManager.getConnection(url,user, pwd);
		}
		catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e ){
			e.printStackTrace();
		}
		}
	public boolean insertUser(jersey.booknet.model.User u) throws SQLException {
		 if(conn == null) {
			 connect();
		 }
		 try {
		 prepStmt = conn.prepareStatement( "INSERT INTO booknet.users ('user_name','email','edad') VALUES (?,?,?);");
		 prepStmt.setString(1,u.getNick());
		 prepStmt.setString(2,u.getEmail());
		 prepStmt.setInt(3,u.getEdad());
	
		 prepStmt.executeUpdate();
		 conn.commit();
		 return true;
		 }
		 catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
			 return false;
		}
		}
	public ArrayList<jersey.booknet.model.User> getUsers() {
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<jersey.booknet.model.User> users = new ArrayList<jersey.booknet.model.User>();
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users ");
		 rs = prepStmt.executeQuery();
		 conn.commit();
		 rs.next();
		 do {
			 users.add(new jersey.booknet.model.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
		 }
		 while(rs.next());
		 return users;
		 }
		 catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
			 return null;
		}
	}
	
	public jersey.booknet.model.User getUser(String nick){
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_name = ?; ");
			 prepStmt.setString(1,nick);
			 rs = prepStmt.executeQuery();
			 conn.commit();
			 rs.next();
			 jersey.booknet.model.User user = new jersey.booknet.model.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
			 return user;
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public boolean changeUser(jersey.booknet.model.User user) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "UPDATE booknet.user SET email=? edad=? WHERE user_name = ?; ");
			 prepStmt.setString(1,user.getNick());
			 prepStmt.setString(2,user.getEmail());
			 prepStmt.setInt(3,user.getEdad());
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}
	
	public boolean removeUser(String nick) {
		
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.user WHERE user_name=?");
			 prepStmt.setString(1,nick);
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}
	public boolean read_book(String nick, int isbn , int fecha , int calificacion) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.user WHERE user_name=?");
			 prepStmt.setString(1,nick);
			 rs = prepStmt.executeQuery();
			 conn.commit();
			 rs.next();
			 int user_id = rs.getInt(1);
			 
			 prepStmt = conn.prepareStatement( "INSERT INTO booknet.read_books ('user_id','isbn','user_rating','read_date') VALUES (?,?,?,?);");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,isbn);
			 prepStmt.setInt(3,calificacion);
			 prepStmt.setInt(3,fecha);
			 
		
			 prepStmt.executeUpdate();
			 conn.commit();
			 
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 	 
	}

	public ArrayList<Book> filtrarLibrosRecomendados(int user_id, int calificacion_minima, String nombre_autor, String categoria){
		if(conn == null) connect();
		try {
			/* Creo que no hace falta obtener el user
			prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_name = ?; ");
			prepStmt.setString(1,user_id);
			rs = prepStmt.executeQuery();
			conn.commit();
			rs.next();
			jersey.booknet.model.User user = new jersey.booknet.model.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
			*/
			prepStmt = conn.prepareStatement( "SELECT friend_id FROM booknet.friendship WHERE user_id = ?;");
			prepStmt.setString(1,user_id)
			rs = prepStmt.executeQuery();
			conn.commit();
			rs.next();
			ArrayList <Integers> all_friends_ids = new ArrayList<Integers>();
			do{
				all_friends_ids.add(rs.getInt(1));
			}while(rs.next());

			if (calificacion_minima == null) calificacion_minima = 0;
			if (nombre_autor == null) nombre_autor = '%%';
			if (categoria == null) categoria = '%%';
			ArrayList <Book> filtered_books = new ArrayList<Book>();
			for (int friend_id : all_friends_ids){
				prepStmt = conn.prepareStatement( "SELECT 	b.* FROM booknet.read_books rb, booknet.books b
															WHERE 	rb.user_id = ? AND
																	rb.user_rating > ? AND
																	rb.category = ? AND
																	b.authors_name = ?;");
				prepStmt.setInt(1, friend_id);
				prepStmt.setInt(2, calificacion_minima);
				prepStmt.setString(3, categoria);
				prepStmt.setString(4, nombre_autor);

				rs = prepStmt.executeQuery();
				conn.commit();
				rs.next();
				do{
					filtered_books.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				}while(rs.next());
			}

			return filtered_books;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	}

	/* Mostrar: datos basicos de un usuario -> String Nick, String email,String born_date,String reg_date;
				ultimo libro leido 			-> int isbn, String name, String, auth_name, String category;
				numero de amigos;
				ultimo libro leido por sus amigos -> ??? Nick + Libro <-VS-> Libro Solo

	DUDA => Hay que crear una clase que sea FullUser.java que contenga todos los elementos que tiene que contener

	public OBJ getFullUserInfo(int user_id){}
				*/
	
}


