/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.opencsv.CSVReader;

@org.springframework.stereotype.Service
public class Service {
  @Autowired
  StaticToolDaoImpl staticToolDaoImpl;

  int maxComplexity;

  public void insert(String projectname) {
    staticToolDaoImpl.insert(projectname);
  }

  public boolean checkforrow(String projectname) {
    return staticToolDaoImpl.getallrows(projectname);
  }

  public Results getvalue(String projectname) {
    return staticToolDaoImpl.get(projectname);
  }

  public void updatesecurity(String projectname, int value) {
    staticToolDaoImpl.updatesecurity(projectname, value);
  }

  public void updatecomplexity(String projectname, int value) {
    staticToolDaoImpl.updatecomplexity(projectname, value);
  }

  public void updatewarnings(String projectname, int value) {
    staticToolDaoImpl.updatewarnings(projectname, value);
  }

  public void updatecoverage(String projectname, int value) {
    staticToolDaoImpl.updatecoverage(projectname, value);
  }


  public void addToResults(String pattern, File file, List<String> result) {
    if (file.isFile() && file.getName().matches(pattern)) {
      result.add(file.getAbsolutePath());
    }
  }

  public void searchFilesInDirectory(final String pattern, final File folder, List<String> result) {
    for (final File file : folder.listFiles()) {
      if (file.isDirectory()) {
        searchFilesInDirectory(pattern, file, result);
      }
      addToResults(pattern, file, result);
    }
  }

  public List<String> getAllClasses(List<String> allTests) {
    final List<String> results = new ArrayList<>();

    for (final String testFile : allTests) {
      final String[] paths = testFile.split("\\\\");
      final List<String> list=Arrays.asList(paths);
      final int x=list.indexOf("test-classes");
      String pathvar = "";
      for (int i = x+1; i < paths.length - 1; i++) {
        pathvar += paths[i] + ".";
      }
      paths[paths.length - 1] = paths[paths.length - 1].replace(".class", "");
      pathvar += paths[paths.length - 1];
      System.out.println(pathvar);
      results.add(pathvar);
    }
    return results;

  }

  public int runcommand(String[] command, String binPath) {
    final ProcessBuilder pb = new ProcessBuilder(command);
    pb.directory(new File(binPath));
    Process process;
    try {
      process = pb.start();
    } catch (final IOException e1) {
      return 1;
    }
    try {
      process.waitFor();
    } catch (final InterruptedException e) {
      return 1;
    }
    return process.exitValue();
  }

  public double createandextract(String command[]) {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    Process process;
    String s = null;
    try {
      process = pb1.start();
      final BufferedReader out =
          new BufferedReader(new InputStreamReader(process.getInputStream()));
      while ((s = out.readLine()) != null && s.charAt(0) != 'T') {
        ;
      }
    } catch (final IOException e) {
      return 0.0;
    }
    if (s == null) {
      return 0.0;
    } else {
      final double time = Double.parseDouble((String) s.subSequence(6, s.length()));
      return time * 1000;
    }
  }

  public double parseCsvFile(String file) throws IOException {
    FileReader filereader;
    try {
      filereader = new FileReader(file);
    } catch (final FileNotFoundException e1) {
      return 0.0;
    }
    final CSVReader csvReader = new CSVReader(filereader);
    double codecoverage = 0.0;
    try {
      String[] nextRecord = csvReader.readNext();
      if ((nextRecord = csvReader.readNext()) != null) {
        final int instructionscovered = Integer.parseInt(nextRecord[4]);
        final int instructionsmissed = Integer.parseInt(nextRecord[3]);
        codecoverage = (double) instructionscovered / (instructionscovered + instructionsmissed);
      }
    } catch (final Exception e) {
      e.printStackTrace();
    } finally {
      filereader.close();
      csvReader.close();
    }
    return codecoverage * 100;
  }

  public int parseTextFile(String currentdir, String projectname) throws IOException {
    String line;
    int count = 0;
    final FileReader fr = new FileReader(currentdir + "\\securityreport\\" + projectname + ".txt");

    @SuppressWarnings("resource")
    final BufferedReader br = new BufferedReader(fr);
    while ((line = br.readLine()) != null) {
      final String[] words = line.split(" ");
      if (words[0].equals("Line:")) {
        count++;
      }
    }
    return count;
  }

  public int parseXmlFile() {
    int issuescount = 0;
    try {
      final File fXmlFile =
          new File(Commands.currentdir + "\\Pmdreports\\" + Commands.projectname + ".xml");
      if (fXmlFile.exists()) {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final Document doc = dBuilder.parse(fXmlFile);
        final NodeList nList = doc.getElementsByTagName("file");
        for (int temp = 0; temp < nList.getLength(); temp++) {
          final Node nNode = nList.item(temp);
          if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            final Element eElement = (Element) nNode;
            final NodeList violations = eElement.getElementsByTagName("violation");
            issuescount += violations.getLength();
          }
        }
      } else {
        System.out.println(" file is not created");
      }
    }

    catch (final Exception e) {
      e.printStackTrace();

    }
    return issuescount;

  }



  // get details from the .txt file
  public void extractTextDetails() {
    final File file = new File(Commands.currentdir+"\\complexityreport\\"+Commands.projectname + ".txt");
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(file));
      final Map<String,Integer> functionMap=new HashMap<>();
      String st;
      while ((st = br.readLine()) != null) {
        final String splitCommand[] = st.split(",");
        putTextFileOutput(splitCommand,functionMap);
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * get the details from the string, store it into a class object
   */
  public Map<String,Integer> putTextFileOutput(String string[],Map<String,Integer> map) {
    int index = 2; // index of the elements in the text line
    while (index < string.length) {
      map.put(string[index], Integer.parseInt(string[index + 1]));
      index = index + 4;
    }
    maxComplexity=getMaxComplexity(map);
    return map;
  }

  public int getMaxiComplexity()
  {return maxComplexity;}

  // returns the maximum complexity in a class, using lambda functions
  public Integer getMaxComplexity(Map<String, Integer> map) {
    final Entry<String, Integer> maxEntry = Collections.max(map.entrySet(),
        (Entry<String, Integer> e1, Entry<String, Integer> e2) -> e1.getValue().compareTo(e2.getValue()));
    return maxEntry.getValue();
  }


  public void consoleInteractor() throws IOException {
    final Set<String> set=new HashSet<>();
    getClassContainingDirectories(Commands.projectdir,set);

    for(final String paths :set)
    {
      final Runtime r = Runtime.getRuntime();
      final Process proc = r.exec(Commands.getcyviscommand());
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
      if(files!=null) {
        for (final File file : files) {
          final int length=file.getCanonicalPath().length();
          if(file.getCanonicalPath().substring(length-5,length).equals("class")) {
            set.add(file.getCanonicalPath().substring(0,file.getCanonicalPath().lastIndexOf("\\")));
          } else if (file.isDirectory()) {
            getClassContainingDirectories(file.getCanonicalPath(),set);
          }
        }
      }

    } catch (final IOException e) {
      e.printStackTrace();
    }
  }


}
