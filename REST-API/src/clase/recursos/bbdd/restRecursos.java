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
	
	
	public void connect() { // connector for db
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
	
	public boolean insertUser(jersey.booknet.model.User u) throws SQLException { // inserts user
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
	public ArrayList<jersey.booknet.model.User> getUsers() { // returs all users on the database
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
	 
	public ArrayList<jersey.booknet.model.User> getUsersWithName(String user_name) { // returns all users with a given name
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<jersey.booknet.model.User> users = new ArrayList<jersey.booknet.model.User>();
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_name = ?");
		 prepStmt.setString(1, user_name);
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
	
	public jersey.booknet.model.User getUser(int id){ // return all basic info regarding a user with a given id
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_id = ?; ");
			 prepStmt.setInt(1,id);
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
	
	public boolean changeUser(int user_id,String email, int edad) { // changes basic info of a user with a given id
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "UPDATE booknet.user SET email=? edad=? WHERE user_id = ?; ");
			 prepStmt.setString(1,email);
			 prepStmt.setInt(2,edad);
			 prepStmt.setInt(3,user_id);
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}
	
	public boolean removeUser(int user_id) { // removes user from db with a given id
		
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.user WHERE user_id=?");
			 prepStmt.setInt(1,user_id);
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}
	
	public boolean readBook(int user_id, int isbn , int rating , int read_date) { // links a book with a user 
		if(conn == null) {
			 connect();
		 }
		 try {
			 
			 prepStmt = conn.prepareStatement( "INSERT INTO booknet.read_book ('user_id','isbn','user_rating','read_date') VALUES (?,?,?,?);");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,isbn);
			 prepStmt.setInt(3,rating);
			 prepStmt.setInt(3,read_date); // YYYYMMDD
			 prepStmt.executeUpdate();
			 conn.commit();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	
			 
		 }
	
	public boolean removeReadBook(int user_id , int isbn) {
		
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.read_book WHERE user_id=? AND isbn =?");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,isbn);
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}	 
	
	public Book getBook(int isbn) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.book WHERE isbn = ?; ");
			 prepStmt.setInt(1,isbn);
			 rs = prepStmt.executeQuery();
			 conn.commit();
			 rs.next();
			 Book book= new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
			 return book;
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	}
	 
	public boolean editBook(Book book){
		if(conn == null) {
			 connect();
		 }
		 try { // No seria booknet.books ?
			 prepStmt = conn.prepareStatement( "UPDATE booknet.book SET name=? authors_name=? category=? WHERE isbn = ?; ");
			 prepStmt.setString(1,book.getName());
			 prepStmt.setString(2,book.getAuthName());
			 prepStmt.setString(3,book.getCategory());
			 prepStmt.setInt(4,book.getIsbn());
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	}
	
	public ArrayList<ReadBook> getBooksUser(int user_id){
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<ReadBook> readBooks = new ArrayList<ReadBook>();
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.read_book WHERE user_id = ?");
		 prepStmt.setInt(1, user_id);
		 rs = prepStmt.executeQuery();
		 conn.commit();
		 rs.next();
		 do {
			 readBooks.add(new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt (3),rs.getInt(4),rs.getInt(5))); // yyyymmdd
		 }
		 while(rs.next());
		 return readBooks;
		 }
		 catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
			 return null;
		}
	}
	
	public boolean addFriendship(int user_id, int friend_id) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "INSERT INTO booknet.friendship ('user_id','friend_id') VALUES (?,?);");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,friend_id);
			 prepStmt.executeUpdate();
			 conn.commit();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	
	}
	public boolean removeFriendship(int user_id, int friend_id) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.friendship WHERE user_id=? AND friend_id =?;");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,friend_id);
			 prepStmt.executeUpdate();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;//
			 } 
	}
	public ArrayList<Integer> getFriendsId(int user_id, Integer first_row, Integer last_row){ // returns list of friends id
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<Integer> friends_id = new ArrayList<Integer>();
		 if (first_row == null) first_row = 0;
		 prepStmt = conn.prepareStatement( "SELECT friend_id FROM booknet.friendship WHERE user_id = ? OFFSET ?;");
		 prepStmt.setInt(1, user_id);
		 rs = prepStmt.executeQuery();
		 conn.commit();
		 rs.next();
		 do {
			 friends_id.add(new Integer(rs.getInt(1))); // yyyymmdd
		 }
		 while(rs.next() && (last_row == null || friend_ids.size() < (last_row - first_row)));
		 return friends_id;
		 }
		 catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
			 return null;
		}
		
	}

	public ArrayList<Book> getLastReadBooks(int user_id, int date, int first, int last){
		if (conn == null) connect();
		try{
			ArrayList <Integer> friends_id = getFriendsId(user_id, null, null);
			if (first == null) first = 0;
			ArrayList <Book> filtered_books = new ArrayList<Book>();
			for (int friend_id : friends_id){
				prepStmt = conn.prepareStatement( "SELECT b.* 	FROM booknet.read_books rb, booknet.books b
																WHERE	rb.user_id = ? AND
																		rb.read_date > ?;")
				prepStmt.setInt(1, friend_id);
				prepStmt.setInt(2, date);

				rs = prepStmt.executeQuery();
				conn.commit();
				rs.next();
				int starting_counter = first;
				do{
					filtered_books.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
					starting_counter --;
					if (starting_counter > 0) filtered_books.remove(filtered_books.size()-1);
				}while(rs.next() && (last_row == null || filtered_books.size() < (last < first)));
			}

				return filtered_books
		}catch (SQLException e){
			// TODO: handle exception
			e.printStackTrace();
			return null;
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
	
	/* Mostrar: datos basicos de un usuario -> String Nick, String email,String born_date,String reg_date;
				ultimo libro leido 			-> int isbn, String name, String, auth_name, String category;
				numero de amigos;
				ultimo libro leido por sus amigos -> ??? Nick + Libro <-VS-> Libro Solo

	DUDA => Hay que crear una clase que sea FullUser.java que contenga todos los elementos que tiene que contener

	public OBJ getFullUserInfo(int user_id){}
				*/
	
}

