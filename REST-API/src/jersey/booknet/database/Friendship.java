package jersey.booknet.database;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "friendship")
public class Friendship {

	private int friendship_id;
	private int id_user1;
	private int id_user2;

	@XmlAttribute
	public int getFriendship_id() {
		return friendship_id;
	}

	public void setFriendship_id(int friendship_id) {
		this.friendship_id = friendship_id;
	}
	

	public int getId_user1() {
		return id_user1;
	}

	public void setId_user1(int id_user1) {
		this.id_user1 = id_user1;
	}

	public int getId_user2() {
		return id_user2;
	}

	public void setId_user2(int id_user2) {
		this.id_user2 = id_user2;
	}

	public Friendship() {
	}

	public Friendship(int friendship_id,int id_user1, int id_user2) {
		super();
	    this.friendship_id = friendship_id;
	    this.id_user1 = id_user1;
	    this.id_user2 = id_user2;
	}

}