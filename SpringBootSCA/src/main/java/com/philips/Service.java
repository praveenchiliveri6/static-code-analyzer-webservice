/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;

@org.springframework.stereotype.Service
public class Service {
  public void createfile(String command[]) throws IOException {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    pb1.start();
  }

  public void createandextract(String command[]) throws IOException {
    final ProcessBuilder pb1 = new ProcessBuilder(command);
    final Process process = pb1.start();
    final BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String s = null;
    while ((s = out.readLine()) != null) {
      if (s.length() != 0) {
        System.out.println(s);
      }
    }
  }


  public double parsefile(String file) throws IOException {
    final FileReader filereader = new FileReader(file);
    final CSVReader csvReader = new CSVReader(filereader);
    try {
      double codecoverage = 0.0;
      String[] nextRecord = csvReader.readNext();
      if ((nextRecord = csvReader.readNext()) != null) {
        final int instructionscovered = Integer.parseInt(nextRecord[4]);
        final int instructionsmissed = Integer.parseInt(nextRecord[3]);
        codecoverage = (double) instructionscovered / (instructionscovered + instructionsmissed);
      }
      return codecoverage;
    } catch (final Exception e) {
      e.printStackTrace();
    } finally {
      filereader.close();
      csvReader.close();
    }
    return 0.0;
  }

}
