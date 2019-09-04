/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.opencsv.CSVReader;

@Service
public class StaticToolService {
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

  public void searchFilesInSubDirectory(final String pattern, final File folder, List<String> result) {
    for (final File file : folder.listFiles()) {
      if (file.isDirectory()) {
        searchFilesInSubDirectory(pattern, file, result);
      }
      addToResults(pattern, file, result);
    }
  }

  public List<String> getAllClassesWithPackage(List<String> allTests) {
    final List<String> results = new ArrayList<>();

    for (final String testFile : allTests) {
      final String[] paths = testFile.split("\\\\");
      final List<String> list = Arrays.asList(paths);
      final int index = list.indexOf("test-classes");
      String pathvar = "";
      for (int i = index + 1; i < paths.length - 1; i++) {
        pathvar += paths[i] + ".";
      }
      paths[paths.length - 1] = paths[paths.length - 1].replace(".class", "");
      pathvar += paths[paths.length - 1];
      results.add(pathvar);
    }
    return results;

  }

  public int runCommandLineArgument(String[] command, String binPath) {
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

  public double createFile(String command[]) throws IOException {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    Process process;
    final BufferedReader out;
    try {
      process = pb1.start();
      out = new BufferedReader(new InputStreamReader(process.getInputStream()));

    } catch (final IOException e) {
      return 0.0;
    }
    return extractFile(out);
  }

  public double extractFile(BufferedReader br) throws IOException {
    String s = null;
    while ((s = br.readLine()) != null && s.charAt(0) != 'T') {
      ;
    }
    final double time = Double.parseDouble((String) s.subSequence(6, s.length()));
    return time * 1000;

  }

  public int parseCsvFile(String file) throws IOException {
    FileReader filereader;
    try {
      filereader = new FileReader(file);
    } catch (final FileNotFoundException e1) {
      return 0;
    }
    final CSVReader csvReader = new CSVReader(filereader);
    return getCodeCoverage(csvReader);
  }

  public int getCodeCoverage(CSVReader csvReader) throws IOException {
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
    }
    csvReader.close();
    return (int)codecoverage * 100;

  }

  public int parseTextFile(String projectname, String currentdir) throws IOException {
    FileReader fr;
    try {
      final File f = new File(currentdir + "\\security\\" + projectname + ".txt");
      fr = new FileReader(f);
    } catch (final FileNotFoundException e) {
      return -1;
    }
    final BufferedReader br = new BufferedReader(fr);
    return getNoOfVulnerabilities(br);
  }

  public int getNoOfVulnerabilities(BufferedReader br) throws IOException {
    String line;
    int count = 0;
    while ((line = br.readLine()) != null) {
      final String[] words = line.split(" ");
      if (words[0].equals("Line:")) {
        count++;
      }
    }
    return count;
  }


  public int parseXML(String projectname, String currentdir) {
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    NodeList nList = null;

    try {

      final File fXmlFile = new File(currentdir + "\\Pmdreports\\" + projectname + ".xml");
      if (fXmlFile.exists()) {
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
        nList = doc.getElementsByTagName("file");
      }


    } catch (final Exception e) {
      return -1;

    }
    return getNoOfWarnings(nList);
  }

  public int getNoOfWarnings(NodeList nList) {
    int noofissues = 0;
    for (int temp = 0; temp < nList.getLength(); temp++) {
      final Node nNode = nList.item(temp);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        final Element eElement = (Element) nNode;
        final NodeList violations = eElement.getElementsByTagName("violation");
        noofissues += violations.getLength();

      }
    }
    return noofissues;
  }

  public void extractTextDetails() {
    final File file =
        new File(Commands.currentdir + "\\complexityreport\\" + Commands.projectname + ".txt");
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(file));
      final Map<String, Integer> functionMap = new HashMap<>();
      String st;
      while ((st = br.readLine()) != null) {
        final String splitCommand[] = st.split(",");
        putTextFileOutput(splitCommand, functionMap);
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public Map<String, Integer> putTextFileOutput(String string[], Map<String, Integer> map) {
    int index = 2;
    while (index < string.length) {
      map.put(string[index], Integer.parseInt(string[index + 1]));
      index = index + 4;
    }
    maxComplexity = getMaxComplexity(map);
    return map;
  }

  public int getMaxiComplexity() {
    return maxComplexity;
  }

  public Integer getMaxComplexity(Map<String, Integer> map) {
    final Entry<String, Integer> maxEntry =
        Collections.max(map.entrySet(), (Entry<String, Integer> e1, Entry<String, Integer> e2) -> e1
            .getValue().compareTo(e2.getValue()));
    return maxEntry.getValue();
  }


  public void consoleInteractor() throws IOException {
    final Runtime r = Runtime.getRuntime();
    final Process proc = r.exec(Commands.getcyviscommand());
    final BufferedReader stdError =
        new BufferedReader(new InputStreamReader(proc.getErrorStream()));
    String s = null;
    while ((s = stdError.readLine()) != null) {
      System.out.println(s);
    }
  }




  public String propertiesFileReader(String key) {
    try (InputStream inStream = new FileInputStream(Commands.currentdir+"//src//main//resources//application.properties")) {

      final Properties prop = new Properties();
      prop.load(inStream);
      return prop.getProperty(key);

    } catch (final IOException io) {
      io.printStackTrace();
    }
    return null;
  }

  public int compare(String userconfig,String defaultValue) {
    if(userconfig.equals("1000")) {
      return Integer.parseInt(defaultValue);
    } else {
      return Integer.parseInt(userconfig);
    }
  }


}
