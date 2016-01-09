package edu.imut.web.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.imut.domain.Book;
//购物车
public class Cart implements Serializable {
	private Map<String,CartItem> items = new HashMap<String,CartItem>();
	private int totalQuantity;
	private float amount;
	public Map<String, CartItem> getItems() {
		return items;
	}
	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}
	public int getTotalQuantity() {
		totalQuantity = 0;
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			totalQuantity += item.getValue().getQuantity();
		}
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getAmount() {
		amount = 0;
		for(Map.Entry<String,CartItem> item:items.entrySet()){
			amount += item.getValue().gettotalprice();
		}
		return amount;
	}
	public void setAmount(float totalPrice) {
		this.amount = totalPrice;
	}
	
	public void addBook(Book book){
		if(items.containsKey(book.getId())){
			//书籍已存在
			CartItem item = items.get(book.getId());
			item.setQuantity(item.getQuantity()+1);
		}else{
			//书籍不存在
			CartItem item = new CartItem(book);
			item.setQuantity(1);
			items.put(book.getId(), item);
		}
	}
	
}
