package jersey.booknet.database;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

// Objeto User serializable
@XmlRootElement
public class User {
	  private int id;
	  private String nick;
	  private String email;
	  private int edad;
	  
	  // Obligatorio el constructor vacio
	  public User(){
	    
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

}
