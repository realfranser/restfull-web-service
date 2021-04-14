package jersey.booknet.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


import java.util.ArrayList;
import java.util.Date;


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
	
	public jersey.booknet.database.User insertUser(jersey.booknet.database.User u) throws SQLException { // inserts user -- OK
		 if(conn == null) {
			 connect();
		 }
		
		 prepStmt = conn.prepareStatement( "INSERT INTO booknet.users (`user_name`,`email`,`edad`) VALUES (?,?,?);",Statement.RETURN_GENERATED_KEYS);
		 prepStmt.setString(1,u.getNick());
		 prepStmt.setString(2,u.getEmail());
		 prepStmt.setInt(3,u.getEdad());
		 prepStmt.executeUpdate();
		 rs = prepStmt.getGeneratedKeys();
		 if (rs.next()) {
			 u.setId(rs.getInt(1));
		 }
		 return u;
		}
	public ArrayList<jersey.booknet.database.User> getUsers() { // returs all users on the database
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<jersey.booknet.database.User> users = new ArrayList<jersey.booknet.database.User>();
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users ");
		 rs = prepStmt.executeQuery();
		 
		 rs.next();
		 do {
			 users.add(new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
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
	 
	public ArrayList<jersey.booknet.database.User> getUsersWithName(String user_name) { // returns all users with a given name
		if(conn == null) {
			 connect();
		 }
		 try {
		 ArrayList<jersey.booknet.database.User> users = new ArrayList<jersey.booknet.database.User>();
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_name = ?");
		 prepStmt.setString(1, user_name);
		 rs = prepStmt.executeQuery();
		 
		 rs.next();
		 do {
			 users.add(new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
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
	
	public jersey.booknet.database.User getUser(int id){ // return all basic info regarding a user with a given id -- OK
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_id = ?; ");
			 prepStmt.setInt(1,id);
			 rs = prepStmt.executeQuery();
			 
			 rs.next();
			 jersey.booknet.database.User user = new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
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
			 prepStmt = conn.prepareStatement( "UPDATE booknet.users SET email=? edad=? WHERE user_id = ?; ");
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
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.users WHERE user_id=?");
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
			 
			 prepStmt = conn.prepareStatement( "INSERT INTO booknet.read_books ('user_id','isbn','user_rating','read_date') VALUES (?,?,?,?);");
			 prepStmt.setInt(1,user_id);
			 prepStmt.setInt(2,isbn);
			 prepStmt.setInt(3,rating);
			 prepStmt.setInt(3,read_date); // YYYYMMDD
			 prepStmt.executeUpdate();
			 
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
			 prepStmt = conn.prepareStatement( "DELETE FROM booknet.read_books WHERE user_id=? AND isbn =?");
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
			 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.books WHERE isbn = ?; ");
			 prepStmt.setInt(1,isbn);
			 rs = prepStmt.executeQuery();
			 
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
		 try {
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
		 prepStmt = conn.prepareStatement( "SELECT * FROM booknet.read_books WHERE user_id = ?");
		 prepStmt.setInt(1, user_id);
		 rs = prepStmt.executeQuery();
		 
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
	
	public Friendship addFriendship(Friendship friends) { // hay que 
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "INSERT INTO booknet.friendship (`user_id`,`friend_id`) VALUES (?,?);",Statement.RETURN_GENERATED_KEYS);
			 prepStmt.setInt(1,friends.getId_user1());
			 prepStmt.setInt(2,friends.getId_user2());
			 prepStmt.executeUpdate();
			 
			 rs = prepStmt.getGeneratedKeys();
			 if (rs.next()) {
				 friends.setFriendship_id(rs.getInt(1));
			 }
			 return friends;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return null;
			 } 
	
	}
	public boolean removeFriendship(Friendship friends) {
		if(conn == null) {
			 connect();
		 }
		 try {
			 prepStmt = conn.prepareStatement( "DELETE FROM `booknet`.`friendship` WHERE `user_id`=? AND `friend_id`=?;",Statement.EXECUTE_FAILED);
			 prepStmt.setInt(1,friends.getId_user1());
			 prepStmt.setInt(2,friends.getId_user2());
			 int a = prepStmt.executeUpdate();
			 if(a==1)
			 return true ;
			 else
			 return false;
			 
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;//
			 } 
	}
	
	
}

