/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  @Autowired
  Service service;
  @Autowired
  SourceInput si;

  String projectdir;
  String projectname;

  @PostMapping(path = "/start", consumes = "application/json")
  public void compileandtest(@RequestBody SourceInput si) throws IOException, InterruptedException {
    projectdir = si.getSourceCodeDirectory();
    projectname = si.getProjectName();
    System.out.println(projectdir+" "+projectname);
    service.runcommand(Commands.mavenclean,Commands.mavenbinpath);
    service.runcommand(Commands.mavencompile,Commands.mavenbinpath);
    service.runcommand(Commands.maventestcompile,Commands.mavenbinpath);
    service.runcommand(Commands.maveninstall,Commands.mavenbinpath);
    if (!service.checkforrow(projectname)) {
      service.insert(projectname);
    }

  }

  @GetMapping(value = "/coverage")
  public void codecoverage() throws IOException, InterruptedException {
    service.runcommand(Commands.getjavaagent(projectdir),"");
    service.runcommand(Commands.getjavacommand(projectdir),"");
    final double cc = service.parseCsvFile("C:\\jacoco-executable\\report2.csv");
    final Results result = service.getvalue(projectname);
    final int codecoverage = (int) cc;
    final int prevresult = result.getCodecoverage();
    service.updatevalue(projectname, codecoverage);
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
  public void unittesting() throws IOException {
    service.createandextract(Commands.gettestcommand(projectdir));
  }

  @GetMapping(value = "/security")
  public void showReport() throws IOException, InterruptedException {
    service.runcommand(Commands.getsecuritycommand(projectdir, projectname),Commands.vcgPath);
    service.parseTextFile(projectname);
  }

  @GetMapping("/duplicate")
  public void showDuplicates() throws IOException, InterruptedException {
    final int duplicate=service.runcommand(Commands.getduplicatecommand(projectdir),"");
    if(duplicate==1) {
      System.out.println("duplicates found");
    } else {
      System.out.println("No duplicates found");
    }
  }

  @GetMapping(value="/complexity")
  public int getComplexity() throws IOException{
    {
      service.runcommand(Commands.getcomplexitycommand(projectdir, projectname),Commands.cyvisBinPath);

    }

    @GetMapping(value="/pmd")
    public void getPmdReport() throws IOException, InterruptedException
    {
      /*final String projectpath="C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp";
      final String projectname=commandLineOperator.getProjectName(projectpath);*/
      service.runcommand(Commands.getpmdcommand(projectdir, projectname),Commands.PmdBinPath);
      service.parseXML(projectname);
    }

  }
