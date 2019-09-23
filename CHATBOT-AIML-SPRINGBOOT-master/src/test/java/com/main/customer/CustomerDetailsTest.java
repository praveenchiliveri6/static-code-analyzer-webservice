/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.customer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CustomerDetailsTest {

  @Test
  public void testToString() {
    final Customer customer = new Customer("Test", "9123456789", "123456789","test@gmail.com","hi","hello");
    final String actual = customer.toString();
    final String expected = "Customer [name=Test, password=9123456789, contactNo=123456789, email=test@gmail.com, shippingAddress=hi]";
    assertEquals(expected, actual);
  }

  @Test
  public void testGetName() {
    final Customer customer = new Customer("Test", "9123456789", "123456789","test@gmail.com","hi","hello");
    final String actual = customer.getName();
    final String expected = "Test";
    assertEquals(expected, actual);
  }

  @Test
  public void testSetName() {
    final Customer customer = new Customer();
    customer.setName("Test");
    final String expected = "Test";
    final String actual = customer.getName();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetContact() {
    final Customer customer = new Customer("Test", "9123456789", "123456789","test@gmail.com","hi","hello");
    final String actual = customer.getContactNo();
    final String expected = "123456789";
    assertEquals(expected, actual);
  }

  @Test
  public void testSetContact() {
    final Customer customer = new Customer();
    customer.setContactNo("9123456789");
    final String expected = "9123456789";
    final String actual = customer.getContactNo();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetShippingAddress() {
    final Customer customer = new Customer("Test", "9123456789", "123456789","test@gmail.com","hi","hello");
    final String actual = customer.getShippingAddress();
    final String expected = "hi";
    assertEquals(expected, actual);
  }

  @Test
  public void testSetShippingAddress() {
    final Customer customer = new Customer();
    customer.setShippingAddress("tier 1");
    final String expected = "tier 1";
    final String actual = customer.getShippingAddress();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetEmail() {
    final Customer customer =new Customer("Test", "9123456789", "123456789","test@gmail.com","hi","hello");
    final String actual = customer.getEmail();
    final String expected = "test@gmail.com";
    assertEquals(expected, actual);
  }

  @Test
  public void testSetEmail() {
    final Customer customer = new Customer();
    customer.setEmail("test@gmail.com");
    final String expected = "test@gmail.com";
    final String actual = customer.getEmail();
    assertEquals(expected, actual);
  }

  @Test
  public void testPassword() {
    final Customer customer = new Customer();
    customer.setPassword("test@gmail.com");
    final String expected = "test@gmail.com";
    final String actual = customer.getPassword();
    assertEquals(expected, actual);
  }

  @Test
  public void testProductName() {
    final Customer customer = new Customer();
    customer.setProductname("test@gmail.com");
    final String expected = "test@gmail.com";
    final String actual = customer.getProductname();
    assertEquals(expected, actual);
  }
}
