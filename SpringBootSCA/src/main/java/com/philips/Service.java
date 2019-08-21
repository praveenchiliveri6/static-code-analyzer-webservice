/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

@org.springframework.stereotype.Service
public class Service {

  public void createfile() throws IOException, InterruptedException {
    final ProcessBuilder pb1 = new ProcessBuilder(Commands.javaagentcommand);
    pb1.start();
    Thread.sleep(5000);
    final ProcessBuilder pb2 = new ProcessBuilder(Commands.javacommand);
    pb2.start();
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
    }
    finally {
      filereader.close();
      csvReader.close();
    }
    return 0.0;
  }

}
