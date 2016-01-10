package edu.imut.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import edu.imut.dao.CustomerDao;
import edu.imut.domain.Customer;
import edu.imut.exception.DaoException;
import edu.imut.util.DBCPUtil;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	public void save(Customer customer) {
		try {
			qr.update(
					"insert into customer (id,name,password,phone,address,email,actived,code) value (?,?,?,?,?,?,?,?)",
					customer.getId(), customer.getName(),
					customer.getPassword(), customer.getPhone(),
					customer.getAddress(), customer.getEmail(),
					customer.isActived(), customer.getCode());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Customer findByCode(String code) {
		try {
			return qr.query("select * from customer where code=?",
					new BeanHandler<Customer>(Customer.class), code);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void update(Customer customer) {
		try {
			qr.update(
					"update customer set name=?,password=?,phone=?,address=?,email=?,actived=?,code=? where id=?",
					customer.getName(), customer.getPassword(),
					customer.getPhone(), customer.getAddress(),
					customer.getEmail(), customer.isActived(),
					customer.getCode(), customer.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Customer fingCustomer(String name, String password) {
		try {
			return qr.query(
					"select * from customer where name=? and password=?",
					new BeanHandler<Customer>(Customer.class), name, password);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}
