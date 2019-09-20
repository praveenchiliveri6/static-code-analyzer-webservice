/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.main.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.main.device.DeviceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins="http://localhost:4200")
@RestController
@Api(value = "CHATBOT")
@RequestMapping(value = "/api/employees")
public class ChatbotController {


  @Autowired
  DeviceUtil deviceUtility;
  ChatClass chat=new ChatClass();
  @ApiOperation(value = "Returns PROPER REPLY OR NEXT PAGE")
  @GetMapping(value = "/chat")
  public ChatClass getBotReply(@RequestParam("message") String clientmsg, @RequestParam("username") String username){

    chat.setUsername(username);
    chat.setClientmsg(clientmsg);
    if(clientmsg.compareTo("clear")!=0) {
      chat.setBotReply(ChatBot.getBotReply(clientmsg));
    }
    else {
      chat.setBotReply(clientmsg);
    }
    deviceUtility.util(clientmsg, username);

    return chat;
  }
}
