package edu.imut.service;

import java.util.List;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.domain.Customer;
import edu.imut.domain.Order;

public interface BusinessService {
	/**
	 * 添加一个分类
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * 查询所有分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据主键查询分类
	 * @param categoryId
	 * @return 没有找到返回null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * 添加一本图书
	 * @param book
	 */
	void addBook(Book book);
	/**
	 * 根据用户要查看的页码，返回封装了分页信息的page对象
	 * @param pagenum 默认为1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum);
	/**
	 * 根据用户要查看的页码，返回封装了分页信息的page对象,按照分类进行查询
	 * @param pagenum 默认为1
	 * @return
	 */
	Page findAllBookPageRecords(String categoryId,String pagenum);
	/**
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
	/**
	 * 用户注册
	 * @param customer
	 */
	void registCustomer(Customer customer);
	/**
	 * 用户激活
	 * @param code 激活码
	 */
	void activeCustomer(String code);
	/**
	 * 用户登录
	 * @param name
	 * @param password
	 * @return
	 */
	Customer login(String name, String password);
	/**
	 * 生成订单
	 * @param order
	 */
	void genOrder(Order order);
	
	/**
	 * 更新订单
	 * @param order
	 */
	void updateOrder(Order order);
	
	/**
	 * 通过ID查询订单
	 * @param orderId
	 * @return
	 */
	Order findOrderById(String orderId);
	/**
	 * 通过订单号查询订单
	 * @param orderNum
	 * @return
	 */
	Order findOrderByOrderNum(String orderNum);
	/**
	 * 通过客户id查询客户的所有订单
	 * @param CustomerId
	 * @return
	 */
	List<Order> findOrdersByCustomer(Customer customer);
}
