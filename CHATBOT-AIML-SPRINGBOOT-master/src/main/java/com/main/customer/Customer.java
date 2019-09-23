/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.customer;

public class Customer {

  String name;
  String password;
  String contactNo;
  String email;
  String shippingAddress;
  String productname;

  public Customer(String name, String password, String contactNo, String email,String shippingAddress,String productName) {

    this.name = name;
    this.password = password;
    this.contactNo = contactNo;
    this.email = email;
    this.shippingAddress = shippingAddress;
    this.productname=productName;
  }
  @Override
  public String toString() {
    return "Customer [name=" + name + ", password=" + password + ", contactNo=" + contactNo
        + ", email=" + email + ", shippingAddress=" + shippingAddress + "]";
  }
  public String getProductname() {
    return productname;
  }
  public void setProductname(String productName) {
    this.productname = productName;
  }
  public Customer() {

  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getContactNo() {
    return contactNo;
  }
  public void setContactNo(String contactNo) {
    this.contactNo = contactNo;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getShippingAddress() {
    return shippingAddress;
  }
  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }





}
