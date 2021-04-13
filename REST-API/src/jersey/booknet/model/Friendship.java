package com.upmsocial.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "friendship")
public class Friendship {

	private int friendship_id;
	private String id_user1;
	private String id_user2;

	@XmlAttribute
	public int getFriendship_id() {
		return friendship_id;
	}

	public void setFriendship_id(int friendship_id) {
		this.friendship_id = friendship_id;
	}
	

	public String getId_user1() {
		return id_user1;
	}

	public void setId_user1(String id_user1) {
		this.id_user1 = id_user1;
	}

	public String getId_user2() {
		return id_user2;
	}

	public void setId_user2(String id_user2) {
		this.id_user2 = id_user2;
	}

	public Friendship() {
	}

	public Friendship(int friendship_id,String id_user1, String id_user2) {
		super();
	    this.friendship_id = friendship_id;
	    this.id_user1 = id_user1;
	    this.id_user2 = id_user2;
	}

}