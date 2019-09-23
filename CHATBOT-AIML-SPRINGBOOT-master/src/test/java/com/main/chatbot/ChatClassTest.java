/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.chatbot;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ChatClassTest {

  @Test
  public void testGetUsername() {
    final ChatClass chatClass = new ChatClass("test",new ArrayList<String>(),new ArrayList<String>());
    final String expected = "test";
    final String actual = chatClass.getUsername();
    assertEquals(expected, actual);
  }

  @Test
  public void testSetUsername() {
    final ChatClass chatClass = new ChatClass();
    chatClass.setUsername("test");
    final String expected = "test";
    final String actual = chatClass.getUsername();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetClientmsg() {
    final List<String> clientmsg=new ArrayList<>();
    clientmsg.add("hello");
    final ChatClass chatClass = new ChatClass("test",clientmsg,new ArrayList<String>());
    final List<String> expected=new ArrayList<>();
    expected.add("hello");
    final List<String> actual = chatClass.getClientmsg();
    assertEquals(expected, actual);
    Assert.assertArrayEquals("failure", expected.toArray(), actual.toArray());
  }

  @Test
  public void testSetClientmsg() {
    final ChatClass chatClass = new ChatClass();
    chatClass.setClientmsg("Hi");
    final List<String> expected =new ArrayList<>();
    expected.add("Hi");
    final List<String> actual = chatClass.getClientmsg();
    Assert.assertArrayEquals("failure", expected.toArray(), actual.toArray());
  }

  @Test
  public void testGetBotReply() {
    final List<String> botreply=new ArrayList<>();
    botreply.add("hello");
    final ChatClass chatClass = new ChatClass("test",new ArrayList<String>(),botreply);
    final List<String> expected=new ArrayList<>();
    expected.add("hello");
    final List<String> actual = chatClass.getBotReply();
    assertEquals(expected, actual);
    Assert.assertArrayEquals("failure", expected.toArray(), actual.toArray());
  }

  @Test
  public void testSetBotReply() {
    final ChatClass chatClass = new ChatClass();
    chatClass.setBotReply("Hi");
    final List<String> expected =new ArrayList<>();
    expected.add("Hi");
    final List<String> actual = chatClass.getBotReply();
    Assert.assertArrayEquals("failure", expected.toArray(), actual.toArray());
  }

}
