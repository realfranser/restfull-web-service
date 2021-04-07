package clase.datos;

import java.util.ArrayList;

public class user {
	private int user_id = this.getUser_id();
	private String user_name;
	private String email;
	private String born_date;
	private String reg_date;
	private ArrayList <user> friends;
	private ArrayList <book> books;
	public user(int user_id,String user_name,String email,String born_date,String reg_date,
														  ArrayList <user> friends,ArrayList <book> books){

		this.user_id = user_id;
		this.user_name = user_name;
		this.email = email;
		this.born_date = born_date;
		this.reg_date = reg_date;
		this.friends = friends;
		this.books = books;

	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBorn_date() {
		return born_date;
	}
	public void setBorn_date(String born_date) {
		this.born_date = born_date;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public ArrayList <user> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList <user> friends) {
		this.friends = friends;
	}
	public ArrayList <book> getBook() {
		return books;
	}
	public void setBook(ArrayList <book> books) {
		this.books = books;
	}
}
