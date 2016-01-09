package edu.imut.test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import edu.imut.dao.CategoryDao;
import edu.imut.dao.impl.CategoryDaoImpl;
import edu.imut.domain.Category;

public class DaoTest {

	@Test
	public void testSave() {
		Category c = new Category();
		c.setId(UUID.randomUUID().toString());
		c.setName("789");
		c.setDescription("789");
		CategoryDao dao = new CategoryDaoImpl();
		dao.save(c);
	}

	@Test
	public void testFindAll() {
	}

	@Test
	public void testFindById() {
	}

}
