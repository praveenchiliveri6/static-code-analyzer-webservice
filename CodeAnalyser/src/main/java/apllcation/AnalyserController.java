/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apllcation.casestudyv2.something.CommandLineOperator;
import apllcation.casestudyv2.something.StaticToolAnalyzer;
import apllcation.casestudyv2.something.Tool1TextFileReader;

@RestController
public class AnalyserController {

  @Autowired
  CommandLineOperator commandLineOperator;
  @Autowired
  Tool1TextFileReader tool1TExtFileReader;
  @Autowired
  StaticToolAnalyzer staticToolAnalyser;
  @RequestMapping(value="/complexity")
  public String getComplexity() throws IOException
  {
    commandLineOperator.consoleInteractor("C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp"
        + "\\bin\\com\\philips\\simpleApp");
    return "success";
  }

  @RequestMapping(value="/pmd")
  public String getPmdReport() throws IOException, InterruptedException, ExecutionException
  {
    final String projectpath="C:\\Users\\320066613\\eclipse-workspace\\com.philips.simpleApp";
    final String projectname=commandLineOperator.getProjectName(projectpath);
    staticToolAnalyser.generateReport(commandLineOperator.GetPmdCommand(projectpath, projectname),commandLineOperator.PmdBinPath , projectname);
    return "yaY";}
}
