/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResourcePathTest {

  @Test
  public void testGetResourcesPath() {
    final String expected = "C:\\Users\\320065909\\Downloads\\CHATBOT-AIML-SPRINGBOOT-master\\CHATBOT-AIML-SPRINGBOOT-master\\src\\main\\resources";
    final String actual = ResourcePath.getResourcesPath();
    assertEquals(expected, actual);
  }

}