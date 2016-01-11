package edu.imut.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import edu.imut.dao.OrderDao;
import edu.imut.domain.Order;
import edu.imut.domain.OrderItem;
import edu.imut.exception.DaoException;
import edu.imut.util.DBCPUtil;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	public void save(Order order) {
		try {
			qr.update("insert into orders (id,orderNum,quantity,amount,status,customerId) values (?,?,?,?,?,?)", 
					order.getId(),
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getCustomer().getId());
			List<OrderItem> items = order.getItems();
			if(items.size()>0){
				for(OrderItem item:items){
					qr.update("insert into orders_item (id,quantity,price,booksId,ordersId) values (?,?,?,?,?)", 
							item.getId(),
							item.getQuantity(),
							item.getPrice(),
							item.getBook().getId(),
							order.getId());
				}
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void update(Order order) {
		try {
			qr.update("update orders set orderNum=?,quantity=?,amount=?,status=? where id=?", 
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Order findById(String orderId) {
		try {
			return qr.query("select * from orders where id=?", new BeanHandler<Order>(Order.class), orderId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Order findByOrderNum(String orderNum) {
		try {
			return qr.query("select * from orders where orderNum=?", new BeanHandler<Order>(Order.class), orderNum);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Order> findByCustomerId(String customerId) {
		try {
			return qr.query("select * from orders where customerId=? order by orderNum desc", new BeanListHandler<Order>(Order.class),customerId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	

}
