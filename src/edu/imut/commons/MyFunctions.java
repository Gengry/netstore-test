package edu.imut.commons;

import edu.imut.domain.Category;
import edu.imut.service.BusinessService;
import edu.imut.service.impl.BusinessServiceImpl;

public class MyFunctions {
	private static BusinessService s = new BusinessServiceImpl();

	public static String showCategoryName(String categoryId) {
		Category c = s.findCategoryById(categoryId);
		if (c != null) {
			return c.getName();
		}
		return null;
	}
}
