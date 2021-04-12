package jersey.booknet.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

// Objeto User serializable
@XmlRootElement
public class User {
	  private String id;
	  private String nick;
	  private String email;
	  private Date born_date;
	  private Date reg_date;
	  
	  // Obligatorio el constructor vacio
	  public User(){
	    
	  }
	  public User (String id, String nick, String email, Date born_date, Date reg_date){
	    this.id = id;
	    this.nick = nick;
	    this.email = email;
	    this.born_date = born_date;
	    this.reg_date = reg_date;
	  }
	  public String getId() {
	    return id;
	  }
	  public void setId(String id) {
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
	  public Date getBornDate() {
		  return born_date;
	  }
	  public void setBornDate(Date born_date) {
		  this.born_date = born_date;
	  }
	  public Date getRegDate() {
		  return reg_date;
	  }
	  public void setRegDate(Date reg_date) {
		  this.reg_date = reg_date;
	  }
}
