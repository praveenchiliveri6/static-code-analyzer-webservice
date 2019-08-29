/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

  String projectName;
  public static String PmdBinPath = "C:\\pmd-bin-6.16.0\\pmd-bin-6.16.0\\bin";
  public static String destinationPath = "C:\\Users\\320066613\\CodeAnalysis\\Reports\\";

  public String getProjectName(String projectPath)
  { final String projectSplit[]=projectPath.split("\\\\");
  this.projectName=projectSplit[projectSplit.length-1];
  System.out.println(this.projectName);
  return this.projectName;}


  public String[] createCommand(String projectPath) {
    return new String[] { "cmd", "/c", "cd", "C:\\cyvis-0.9", "&&", "jar", "cf", "jar1.jar",
        projectPath, "&&", "java", "-jar",
        "cyvis-0.9.jar", "-p", "jar1.jar", "-t", getProjectName(projectPath)+".txt" };

  }

  public String[] GetPmdCommand(String projectdir, String projectName) {
    final String[] PmdCommand = { "cmd", "/c", "pmd", "-d", projectdir, "-f", "xml", "-R", "rulesets/java/quickstart.xml",
        ">", destinationPath + projectName + ".xml" };

    return PmdCommand;
  }
  @Autowired
  Tool1TextFileReader tool1TextFileReader;

  public void consoleInteractor(String projectPath) throws IOException {
    final Set<String> set=new HashSet<>();
    getClassContainingDirectories(projectPath,set);
    for(final String paths :set)
    {   final Runtime r = Runtime.getRuntime();
    final Process proc = r.exec(createCommand(paths));
    final BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
    String s = null;
    while ((s = stdError.readLine()) != null) {
      System.out.println(s);
    }
    }

  }


  public void getClassContainingDirectories(String projectpath,Set<String> set) {
    final File dir=new File(projectpath);
    try {
      final File[] files = dir.listFiles();
      for (final File file : files) {
        final int length=file.getCanonicalPath().length();
        if(file.getCanonicalPath().substring(length-5,length).equals("class")) {
          set.add(file.getCanonicalPath().substring(0,file.getCanonicalPath().lastIndexOf("\\")));
        } else if (file.isDirectory()) {
          getClassContainingDirectories(file.getCanonicalPath(),set);
        }
      }

    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
