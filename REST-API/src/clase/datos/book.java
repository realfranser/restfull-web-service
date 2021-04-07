package clase.datos;

public class book {
	private int isbn;
	private String name ;
	private String author ;
	private String cathegory ;
	private int rating;
	private String read_date;
	
	public book(int isbn , String name , String author, String cathegory, int rating , String read_date) {
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.cathegory = cathegory;
		this.rating = rating;
		this.read_date = read_date;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCathegory() {
		return cathegory;
	}
	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getRead_date() {
		return read_date;
	}
	public void setRead_date(String read_date) {
		this.read_date = read_date;
	}
}
