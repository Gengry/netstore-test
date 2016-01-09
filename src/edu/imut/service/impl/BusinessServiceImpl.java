package edu.imut.service.impl;

import java.util.List;
import java.util.UUID;

import edu.imut.commons.Page;
import edu.imut.dao.BookDao;
import edu.imut.dao.CategoryDao;
import edu.imut.dao.impl.BookDaoImpl;
import edu.imut.dao.impl.CategoryDaoImpl;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	public void addCategory(Category category) {
		category.setId(UUID.randomUUID().toString());
		categoryDao.save(category);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

	public Category findCategoryById(String categoryId) {
		return categoryDao.findById(categoryId);
	}

	public void addBook(Book book) {
		book.setId(UUID.randomUUID().toString());
		bookDao.save(book);
	}

	public Page findAllBookPageRecords(String pagenum) {
		
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		
		int totalRecords = bookDao.findAllBooksNumber();
		Page page = new Page(currentPageNum,totalRecords);
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize()));
		return page;
	}

	public Book findBookById(String bookId) {
		return bookDao.findById(bookId);
	}

	public Page findAllBookPageRecords(String categoryId, String pagenum) {
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		
		int totalRecords = bookDao.findCategoryBooksNumber(categoryId);
		Page page = new Page(currentPageNum,totalRecords);
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize(),categoryId));
		return page;
	}

}
