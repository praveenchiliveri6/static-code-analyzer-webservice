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
import java.util.List;
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

  public void searchFilesInDirectory(final String pattern, final File folder, List<String> result) {
    for (final File file : folder.listFiles()) {
      if (file.isDirectory()) {
        searchFilesInDirectory(pattern, file, result);
      }
      if (file.isFile() && file.getName().matches(pattern)) {
        result.add(file.getAbsolutePath());
      }
    }
  }

  public List<String> getAllClasses(List<String> allTests) {
    final List<String> results=new ArrayList<>();
    for(final String testFile : allTests) {
      final String[] paths=testFile.split("\\\\");
      boolean flag=false;
      String pathvar="";
      for(int i=0;i<paths.length-1;i++) {
        if(paths[i].equals("test-classes")) {
          flag=true;
          i+=1;
        }
        if(flag) {
          pathvar+=paths[i]+".";
        }
      }
      paths[paths.length-1] = paths[paths.length-1].replace(".class", "");
      pathvar+=paths[paths.length-1];
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
    final double time = Double.parseDouble((String) s.subSequence(6, s.length()));
    return time * 1000;
  }

  public double parseCsvFile(String file) throws IOException{
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
    }

    filereader.close();
    csvReader.close();


    return codecoverage * 100;
  }

  public int parseTextFile(String projectname) throws IOException {
    String line;
    int count = 0;
    final FileReader fr =
        new FileReader(Commands.currentdir+"\\security\\" + projectname + ".txt");

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
