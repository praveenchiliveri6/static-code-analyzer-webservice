/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.philips.Service;

public class TestClass {

  Service service=new Service();
  @Test
  public void Test1() {
    final int expected=0;
    final String[]command= {"cmd", "/c","cd Users"};
    assertEquals(expected,service.runcommand(command,"C:\\"));
  }
  @Test
  public void Test2() {
    final int expected=1;
    final String[]command= {"cmd", "/c","cd src"};
    Thread.currentThread().interrupt();
    assertEquals(expected,service.runcommand(command,""));
  }

  @Test
  public void Test3() {
    final int expected=1;
    final String[]command= {""};
    assertEquals(expected,service.runcommand(command,""));
  }

  @Test
  public void Test4() {
    final double expected=0.0;
    final String[] command= {""};
    assertEquals(expected,service.createandextract(command),0.0);
  }

  @Test
  public void Test5() throws IOException {
    final double expected=0.0;
    final String file="C:\\Users";
    assertEquals(expected,service.parseCsvFile(file),0.0);
  }

  @Test
  public void Test6() throws IOException {
    final double expected=100.0;
    final String file=System.getProperty("user.dir")+"\\jacoco-report\\ExampleWebApp.csv";
    assertEquals(expected,service.parseCsvFile(file),0.0);
  }



}
