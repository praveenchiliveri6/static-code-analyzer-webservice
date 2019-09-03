/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
  @Autowired
  Service service;
  @Autowired
  SourceInput si;
  List<String> classnames;

  @RequestMapping(value="/enter")
  public String homepage() {
    return "home";
  }

  @RequestMapping(value="/start")
  public String compileandtest(@RequestParam("v1") String projectname,@RequestParam("v2") String projectdir) {
    Commands.setProjectname(projectname);
    Commands.setProjectdir(projectdir);
    Commands.setCurrentdir(System.getProperty("user.dir"));
    final int returnvalue=0;
    /*
     * returnvalue=service.runcommand(Commands.mavenclean, Commands.projectdir);
     * returnvalue=service.runcommand(Commands.mavencompile, Commands.projectdir);
     * returnvalue=service.runcommand(Commands.maventestcompile, Commands.projectdir);
     * returnvalue=service.runcommand(Commands.maveninstall, Commands.projectdir);
     */final File pomfile=new File(projectdir+"\\pom.xml");
     if(returnvalue==1||!pomfile.exists()) {
       System.out.println("Please give the maven project as input");
       return "nogo";
     }

     final List<String> resultFiles = new ArrayList<>();
     service.searchFilesInDirectory(".*\\.class",
         new File(Commands.projectdir + "/target/test-classes"), resultFiles);
     classnames = service.getAllClasses(resultFiles);

     if (!service.checkforrow(Commands.projectname)) {
       service.insert(Commands.projectname);
     }
     return "choose";
  }

  @GetMapping(value = "/coverage")
  public String codecoverage() throws IOException {

    for (final String classname : classnames) {
      service.runcommand(Commands.getjavaagent(classname), Commands.projectdir);
    }
    service.runcommand(Commands.getjavacommand(), Commands.projectdir);
    final double cc = service
        .parseCsvFile(Commands.currentdir + "\\jacoco-report\\" + Commands.projectname + ".csv");
    final Results result = service.getvalue(Commands.projectname);
    final int codecoverage = (int) cc;
    final int prevresult = result.getCodecoverage();
    service.updatecoverage(Commands.projectname, codecoverage);
    if (codecoverage <= 80) {
      return "nogo";
    } else {
      if (prevresult > codecoverage) {
        System.out.println("codecoverage is worse than the previous");
      }
      return "go";

    }
  }

  @GetMapping(value = "/test")
  public String unittesting() {
    boolean flag = true;
    for (final String classname : classnames) {
      final double time = service.createandextract(Commands.gettestcommand(classname));
      if (time > 20) {
        flag = false;
      }
      System.out.println(time + "ms");
    }
    if (flag) {
      return "go";
    } else {
      return "nogo";
    }

  }

  @GetMapping(value = "/security")
  public String showReport() throws IOException {
    service.runcommand(Commands.getsecuritycommand(), Commands.getvcgpath());
    final int count = service.parseTextFile(Commands.currentdir,Commands.projectname);
    final Results result = service.getvalue(Commands.projectname);
    final int prevcount = result.getSecurityvulnerability();
    service.updatesecurity(Commands.projectname, count);
    if (count != 0) {
      return "nogo";
    } else {
      if (prevcount > count) {
        System.out.println("security warnings are worse than the previous");
      }
      return "go";
    }
  }

  @GetMapping("/duplicate")
  public String showDuplicates() {
    final int duplicate =
        service.runcommand(Commands.getduplicatecommand(), Commands.getsimianpath());
    if (duplicate == 1) {
      return "go";
    } else {
      return "nogo";
    }
  }

  @GetMapping(value = "/warnings")
  public String getPmdReport() {
    service.runcommand(Commands.getPmdCommand(), Commands.getpmdbinpath());
    final int count = service.parseXmlFile();
    final Results result = service.getvalue(Commands.projectname);
    final int prevcount = result.getStaticwarnings();
    service.updatewarnings(Commands.projectname, count);
    if (count != 0) {
      System.out.println("no go");
      return "nogo";
    } else {
      if (prevcount > count) {
        System.out.println("static warnings are worse than the previous");
      }
      System.out.println("go");
      return "go";

    }
  }

  @GetMapping(value="/complexity")
  public String getComplexity() throws IOException
  {
    int maxcomplexity=0;
    service.consoleInteractor();
    final Set<String> set=new HashSet<>();
    service.getClassContainingDirectories(Commands.projectdir, set);
    for(final String s:set) {
      service.extractTextDetails();
      maxcomplexity=Math.max(service.getMaxiComplexity(),maxcomplexity);
    }
    service.updatecomplexity(Commands.projectname,maxcomplexity);
    if(maxcomplexity>3) {
      return "nogo";
    } else {
      return "go";
    }
  }

}
