package edu.imut.test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.imut.dao.BookDao;
import edu.imut.dao.impl.BookDaoImpl;
import edu.imut.domain.Book;

public class BookDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSave() {
		BookDao dao = new BookDaoImpl();
		Book book = new Book();
		book.setId(UUID.randomUUID().toString());
		book.setName("ELB");
		book.setAuthor("Gengry");
		book.setPrice(109);
		book.setPhotoFileName("8acc979b-6db2-4873-87fd-33be7bacc158.jpg");
		book.setPath("/9/0");
		book.setDescription("java企业级开发");
		book.setCategoryId("2dd88764-cde5-4fdd-ac40-aa9fd97d07e5");
		dao.save(book);
	}

}
