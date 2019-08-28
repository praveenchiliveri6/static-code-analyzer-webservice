/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
  @Autowired
  Service service;
  @Autowired
  SourceInput si;
  @Autowired
  CommandLineOperator commandLineOperator;
  @Autowired
  Tool1TextFileReader tool1TExtFileReader;
  @Autowired
  StaticToolAnalyzer staticToolAnalyser;
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
    service.createfile(Commands.getjavaagent(projectdir));
    service.createfile(Commands.getjavacommand(projectdir));
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
    final int duplicate=service.createfile(Commands.getduplicatecommand(projectdir));
    if(duplicate==1) {
      System.out.println("duplicates found");
    } else {
      System.out.println("No duplicates found");
    }
  }
  
  @RequestMapping(value="/complexity")
  public String getComplexity() throws IOException
  {
    commandLineOperator.consoleInteractor("C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp"
        + "\\bin\\com\\philips\\simpleApp");
    return "success";
  }

  @RequestMapping(value="/pmd")
  public int getPmdReport() throws IOException, InterruptedException, ExecutionException
  {
    final String projectpath="C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp";
    final String projectname=commandLineOperator.getProjectName(projectpath);
    staticToolAnalyser.generateReport(commandLineOperator.GetPmdCommand(projectpath, projectname),commandLineOperator.PmdBinPath , projectname);
    return Parse.no_of_issues;}
}