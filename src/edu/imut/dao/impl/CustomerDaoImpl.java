package edu.imut.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import edu.imut.dao.CustomerDao;
import edu.imut.domain.Customer;
import edu.imut.exception.DaoException;
import edu.imut.util.DBCPUtil;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Customer customer) {
		try{
		qr.update("insert into customer (id,name,password,phone,address,email,actived,code) value (?,?,?,?,?,?,?,?)", 
				customer.getId(),
				customer.getName(),
				customer.getPassword(),
				customer.getPhone(),
				customer.getAddress(),
				customer.getEmail(),
				customer.isActived(),
				customer.getCode());
		}catch(SQLException e){
			throw new DaoException(e);
		}
	}

}
