/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "USER DETAILS")
@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

  @Autowired
  CustomerDAO customerDAO;

  Customer customer = new Customer();


  @ApiOperation(value = "adds customer to db")
  @PostMapping(value = "/user/add",consumes="application/json")
  public @ResponseBody Customer add(@RequestBody Customer customerObj) {
    System.out.println(customerObj.toString()+customerObj.getProductname());
    customer.setName(customerObj.getName());
    customer.setPassword(customerObj.getPassword());
    customer.setContactNo(customerObj.getContactNo());
    customer.setEmail(customerObj.getEmail());
    customer.setShippingAddress(customerObj.getShippingAddress());
    customer.setProductname(customerObj.getProductname());
    customerDAO.addCustomer(customer);
    return customer;
  }

  @ApiOperation(value = "get customer from db by email")
  @GetMapping(value = "/user/{email}")
  public Customer getCustomerByEmail(@PathVariable("email") String email) {
    return customerDAO.getCustomer(email);
  }

  @ApiOperation(value = "get all the customers")
  @GetMapping(value="/users")
  public List<Customer> getCustomers(){
    return customerDAO.getCustomers();
  }

  @ApiOperation(value = "get all the customers by product name")
  @GetMapping(value="/customers/{productname}")
  public List<Customer> getCustomersByproductName(@PathVariable("productname") String productname){
    final List<Customer> list=customerDAO.getCustomersByProductName(productname);
    for(final Customer c:list) {
      System.out.println(c.contactNo);
    }
    System.out.println("entered "+productname);
    return list;
  }
}

