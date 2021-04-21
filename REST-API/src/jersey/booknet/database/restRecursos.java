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

			if(!rs.next())
				return null;
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

	public ArrayList<jersey.booknet.database.User> getUsersWithName(String user_name) throws SQLException{ // returns all users with a given name
		if(conn == null) {
			connect();
		}
	
		ArrayList<jersey.booknet.database.User> users = new ArrayList<jersey.booknet.database.User>();
		prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_name LIKE ?");
		prepStmt.setString(1, "%"+user_name+"%");
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			users.add(new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
		}
		while(rs.next());
		return users;
	}

	public jersey.booknet.database.User getUser(int id) throws SQLException{ // return all basic info regarding a user with a given id -- OK
		if(conn == null) {
			connect();
		}

		prepStmt = conn.prepareStatement( "SELECT * FROM booknet.users WHERE user_id = ?; ");
		prepStmt.setInt(1,id);
		rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;
		jersey.booknet.database.User user = new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
		return user;
	}

	public jersey.booknet.database.User changeUser(jersey.booknet.database.User user) throws SQLException{ // changes basic info of a user with a given id
		if(conn == null) {
			connect();
		}
		if (user.getEdad()!=0 && user.getEmail() != null) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.users SET email=? , edad=? WHERE user_id=?; ");
			prepStmt.setString(1,user.getEmail());
			prepStmt.setInt(2,user.getEdad());
			prepStmt.setInt(3,user.getId());
		}
		else if (user.getEdad()!=0) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.users SET edad=? WHERE user_id=?; ");
			prepStmt.setInt(1,user.getEdad());
			prepStmt.setInt(2,user.getId());
		}
		else if (user.getEmail()!=null) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.users SET email=? WHERE user_id=?; ");
			prepStmt.setString(1,user.getEmail()); 
			prepStmt.setInt(2,user.getId());
		}
		prepStmt.executeUpdate();
		return getUser(user.getId());
	}

	public boolean removeUser(int user_id) { // removes user from db with a given id

		if(conn == null) {
			connect();
		}
		try {
			prepStmt = conn.prepareStatement( "DELETE FROM booknet.users WHERE user_id=?",Statement.EXECUTE_FAILED);
			prepStmt.setInt(1,user_id);
			int a =  prepStmt.executeUpdate();
			if(a==1)
				return true ;
			else
				return false;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		} 
	}

	public ReadBook readBook(int user_id, ReadBook readbook) throws SQLException{ // links a book with a user 
		if(conn == null) {
			connect();
		}
		prepStmt = conn.prepareStatement( "INSERT INTO booknet.read_books (`user_id`,`isbn`,`user_rating`,`read_date`) VALUES (?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,readbook.getIsbn());
		prepStmt.setInt(3,readbook.getRating());
		prepStmt.setInt(4,readbook.getReadDate()); // YYYYMMDD
		prepStmt.executeUpdate();
		rs = prepStmt.getGeneratedKeys();
		if (rs.next()) {
			readbook.setId(rs.getInt(1));
			readbook.setUserId(user_id);
		}
		return readbook; 
	}

	public boolean removeReadBook(int user_id , int isbn) {

		if(conn == null) {
			connect();
		}
		try { 
			prepStmt = conn.prepareStatement( "DELETE FROM booknet.read_books WHERE user_id=? AND isbn =?",Statement.EXECUTE_FAILED);
			prepStmt.setInt(1,user_id);
			prepStmt.setInt(2,isbn);
			int a = prepStmt.executeUpdate();
			if(a==1)
				return true ;
			else
				return false;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		} 
	}
	public ReadBook getReadBook(int user_id, int isbn) throws SQLException{ // return all basic info regarding a user with a given id -- OK
		if(conn == null) {
			connect();
		}

		prepStmt = conn.prepareStatement( "SELECT * FROM booknet.read_books WHERE user_id = ? AND isbn=?; ");
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,isbn);
		rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;
		ReadBook book = new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
		return book;
	}

	public ReadBook editReadBook(int user_id , ReadBook read_book) throws SQLException {

		if(conn == null) {
			connect();
		}
		if (read_book.getRating()>0 && read_book.getReadDate()>0) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.read_books SET user_rating=? , read_date=? WHERE user_id=? AND isbn=?; ");
			prepStmt.setInt(1,read_book.getRating());
			prepStmt.setInt(2,read_book.getReadDate());
			prepStmt.setInt(3,user_id);
			prepStmt.setInt(4,read_book.getIsbn());
		}
		else if (read_book.getRating()>0) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.read_books SET user_rating=? WHERE user_id=? AND isbn=?; ");
			prepStmt.setInt(1,read_book.getRating());
			prepStmt.setInt(2,user_id);
			prepStmt.setInt(3,read_book.getIsbn());
		}
		else if (read_book.getReadDate()>0) {
			prepStmt = conn.prepareStatement( "UPDATE booknet.read_books SET read_date=? WHERE user_id=? AND isbn=?; ");
			prepStmt.setInt(1,read_book.getReadDate());
			prepStmt.setInt(2,user_id);
			prepStmt.setInt(3,read_book.getIsbn());
		}
		prepStmt.executeUpdate();
		return getReadBook(user_id, read_book.getIsbn());
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

	/*
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
	 */

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

	public ArrayList <ReadBook> getReadBooks(int user_id , int date , int from , int to)throws SQLException{

		if(conn == null) {
			connect();
		}
		ArrayList <ReadBook> readBooks = new ArrayList<ReadBook>();
		int steps  = to - from;
		prepStmt = conn.prepareStatement( "SELECT * FROM `booknet`.`read_books` WHERE `user_id`=? AND `read_date` BETWEEN ? AND ? LIMIT ?,?;",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,0);// se podria cambiar es un poco chapuza xd
		prepStmt.setInt(3,date);
		prepStmt.setInt(4,from);
		prepStmt.setInt(5,steps);
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			readBooks.add(new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt (3),rs.getInt(4),rs.getInt(5))); // yyyymmdd
		}
		while(rs.next());
		return readBooks;
	}

	public ArrayList<jersey.booknet.database.User> listFriends(int user_id , String friend_name , int from , int to)throws SQLException{
		if(conn == null) {
			connect();
		}
		ArrayList <jersey.booknet.database.User> friends = new ArrayList<jersey.booknet.database.User>();
		int steps  = to - from;
		prepStmt = conn.prepareStatement( "SELECT * FROM `booknet`.`users` WHERE user_id IN(SELECT friend_id FROM `booknet`.`friendship` WHERE user_id = ? ) AND user_name LIKE ? LIMIT ?,?;",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		prepStmt.setString(2, "%"+friend_name+"%");
		prepStmt.setInt(3,from);
		prepStmt.setInt(4,steps);
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			friends.add(new jersey.booknet.database.User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4))); // yyyymmdd
		}
		while(rs.next());
		return friends;
	}

	public ArrayList<ReadBook> listReadingsFriends(int user_id , int date , int from , int to)throws SQLException{
		if(conn == null) {
			connect();
		}
		ArrayList <ReadBook> friendsReadings = new ArrayList<ReadBook>();
		int steps  = to - from;
		prepStmt = conn.prepareStatement( "SELECT * FROM `booknet`.`read_books` WHERE user_id IN(SELECT friend_id FROM `booknet`.`friendship` WHERE user_id = ? ) AND `read_date` BETWEEN ? AND ? LIMIT ?,?;",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,0);
		prepStmt.setInt(3,date);
		prepStmt.setInt(4,from);
		prepStmt.setInt(5,steps);
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			friendsReadings.add(new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt (3),rs.getInt(4),rs.getInt(5))); // yyyymmdd
		}
		while(rs.next());
		return friendsReadings;
	}


	public ArrayList<Book> getFriendsRecomendations(int user_id,int rating, String author,String category)throws SQLException{	
		if(conn == null) {
			connect();
		}
		ArrayList <Book> friendsRecomendations = new ArrayList<Book>();
		prepStmt = conn.prepareStatement( "SELECT * FROM `booknet`.`books` WHERE isbn IN(SELECT isbn FROM `booknet`.`read_books` WHERE user_id IN(SELECT friend_id FROM `booknet`.`friendship` WHERE user_id = ? AND `user_rating` BETWEEN ? AND ?)) AND `authors_name` LIKE ? AND `category` LIKE ?;",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,rating);
		prepStmt.setInt(3,10);// esto se puede cambiar
		prepStmt.setString(4,"%"+author+"%");
		prepStmt.setString(5,"%"+category+"%");
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			friendsRecomendations.add(new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4))); // yyyymmdd
		}
		while(rs.next());
		return friendsRecomendations;
	}

	public ReadBook getLastReadBook(int user_id)throws SQLException {
		if(conn == null) {
			connect();
		}
		prepStmt = conn.prepareStatement( "SELECT * FROM booknet.read_books WHERE user_id = ? AND read_date=(SELECT MAX(read_date) FROM `booknet`.`read_books` WHERE user_id = ?); ");
		prepStmt.setInt(1,user_id);
		prepStmt.setInt(2,user_id);
		rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;
		ReadBook book = new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
		return book;
	}

	public int countFriends(int user_id)throws SQLException{
		if(conn == null) {
			connect();
		}
		prepStmt = conn.prepareStatement( "SELECT COUNT(*) FROM `booknet`.`friendship` WHERE user_id =?",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		rs = prepStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
	public ArrayList<ReadBook> getLastReadBookFriends(int user_id)throws SQLException{
		if(conn == null) {
			connect();
		}
		ArrayList <ReadBook> lastBookFriends = new ArrayList<ReadBook>();

		prepStmt = conn.prepareStatement("SELECT * FROM `booknet`.`read_books` WHERE user_id IN(SELECT friend_id FROM `booknet`.`friendship` WHERE user_id = ?)AND read_date IN(SELECT MAX(read_date) FROM `booknet`.`read_books` GROUP BY user_id);",Statement.EXECUTE_FAILED);
		prepStmt.setInt(1,user_id);
		rs = prepStmt.executeQuery();
		if(!rs.next())
			return null;
		do {
			lastBookFriends.add(new ReadBook(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5))); // yyyymmdd
		}
		while(rs.next());
		return lastBookFriends;
	}

	public jersey.booknet.database.User getAppData(int user_id)throws SQLException{	
		if(conn == null) {
			connect();
		}
		jersey.booknet.database.User dataUser = new jersey.booknet.database.User();
		dataUser = getUser(user_id);
		ReadBook lastBookInfo = new ReadBook();
		lastBookInfo = getLastReadBook(user_id);
		Book lastBook = new Book();
		lastBook = getBook(lastBookInfo.getIsbn());
		lastBookInfo.setBook(lastBook);
		int friendsNumber = countFriends(user_id);
		ArrayList <ReadBook> lastBookFriends = new ArrayList<ReadBook>();
		lastBookFriends = getLastReadBookFriends(user_id);
		dataUser.setLastBookinfo(lastBookInfo);
		dataUser.setFriendsNumber(friendsNumber);
		dataUser.setLastBookFriends(lastBookFriends);
		return dataUser;
	}
}




