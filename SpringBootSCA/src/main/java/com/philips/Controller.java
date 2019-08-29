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
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controller {
  @Autowired
  Service service;
  @Autowired
  SourceInput si;
  @Autowired
  Commands commands;
  @Autowired
  Tool1TextFileReader tool1TextFileReader;
  @Autowired
  StaticToolAnalyzer staticToolAnalyzer;

  List<String> classnames;

  @PostMapping(path = "/start", consumes = "application/json")
  public void compileandtest(@RequestBody SourceInput si) {
    Commands.setProjectname(si.getProjectName());
    Commands.setProjectdir(si.getSourceCodeDirectory());
    Commands.setCurrentdir(System.getProperty("user.dir"));
    final List<String> resultFiles = new ArrayList<>();
    service.searchFilesInDirectory(".*\\.class",
        new File(Commands.projectdir + "/target/test-classes"), resultFiles);
    classnames = service.getAllClasses(resultFiles);
    service.runcommand(Commands.mavenclean, Commands.projectdir);
    service.runcommand(Commands.mavencompile, Commands.projectdir);
    service.runcommand(Commands.maventestcompile, Commands.projectdir);
    service.runcommand(Commands.maveninstall, Commands.projectdir);
    if (!service.checkforrow(Commands.projectname)) {
      service.insert(Commands.projectname);
    }


  }

  @GetMapping(value = "/coverage")
  public void codecoverage() throws IOException {

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
      System.out.println("no go");
    } else {
      System.out.println("go");
      if (prevresult > codecoverage) {
        System.out.println("codecoverage is worse than the previous");
      }
    }
  }

  @GetMapping(value = "/test")
  public void unittesting() {
    for (final String classname : classnames) {
      final double time = service.createandextract(Commands.gettestcommand(classname));
      System.out.println(time + "ms");
    }
  }

  @GetMapping(value = "/security")
  public void showReport() throws IOException {
    service.runcommand(Commands.getsecuritycommand(), Commands.vcgPath);
    service.parseTextFile(Commands.projectname);

  }

  @GetMapping("/duplicate")
  public void showDuplicates() {
    final int duplicate = service.runcommand(Commands.getduplicatecommand(), "");
    if (duplicate == 1) {
      System.out.println("duplicates found");
    } else {
      System.out.println("No duplicates found");
    }
  }

  @RequestMapping(value="/complexity",produces="application/json")
  public void getComplexity(/*@RequestParamString projectpath*/) throws IOException
  {
    final String projectname="C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp";
    commands.consoleInteractor(projectname);
    final Set<String> set=new HashSet<>();
    commands.getClassContainingDirectories(projectname, set);
    for(final String s:set) {
      tool1TextFileReader.extractTextDetails(commands.getProjectName(s));
    }
    System.out.println(tool1TextFileReader.getMaxiComplexity());
  }

  @RequestMapping(value="/pmd")
  public void getPmdReport() throws IOException, InterruptedException, ExecutionException
  {
    final String projectpath="C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp";
    final String projectname=commands.getProjectName(projectpath);
    staticToolAnalyzer.generateReport(commands.GetPmdCommand(projectpath, projectname),commands.PmdBinPath , projectname);
    System.out.println(Parse.no_of_issues);}

}
