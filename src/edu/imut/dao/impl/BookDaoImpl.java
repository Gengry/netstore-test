package edu.imut.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import edu.imut.dao.BookDao;
import edu.imut.domain.Book;
import edu.imut.exception.DaoException;
import edu.imut.util.DBCPUtil;

public class BookDaoImpl implements BookDao {

	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Book book) {
		try {
			qr.update(
					"insert into books (id,name,author,price,path,photoFileName,description,categoryId) values (?,?,?,?,?,?,?,?)",
				book.getId(),
				book.getName(),
				book.getAuthor(),
				book.getPrice(),
				book.getPath(),
				book.getPhotoFileName(),
				book.getDescription(),
				book.getCategoryId()
				);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Book findById(String bookId) {
		try {
			return qr.query("select * from books where id=?", new BeanHandler<Book>(Book.class), bookId);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public int findAllBooksNumber(){
		try {
			Object obj = qr.query("select count(*) from books", new ScalarHandler(1));
			Long num = (Long)obj;
			return num.intValue();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public List<Book> findPageBooks(int startIndex,int offset){
		try{
			return qr.query("select * from books limit ?,?", new BeanListHandler<Book>(Book.class), startIndex,offset);
		}catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public int findCategoryBooksNumber(String categoryId) {
		try {
			Object obj = qr.query("select count(*) from books where categoryId=?", new ScalarHandler(1),categoryId);
			Long num = (Long)obj;
			return num.intValue();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List findPageBooks(int startIndex, int pageSize, String categoryId) {
		try{
			return qr.query("select * from books where categoryId=? limit ?,?", new BeanListHandler<Book>(Book.class), categoryId,startIndex,pageSize);
		}catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
