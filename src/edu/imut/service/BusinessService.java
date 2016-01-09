package edu.imut.service;

import java.util.List;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;

public interface BusinessService {
	/**
	 * ���һ������
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * ��ѯ���з���
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * ����������ѯ����
	 * @param categoryId
	 * @return û���ҵ�����null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * ���һ��ͼ��
	 * @param book
	 */
	void addBook(Book book);
	/**
	 * �����û�Ҫ�鿴��ҳ�룬���ط�װ�˷�ҳ��Ϣ��page����
	 * @param pagenum Ĭ��Ϊ1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum);
	/**
	 * �����û�Ҫ�鿴��ҳ�룬���ط�װ�˷�ҳ��Ϣ��page����,���շ�����в�ѯ
	 * @param pagenum Ĭ��Ϊ1
	 * @return
	 */
	Page findAllBookPageRecords(String categoryId,String pagenum);
	/**
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
}
