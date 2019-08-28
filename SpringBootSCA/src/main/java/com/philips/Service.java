/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
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

  public void insert(String projectname) {
    staticToolDaoImpl.insert(projectname);
  }

  public boolean checkforrow(String projectname) {
    return staticToolDaoImpl.getallrows(projectname);
  }

  public Results getvalue(String projectname) {
    return staticToolDaoImpl.get(projectname);
  }

  public void updatevalue(String projectname, int value) {
    staticToolDaoImpl.update(projectname, value);
  }

  public int runcommand(String[] command, String binPath) {
    final ProcessBuilder pb = new ProcessBuilder(command);
    pb.directory(new File(Commands.projectdir));
    final Map<String, String> envMap = pb.environment();
    String path = envMap.get("Path");
    path += binPath;
    envMap.put("Path", path);
    Process process;
    try {
      process = pb.start();
    } catch (final IOException e) {
      return 1;
    }
    try {
      process.waitFor();
    } catch (final InterruptedException e) {
      return 1;
    }
    return process.exitValue();
  }



  public void createandextract(String command[]) throws IOException {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    final Process process = pb1.start();
    final BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String s = null;
    while ((s = out.readLine()) != null && s.charAt(0) != 'T') {
      ;
    }
    if (s == null) {
      System.out.println("0 ms");
    } else {
      final double time = Double.parseDouble((String) s.subSequence(6, s.length()));
      System.out.println(time * 1000 + "ms");
    }
  }

  public double parseCsvFile(String file) throws IOException {
    final FileReader filereader = new FileReader(file);
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

  public int parseTextFile(String str) throws IOException {
    String line;
    int count = 0;
    final FileReader fr =
        new FileReader("C:\\Users\\320065909\\Desktop\\security\\" + str + ".txt");
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

  public int parseXML(String projectname) {

    try {
      int noofissues = 0;
      final File fXmlFile = new File("C:\\Users\\" + projectname + ".xml");
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
            noofissues += violations.getLength();
          }
        }
      } else {
        System.out.println(" file is not created");
        System.out.println("The possible reasons are :\n");
        System.out.println("The user didn't install pmd in C: Drive.\n");
        System.out.println("The user may have installed the different version of pmd"
            + " other than we provided in README file.\n");
      }
      return noofissues;
    } catch (final Exception e) {
      return -1;

    }
  }
}


