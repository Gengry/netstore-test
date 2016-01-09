package edu.imut.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.service.BusinessService;
import edu.imut.service.impl.BusinessServiceImpl;
import edu.imut.web.beans.Cart;

public class ClientServlet extends HttpServlet {
	private BusinessService service = new BusinessServiceImpl();

	public ClientServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("showIndex".equals(op)) {
			showIndex(request, response);
		} else if ("showCategoryBooks".equals(op)) {
			showCategoryBooks(request, response);
		} else if ("showBookDetails".equals(op)) {
			showBookDetails(request, response);
		} else if ("buyBook".equals(op)) {
			buyBook(request, response);
		}
	}

	// 添加图书到购物车
	private void buyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String bookId = request.getParameter("bookId");
		Book book = service.findBookById(bookId);
		// 放入购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		cart.addBook(book);
		request.setAttribute("msg", "<a href='"+request.getContextPath()+"'>书籍已经放入购物车，继续购物</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);

	}

	// 显示书籍详细信息
	private void showBookDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = service.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/showDetails.jsp").forward(request,
				response);
	}

	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		List<Category> cs = service.findAllCategories();
		Page page = service.findAllBookPageRecords(categoryId, num);
		page.setUrl("/servlet/ClientServlet?op=showCategoryBooks&categoryId="
				+ categoryId);

		request.setAttribute("cs", cs);
		request.setAttribute("page", page);

		request.getRequestDispatcher("/listAllBooks.jsp").forward(request,
				response);
	}

	private void showIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		List<Category> cs = service.findAllCategories();
		Page page = service.findAllBookPageRecords(num);
		page.setUrl("/servlet/ClientServlet?op=showIndex");

		request.setAttribute("cs", cs);
		request.setAttribute("page", page);

		request.getRequestDispatcher("/listAllBooks.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
