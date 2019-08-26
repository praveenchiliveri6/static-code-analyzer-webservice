/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*class to get the cyclometric complexity
 *of every function and store it into a class
 */

@Service
public class Tool1TextFileReader {

  int complexityLimit;
  @Autowired
  Tool1 tool1;
  // get details from the .txt file
  public void extractTextDetails(String projectname) {
    final File file = new File("C:\\cyvis-0.9\\" + projectname + ".txt");
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null) {
        final String splitCommand[] = st.split(",");
        System.out.println(putTextFileOutput(splitCommand).toString());
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * get the details from the string, store it into a class object
   */
  public Tool1 putTextFileOutput(String string[]) {
    tool1.setPackagename(string[0]);
    tool1.setClassname(string[1]);
    final Map<String, Integer> map = new HashMap<>();
    int index = 2; // index of the elements in the text line
    System.out.println(string.length);
    while (index < string.length) {
      map.put(string[index], Integer.parseInt(string[index + 1]));
      index = index + 4;
    }
    tool1.setFunctions(map);
    return tool1;
  }

  // returns the maximum complexity in a class, using lambda functions
  public <String, Integer extends Comparable<Integer>> Integer getMaxComplexity(Map<String, Integer> map) {
    final Entry<String, Integer> maxEntry = Collections.max(map.entrySet(),
        (Entry<String, Integer> e1, Entry<String, Integer> e2) -> e1.getValue().compareTo(e2.getValue()));
    return maxEntry.getValue();
  }

}
