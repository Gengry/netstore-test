package edu.imut.dao;

import java.util.List;

import edu.imut.domain.Order;

public interface OrderDao {

	void save(Order order);

	Order findById(String orderId);

	Order findByOrderNum(String orderNum);

	List<Order> findByCustomerId(String customerId);

	void update(Order order);


}
