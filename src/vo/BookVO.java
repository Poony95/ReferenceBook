package vo;

public class BookVO {
	private int bookied;
	private String bookname;
	private String publisher;
	private int price;
	public BookVO(int bookied, String bookname, String publisher, int price) {
		super();
		this.bookied = bookied;
		this.bookname = bookname;
		this.publisher = publisher;
		this.price = price;
	}
	public BookVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getBookied() {
		return bookied;
	}
	public void setBookied(int bookied) {
		this.bookied = bookied;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
