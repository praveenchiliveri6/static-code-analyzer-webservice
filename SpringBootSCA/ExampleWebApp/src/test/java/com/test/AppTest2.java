/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.philips.Standardclass;
public class AppTest2 {
  Standardclass app=new Standardclass();

  @Test
  public void Test2() {
    final String expected="goodmorning";
    assertEquals(expected,app.sayGoodMorning());
  }

}