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
			 prepStmt.setInt(1,user_id);
			 prepStmt.setString(2,email);
			 prepStmt.setInt(3,edad);
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
	
	public boolean read_book(int user_id, int isbn , int rating , int read_date) { // links a book with a user 
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
			 conn.commit();
			 return true;
		 }
		 catch(SQLException e){
			 e.printStackTrace();
			 return false;
			 } 
	
			 
		 }
	
	
	
	
	
}

