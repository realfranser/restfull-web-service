package jersey.booknet.database;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Date;

// Objeto User serializable
@XmlRootElement
public class User {
	  private int id;
	  private String nick;
	  private String email;
	  private int edad;
	  
	  private ReadBook lastBookinfo;
	  private Book lastBook;
	  private int friendsNumber;
	  private ArrayList<ReadBook> lastBookFriends;
		  
	  // Obligatorio el constructor vacio
	  public User(){
	    
	  }
	  public User (int id, String nick, String email, int edad,ReadBook lastBookinfo,Book lastBook,int friendsNumber, ArrayList<ReadBook> lastBookFriends){
		    this.id = id;
		    this.nick = nick;
		    this.email = email;
		    this.setEdad(edad);
		    this.setLastBook(lastBook);
		    this.setLastBookinfo(lastBookinfo);
		    this.setFriendsNumber(friendsNumber);
		    this.setLastBookFriends(lastBookFriends);
		  }
	  public User (int id, String nick, String email, int edad){
	    this.id = id;
	    this.nick = nick;
	    this.email = email;
	    this.setEdad(edad);
	  }
	  public int getId() {
	    return id;
	  }
	  public void setId(int id) {
	    this.id = id;
	  }
	  public String getNick() {
	    return nick;
	  }
	  public void setNick(String nick) {
	    this.nick = nick;
	  }
	  public String getEmail() {
	    return email;
	  }
	  public void setEmail(String email) {
	    this.email = email;
	  }
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public ReadBook getLastBookinfo() {
		return lastBookinfo;
	}
	public void setLastBookinfo(ReadBook lastBookinfo) {
		this.lastBookinfo = lastBookinfo;
	}
	public Book getLastBook() {
		return lastBook;
	}
	public void setLastBook(Book lastBook) {
		this.lastBook = lastBook;
	}
	public int getFriendsNumber() {
		return friendsNumber;
	}
	public void setFriendsNumber(int friendsNumber) {
		this.friendsNumber = friendsNumber;
	}
	public ArrayList<ReadBook> getLastBookFriends() {
		return lastBookFriends;
	}
	public void setLastBookFriends(ArrayList<ReadBook> lastBookFriends) {
		this.lastBookFriends = lastBookFriends;
	}

}
