package edu.imut.service.impl;

import java.util.List;
import java.util.UUID;

import edu.imut.commons.Page;
import edu.imut.dao.BookDao;
import edu.imut.dao.CategoryDao;
import edu.imut.dao.CustomerDao;
import edu.imut.dao.OrderDao;
import edu.imut.dao.impl.BookDaoImpl;
import edu.imut.dao.impl.CategoryDaoImpl;
import edu.imut.dao.impl.CustomerDaoImpl;
import edu.imut.dao.impl.OrderDaoImpl;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.domain.Customer;
import edu.imut.domain.Order;
import edu.imut.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private OrderDao orderDao = new OrderDaoImpl();
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

	public void registCustomer(Customer customer) {
		customer.setId(UUID.randomUUID().toString());
		customerDao.save(customer);
	}

	public void activeCustomer(String code) {
		Customer customer = customerDao.findByCode(code);
		customer.setActived(true);
		customerDao.update(customer);
	}

	public Customer login(String name, String password) {
		Customer c = customerDao.fingCustomer(name,password);
		if(c==null)
			return null;
		if(!c.isActived())
			return null;
		return c;
	}

	public void genOrder(Order order) {
		order.setId(UUID.randomUUID().toString());
		orderDao.save(order);
	}

	public Order findOrderById(String orderId) {
		return orderDao.findById(orderId);
	}

	public Order findOrderByOrderNum(String orderNum) {
		return orderDao.findByOrderNum(orderNum);
	}

	public List<Order> findOrdersByCustomer(Customer customer) {
		return orderDao.findByCustomerId(customer.getId());
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

}
