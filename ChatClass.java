/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.chatbot;

import java.util.ArrayList;
import java.util.List;

public class ChatClass {
  String username;
  List<String> clientmsg=new ArrayList<>();
  List<String> botReply=new ArrayList<>();
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public List<String> getClientmsg() {
    return clientmsg;
  }
  public void setClientmsg(String clientmsg) {
    if(clientmsg.equals("clear")) {
      this.clientmsg.clear();
    } else {
      this.clientmsg.add(clientmsg);
    }
  }
  public List<String> getBotReply() {
    return botReply;
  }
  public void setBotReply(String botReply) {
    if(botReply.equals("clear")) {
      this.botReply.clear();
    } else {
      this.botReply.add(botReply);
    }
  }
  public ChatClass(String username, List<String> clientmsg, List<String> botReply) {
    super();
    this.username = username;
    this.clientmsg = clientmsg;
    this.botReply = botReply;
  }
  public ChatClass() {
    super();

  }


}
