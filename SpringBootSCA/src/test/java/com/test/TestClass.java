/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.test;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import com.philips.Service;

public class TestClass {

  Service service=new Service();
  @Test
  public void Test1() {
    final int expected=0;
    final String[]command= {"cmd", "/c","cd Program Files"};
    assertEquals(expected,service.runcommand(command,"C:\\"));
  }
  @Test
  public void Test2() {
    final int expected=1;
    final String[]command= {"cmd", "/c","cd Openfolder"};
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

  @Test
  public void Test7() {
    final List<String> result=new ArrayList<>();
    final int expected=1;
    service.addToResults(".*\\.csv", new File(System.getProperty("user.dir")+"\\jacoco-report\\ExampleWebApp.csv"), result);
    assertEquals(expected,result.size());
  }

  @Test
  public void Test8() {
    final int expected=3;
    final List<String> list=new ArrayList<>(Arrays.asList("C:\\proj1\\test-classes\\com\\philips\\test1.class","C:\\proj2\\test-classes\\com\\philips\\test2.class","C:\\proj3\\test-classes\\com\\philips\\test3.class"));
    final List<String> results=service.getAllClasses(list);
    assertEquals(expected,results.size());
  }

  @Test
  public void Test9() {
    final String command[]= {"cmd","/c","echo Time: 0.004"};
    final double expected=4.0;
    assertEquals(expected,service.createandextract(command),0.0);
  }

  @Test
  public void Test10() {
    final String command[]= {"cmd","/c","echo Hello"};
    final double expected=0.0;
    assertEquals(expected,service.createandextract(command),0.0);
  }

  @Test
  public void Test11() throws IOException {
    assertEquals(3,service.parseTextFile(System.getProperty("user.dir"),"ExampleWebApp"));
  }

}
