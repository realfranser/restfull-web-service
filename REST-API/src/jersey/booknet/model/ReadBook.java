package com.upmsocial.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement(name = "post")
public class ReadBook {
	private int id; // Key
	private int user_id; // Foreign-Key-1
	private int isbn; // Foreign-Key-2
	private int rating;
	private Date read_date;

	@XmlAttribute
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getIsbn() {
		return isbn;
	}
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Date getReadDate() {
		return read_date;
	}
	public void setReadDate(Date date) {
		this.date = date;
	}
	
	public ReadBook() {

    }

    public ReadBook(int id, int user_id, id isbn, int rating, Date date) {
        super();
        this.id = id;
        this.username = username;
        this.date_post = date_post;
        this.url = url;
        this.description = description;
    }
    
}