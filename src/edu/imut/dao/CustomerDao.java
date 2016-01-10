package edu.imut.dao;

import edu.imut.domain.Customer;

public interface CustomerDao {

	void save(Customer customer);

	Customer findByCode(String code);

	void update(Customer customer);

	Customer fingCustomer(String name, String password);

}
