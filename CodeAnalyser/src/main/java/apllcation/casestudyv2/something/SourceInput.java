/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import org.springframework.stereotype.Component;

@Component
public class SourceInput {
  String sourceCodeDirectory;
  String projectName;

  public String getSourceCodeDirectory() {
    return sourceCodeDirectory;
  }

  public void setSourceCodeDirectory(String sourceCodeDirectory) {
    this.sourceCodeDirectory = sourceCodeDirectory;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
}
