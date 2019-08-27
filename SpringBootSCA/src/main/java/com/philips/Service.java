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
import org.springframework.beans.factory.annotation.Autowired;
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

  public void runcommand(String[] command,String binPath) throws IOException, InterruptedException {
    final ProcessBuilder pb = new ProcessBuilder(command);
    pb.directory(new File(Commands.projectdir));
    final Map<String, String> envMap = pb.environment();
    String path = envMap.get("Path");
    path += binPath;
    envMap.put("Path", path);
    final Process process = pb.start();
    process.waitFor();
  }

  public int createfile(String command[]) throws IOException {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    final Process process = pb1.start();
    try {
      process.waitFor();
    } catch (final InterruptedException e) {
      e.printStackTrace();
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
    if(s==null) {
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
}
