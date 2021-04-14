package jersey.booknet.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

import User;
import Book;

// Objeto User serializable
@XmlRootElement
public class FullUser {
    private User user;
    private Book last_book;
    private int num_friends;
    private ArrayList<Book>last_book_friends;
	  
	  // Obligatorio el constructor vacio
	  public User(){

	  }
	  public User (User user, Book last_book, int num_friends, ArrayList<Book>last_book_friends){
	    this.user = user;
	    this.last_book = last_book;
	    this.num_friends = num_friends;
        this.last_book_friends = last_book_friends;
	  }
	  public User getUser() {
	    return user;
	  }
	  public void setUser(User user) {
	    this.user = user;
	  }
	  public Book getBook() {
	    return nick;
	  }
	  public void setBook(Book book) {
	    this.book = book;
	  }
	  public int getNumFriends() {
	    return num_friends;
	  }
	  public void setNumFriends(int num_friends) {
	    this.num_friends = num_friends;
	  }
	  public ArrayList<Book> getLastBookFriends() {
		return last_book_friends;
	}
	public void setLastBookFriends(ArrayList<Book>last_book_friends) {
		this.last_book_friends = last_book_friends;
	}

}