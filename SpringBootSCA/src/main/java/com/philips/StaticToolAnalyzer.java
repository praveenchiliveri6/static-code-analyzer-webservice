/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

@Service
public class StaticToolAnalyzer {

  public void generateReport(String[] command, String binPath, String projectname)
      throws IOException, InterruptedException, ExecutionException {
    final ProcessBuilder pb = new ProcessBuilder(command);
    final Map<String, String> envMap = pb.environment();
    String path = envMap.get("Path");
    path += binPath;
    envMap.put("Path", path);
    pb.start();
    System.out.println(" XML file is created\n");
    Parse.parseXml(projectname);
  }

}
