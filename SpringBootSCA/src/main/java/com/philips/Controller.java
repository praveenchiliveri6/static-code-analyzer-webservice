/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
  @Autowired
  Service service;

  @GetMapping(value="/coverage")
  public void codecoverage() throws IOException, InterruptedException {
    service.createfile();
    final double codecoverage=service.parsefile("C:\\jacoco-executable\\report2.csv");
    System.out.println("code coverage is: "+codecoverage*100);
  }

}
