package edu.imut.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.service.BusinessService;
import edu.imut.service.impl.BusinessServiceImpl;
import edu.imut.util.FillBeanUtil;

public class ControlServlet extends HttpServlet {

	private BusinessService service = new BusinessServiceImpl();
	public ControlServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("showAllCategories".equals(op)){
			showAllCategories(request,response);
		}else if("showAllBookUI".equals(op)){
			showAddBookUI(request,response);
		}else if("addBook".equals(op)){
			try {
				addBook(request,response);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else if("showAllBooks".equals(op)){
			showAllBooks(request,response);
		}
	}
	//分页显示所有书籍
	private void showAllBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		Page page = service.findAllBookPageRecords(num);
		page.setUrl("/servlet/ControlServlet?op=showAllBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
	}
	//添加一本图书
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Book book = FillBeanUtil.fillBeanAndFileUpload(request, Book.class);
		if(book==null){
			request.setAttribute("msg", "表单提交错误，请检查！");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
		}
		service.addBook(book);
		request.setAttribute("msg", "保存成功！");
		request.getRequestDispatcher("/manage/message.jsp").forward(request,response);
	}
	//显示添加书籍的界面
	private void showAddBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = service.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}
	//查询所有分类
	private void showAllCategories(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = service.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/listCategories.jsp").forward(request, response);
	}
	//添加一个分类
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		service.addCategory(category);
		try {
			request.setAttribute("msg", "保存成功！");
			request.getRequestDispatcher("/manage/message.jsp").forward(request,response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
