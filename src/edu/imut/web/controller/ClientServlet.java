package edu.imut.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.imut.commons.Page;
import edu.imut.domain.Book;
import edu.imut.domain.Category;
import edu.imut.domain.Customer;
import edu.imut.domain.Order;
import edu.imut.domain.OrderItem;
import edu.imut.service.BusinessService;
import edu.imut.service.impl.BusinessServiceImpl;
import edu.imut.util.FillBeanUtil;
import edu.imut.util.IdGenertor;
import edu.imut.util.PaymentUtil;
import edu.imut.util.SendMail;
import edu.imut.web.beans.Cart;
import edu.imut.web.beans.CartItem;

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
		} else if ("changeNum".equals(op)) {
			changeNum(request, response);
		} else if ("delOneItem".equals(op)) {
			delOneItem(request, response);
		} else if ("registCustomer".equals(op)) {
			registCustomer(request, response);
		} else if ("active".equals(op)) {
			active(request, response);
		} else if ("login".equals(op)) {
			login(request, response);
		}else if("logout".equals(op)){
			logout(request,response);
		}else if("genOrders".equals(op)){
			genOrders(request,response);
		}else if("showCustomerOrders".equals(op)){
			showCustomerOrders(request,response);
		}else if("pay".equals(op)){
			pay(request,response);
		}
	}
	//֧������
	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNum = request.getParameter("orderNum");
		String money = request.getParameter("money");
		String pd_FrpId = request.getParameter("pd_FrpId");
		
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = orderNum;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "unknown";
		String p6_Pcat = "unknown";
		String p7_Pdesc = "unknown";
		String p8_Url = "http://localhost:8080/day23_00_netstore/servlet/PaymentResponse";
		String p9_SAF = "1";
		String pa_MP = "no";
		String pr_NeedResponse = "1";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		
		request.setAttribute("p0_Cmd",p0_Cmd );
		request.setAttribute("p1_MerId",p1_MerId );
		request.setAttribute("p2_Order",p2_Order );
		request.setAttribute("p3_Amt",p3_Amt );
		request.setAttribute("p4_Cur",p4_Cur );
		request.setAttribute("p5_Pid",p5_Pid );
		request.setAttribute("p6_Pcat",p6_Pcat );
		request.setAttribute("p7_Pdesc",p7_Pdesc );
		request.setAttribute("p8_Url",p8_Url );
		request.setAttribute("p9_SAF",p9_SAF );
		request.setAttribute("pa_MP",pa_MP );
		request.setAttribute("pr_NeedResponse",pr_NeedResponse );
		request.setAttribute("pd_FrpId",pd_FrpId );
		request.setAttribute("hmac",hmac );
		
		request.getRequestDispatcher("/sure.jsp").forward(request, response);
	}

	//��ѯ��¼�û��Ķ���
	private void showCustomerOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		//�ж��û���û�е�¼
		Customer c = (Customer) session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		List<Order> orders = service.findOrdersByCustomer(c);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/listOrders.jsp").forward(request, response);
	}
	//���ɶ���
	private void genOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		//�û��Ƿ��¼
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//���ﳵ---������
		Cart cart = (Cart) session.getAttribute("cart");
		
		Order order = new Order();
		String orderNum = IdGenertor.genOrdersNum();
		order.setOrderNum(orderNum);
		order.setQuantity(cart.getTotalQuantity());
		order.setAmount(cart.getAmount());
		order.setCustomer(c);
		
		//������---��������
		Map<String,CartItem> items = cart.getItems();
		for(Map.Entry<String , CartItem> me:items.entrySet()){
			OrderItem oi = new OrderItem();
			oi.setId(UUID.randomUUID().toString());
			oi.setQuantity(me.getValue().getQuantity());
			oi.setPrice(me.getValue().gettotalprice());
			oi.setBook(me.getValue().getBook());
			//������ϵ
			order.getItems().add(oi);
		}
		//����
		service.genOrder(order);
		request.getRequestDispatcher("/pay.jsp?orderNum="+orderNum+"&amount="+order.getAmount()).forward(request, response);
		
	}

	//�û�ע��
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("customer");
		response.sendRedirect(request.getContextPath());
	}

	// �ͻ���¼
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Customer customer = service.login(name, password);
		if (customer == null) {
			request.setAttribute("msg", "�û������������");
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("customer", customer);
			response.sendRedirect(request.getContextPath());
		}
	}

	// �����˻�
	private void active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		if (code != null && !"".equals(code)) {
			service.activeCustomer(code);
			request.setAttribute("msg", "����ɹ���");
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
		} else {
			request.setAttribute("msg", "ע��������");
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
		}
	}

	// �û�ע��
	private void registCustomer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Customer customer = FillBeanUtil.fillBean(request, Customer.class);
		// ����Ψһ������
		customer.setCode(UUID.randomUUID().toString());
		service.registCustomer(customer);
		// ���ͼ����ʼ�����Ҫʱ�䣺���߳�
		SendMail sm = new SendMail(customer);
		sm.start();
		request.setAttribute("msg",
				"ע��ɹ��������Ѿ�������һ�⼤���ʼ�������" + customer.getEmail() + "�����У��뼰ʱ���������˻�");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	// ɾ��������
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		cart.getItems().remove(bookId);
		request.getRequestDispatcher("/showCart.jsp")
				.forward(request, response);
	}

	// �ı乺�ﳵbook����
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		String num = request.getParameter("num");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		cart.getItems().get(bookId).setQuantity(Integer.parseInt(num));
		request.getRequestDispatcher("/showCart.jsp")
				.forward(request, response);
	}

	// ���ͼ�鵽���ﳵ
	private void buyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = service.findBookById(bookId);
		// ���빺�ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		cart.addBook(book);
		request.setAttribute("msg", "<a href='" + request.getContextPath()
				+ "'>�鼮�Ѿ����빺�ﳵ����������</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);

	}

	// ��ʾ�鼮��ϸ��Ϣ
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
