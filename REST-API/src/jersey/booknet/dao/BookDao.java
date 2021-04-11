package jersey.booknet.dao;

import java.util.HashMap;
import java.util.Map;

import jersey.booknet.model.Book;

public class BookDao {
	private Map<String, Book> contentProvider = new HashMap<>();
	  
	  private static BookDao instance = null;
	  
	  private BookDao() {
	    
	    Book book = new Book(	/*ISBN*/"1",
	    						/*Book name*/"SOS Book",
	    						/*Auth Name*/"Jesus Serrano",
	    						/*Category*/"Engineering");
	    
	    book.setName("SOS Book");
	    contentProvider.put("1", book);
	    book = new Book("2", "SDE 1", "Tech Lead", "Engineering");
	    book.setName("SDE 1");
	    contentProvider.put("2", book);
	    
	  }
	  public Map<String, Book> getModel(){
	    return contentProvider;
	  }
	  
	  
	  public static BookDao getInstance() {
		  if (instance==null)
			  instance = new BookDao();
		  return instance;
	  }
	} 

/*	
	public enum BookDao {
	  instance;
	  
	  private Map<String, Book> contentProvider = new HashMap<String, Book>();
	  
	  private BookDao() {
	    
	    Book book = new Book("1", "SOS Book", "Jesus Serrano", "Engineering");
	    book.setName("SOS Book");
	    contentProvider.put("1", book);
	    book = new Book("2", "SDE 1", "Tech Lead", "Engineering");
	    book.setName("SDE 1");
	    contentProvider.put("2", book);
	    
	  }
	  public Map<String, Book> getModel(){
	    return contentProvider;
	  }

}*/




