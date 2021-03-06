package client;

import javax.xml.bind.annotation.XmlRootElement;

// Objeto Book serializable
@XmlRootElement
public class Book {
	  private int isbn;
	  private String name;
	  private String auth_name;
	  private String category;
	  
	  // Obligatorio el constructor vacio
	  public Book(){
	    
	  }
	  public Book (int isbn, String name, String auth_name, String category){
	    this.isbn = isbn;
	    this.name = name;
	    this.auth_name = auth_name;
	    this.category = category;
	  }
	  public int getIsbn() {
	    return isbn;
	  }
	  public void setIsbn(int isbn) {
	    this.isbn = isbn;
	  }
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	  public String getAuthName() {
	    return auth_name;
	  }
	  public void setAuthName(String auth_name) {
	    this.auth_name = auth_name;
	  }
	  public String getCategory() {
		  return category;
	  }
	  public void setCategory(String category) {
		  this.category= category;
	  }
}
