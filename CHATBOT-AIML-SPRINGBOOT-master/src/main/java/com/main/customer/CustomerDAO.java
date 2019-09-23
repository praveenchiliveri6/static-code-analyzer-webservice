/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAO {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public int addCustomer(Customer customerDetails) {
    return jdbcTemplate.update("insert into client values(?,?,?,?,?,?)",customerDetails.getName(),customerDetails.getPassword(),customerDetails.getContactNo(),customerDetails.getEmail(),customerDetails.getShippingAddress(),customerDetails.getProductname());
  }

  public Customer getCustomer(String email) {
    return jdbcTemplate.queryForObject("select * from client where email=?",new CustomerMapper(),email);
  }

  public List<Customer> getCustomers() {
    return jdbcTemplate.query("select * from client",new CustomerMapper());
  }

  public List<Customer> getCustomersByProductName(String productname)
  {
    return jdbcTemplate.query("select * from client where productname='"+productname+"'",new CustomerMapper());
  }

}
