/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

public class Commands {

  public static final String[] mavenclean = {"cmd", "/c", "mvn", "clean"};
  public static final String[] mavencompile = {"cmd", "/c", "mvn", "compile"};
  public static final String[] maventestcompile = {"cmd", "/c", "mvn", "test-compile"};
  public static final String[] maveninstall = {"cmd", "/c", "mvn", "install"};
  public static final String projectdir = "C:\\Users\\320065410\\eclipse-workspace\\ExampleWebApp";
  public static final String mavenbinpath =
      "C:\\Users\\320065410\\Downloads\\apache-maven-3.6.1-bin (1)\\apache-maven-3.6.1\\bin";
  public static final String vcgPath = "C:\\Program Files (x86)\\VisualCodeGrepper";
  public static final String simianjarPath =
      "C:\\Users\\320065909\\Downloads\\bin\\simian-2.5.10.jar";
  public static String PmdBinPath = "C:\\pmd-bin-6.16.0\\pmd-bin-6.16.0\\bin";
  public static String cyvisBinPath="C:\\cyvis-0.9";

  public static String[] getsecuritycommand(String projectdir, String projectname) {
    return new String[] {"cmd", "/c", "VisualCodeGrepper.exe", "-c", "-l", "java", "-t", projectdir,
        "--results", "C:\\Users\\320065909\\Desktop\\security\\" + projectname + ".txt"};
  }

  public static String[] getduplicatecommand(String projectdir) {
    return new String[] {"cmd", "/c", "java", "-jar", simianjarPath, projectdir + "\\src\\*.java",
    "C:\\Users\\320065909\\Desktop\\reports.xml"};
  }

  public static String[] getjavaagent(String projectdir) {
    return new String[] {"cmd", "/c", "java", "-cp",
        "C:\\Users\\320065410\\Downloads\\junit-4.12.jar;C:\\Users\\320065410\\Downloads\\hamcrest-core-1.3.jar;"
            + projectdir + "\\target\\test-classes;" + projectdir + "\\target\\classes",
            "-javaagent:C:\\Users\\320065410\\Downloads\\jacoco-0.8.4\\lib\\jacocoagent.jar=destfile=C:\\jacoco-executable\\jacoco.exec",
            "org.junit.runner.JUnitCore", "com.test.AppTest"};
  };

  public static String[] getjavacommand(String projectdir) {
    return new String[] {"cmd", "/c", "java", "-jar",
        "C:\\Users\\320065410\\Downloads\\jacoco-0.8.4\\lib\\jacococli.jar", "report",
        "C:\\jacoco-executable\\jacoco.exec", "--classfiles", projectdir + "\\target\\classes",
        "--sourcefiles", projectdir + "\\src", "--csv", "C:\\jacoco-executable\\report2.csv"};
  }

  public static String[] gettestcommand(String projectdir) {
    return new String[] {"java", "-cp",
        "C:\\Users\\320065410\\Downloads\\junit-4.12.jar;C:\\Users\\320065410\\Downloads\\hamcrest-core-1.3.jar;"
            + projectdir + "\\target\\test-classes;" + projectdir + "\\target\\classes",
            "org.junit.runner.JUnitCore", "com.test.AppTest"};
  }

  public static String[] getcomplexitycommand(String projectdir,String projectname) {
    return new String[] { "cmd", "/c", "cd",cyvisBinPath, "&&", "jar", "cf", "jar1.jar",
        projectdir, "&&", "java", "-jar",
        "cyvis-0.9.jar", "-p", "jar1.jar", "-t", projectname+".txt" };

  }

  public static String[] getpmdcommand(String projectdir, String projectName) {
    final String[] PmdCommand = { "cmd", "/c", "pmd", "-d", projectdir, "-f", "xml", "-R", "rulesets/java/quickstart.xml",
        ">",projectdir + projectName + ".xml" };

    return PmdCommand;
  }



}
