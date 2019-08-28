/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

public class Commands {

  //Maven Commands
  public static final String[] mavenclean = {"cmd", "/c", "mvn", "clean"};
  public static final String[] mavencompile = {"cmd", "/c", "mvn", "compile"};
  public static final String[] maventestcompile = {"cmd", "/c", "mvn", "test-compile"};
  public static final String[] maveninstall = {"cmd", "/c", "mvn", "install"};
  public static final String mavenbinpath ="C:\\apache-maven-3.6.1\\bin";


  public static final String vcgPath = "C:\\Program Files (x86)\\VisualCodeGrepper";
  public static final String simianjarPath = "C:\\Users\\320065909\\Downloads\\bin\\simian-2.5.10.jar";

  public static String projectdir="C:\\",currentdir,projectname;

  public static void setProjectdir(String dir) {
    projectdir=dir;
  }

  public static void setCurrentdir(String dir) {
    currentdir=dir;
  }

  public static void setProjectname(String dir) {
    projectname=dir;
  }

  public static String[] getsecuritycommand() {
    return new String[] {"cmd", "/c", "VisualCodeGrepper.exe", "-c", "-l", "java",
        "-t",projectdir,"--results",
        currentdir+"\\security\\" +projectname+".txt"};
  }

  public static String[] getduplicatecommand() {
    return new String[] {"cmd", "/c", "java", "-jar", simianjarPath,
        projectdir+"\\src\\*.java",
    "C:\\Users\\320065909\\Desktop\\reports.xml"};
  }

  public static String[] getjavaagent(String classname) {
    return new String []{"cmd", "/c", "java", "-cp",
        "C:\\junit-4.12.jar;C:\\hamcrest-core-1.3.jar;"+projectdir+"\\target\\test-classes;"+projectdir+"\\target\\classes",
        "-javaagent:C:\\jacocoagent.jar=destfile="+currentdir+"\\jacoco-report\\jacoco-"+projectname+".exec",
        "org.junit.runner.JUnitCore", classname};
  }

  public static String[] getjavacommand() {
    return new String []{"cmd", "/c","java", "-jar",
        "C:\\jacococli.jar", "report",
        currentdir+"\\jacoco-report\\jacoco-"+projectname+".exec", "--classfiles",
        projectdir+"\\target\\classes", "--sourcefiles",
        projectdir+"\\src", "--csv",
        currentdir+"\\jacoco-report\\"+projectname+".csv"};
  }

  public static String[] gettestcommand(String classname) {
    return new String[] {"java", "-cp",
        "C:\\junit-4.12.jar;C:\\hamcrest-core-1.3.jar;"+projectdir+"\\target\\test-classes;"+projectdir+"\\target\\classes",
        "org.junit.runner.JUnitCore", classname};
  }
}
