package edu.imut.dao;

import java.util.List;

import edu.imut.domain.Book;

public interface BookDao {

	void save(Book book);

	Book findById(String bookId);

	int findAllBooksNumber();
	
	List<Book> findPageBooks(int startIndex,int offset);

	int findCategoryBooksNumber(String categoryId);

	List findPageBooks(int startIndex, int pageSize, String categoryId);
}
