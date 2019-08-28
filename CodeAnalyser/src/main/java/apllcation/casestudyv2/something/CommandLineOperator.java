/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CommandLineOperator {

  String projectname;
  public static String PmdBinPath = "C:\\pmd-bin-6.16.0\\pmd-bin-6.16.0\\bin";
  public static String destinationPath = "C:\\Users\\320066613\\CodeAnalysis\\Reports\\";

  public String getProjectName(String projectPath)
  {	final String projectSplit[]=projectPath.split("\\\\");
  this.projectname=projectSplit[projectSplit.length-1];
  System.out.println(this.projectname);
  return this.projectname;}


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


  public static void getClassContainingDirectories(String projectpath, Set<String> x) {
    final File dir=new File(projectpath);
    try {
      final File[] files = dir.listFiles();
      for (final File file : files) {
        final int length=file.getCanonicalPath().length();
        if(file.getCanonicalPath().substring(length-5,length).equals("class")) {
          x.add(file.getCanonicalPath().substring(0,file.getCanonicalPath().lastIndexOf("\\")));
        } else if (file.isDirectory()) {
          getClassContainingDirectories(projectpath,x);
        }
      }

    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}
