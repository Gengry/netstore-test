package edu.imut.web.beans;

import java.io.Serializable;

import edu.imut.domain.Book;
//���ﳵ�еĹ�����
public class CartItem implements Serializable{
	private Book book;
	public CartItem(Book book){
		this.book = book;
	}
	private int quantity;
	private float totalprice;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float gettotalprice() {
		return book.getPrice()*quantity;
	}
	public void settotalprice(float totalprice) {
		this.totalprice = totalprice;
	}
}
