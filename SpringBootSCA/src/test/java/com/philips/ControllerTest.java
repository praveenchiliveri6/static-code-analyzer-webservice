/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ControllerTest {

  Controller controller=new Controller();
  @Test
  public void test() {
    final SourceInput si=new SourceInput();
    si.setProjectName("ExampleWebApp");
    si.setSourceCodeDirectory("C:/Users/320065410/eclipse-workspace/ExampleWebApp");
    final String actual=controller.compileandtest(si);
    final String expected="mainpage";
    assertEquals(expected,actual);

  }

}
