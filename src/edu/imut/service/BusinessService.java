package edu.imut.service;

import java.util.List;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;

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
}
