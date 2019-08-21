/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

public class Commands {

  public static final String javaagentcommand[] = {"cmd", "/c", "java", "-cp",
      "C:\\Users\\320065410\\Downloads\\junit-4.12.jar;C:\\Users\\320065410\\Downloads\\hamcrest-core-1.3.jar;C:\\Users\\320065410\\eclipse-workspace\\MavenDemoProject\\target\\test-classes;C:\\Users\\320065410\\eclipse-workspace\\MavenDemoProject\\src\\main\\java",
      "-javaagent:C:\\Users\\320065410\\Downloads\\jacoco-0.8.4\\lib\\jacocoagent.jar=destfile=C:\\jacoco-executable\\jacoco.exec",
      "org.junit.runner.JUnitCore", "com.test.AppTest"};
  public static final String javacommand[] = {"cmd", "/c", "java", "-jar",
      "C:\\Users\\320065410\\Downloads\\jacoco-0.8.4\\lib\\jacococli.jar", "report",
      "C:\\jacoco-executable\\jacoco.exec", "--classfiles",
      "C:\\Users\\320065410\\eclipse-workspace\\MavenDemoProject\\src\\main\\java\\com\\philips",
      "--sourcefiles", "C:\\Users\\320065410\\eclipse-workspace\\MavenDemoProject\\src", "--csv",
  "C:\\jacoco-executable\\report2.csv"};
}
