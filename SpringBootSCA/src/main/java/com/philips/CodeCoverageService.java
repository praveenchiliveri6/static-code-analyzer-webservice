/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.io.FileReader;
import java.io.IOException;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
@Service
public class CodeCoverageService {
  public int parseCsvFile(String file) throws IOException {
    FileReader filereader;
    filereader = new FileReader(file);
    final CSVReader csvReader = new CSVReader(filereader);
    double codecoverage = 0.0;
    String[] nextRecord = csvReader.readNext();
    if ((nextRecord = csvReader.readNext()) != null) {
      final int instructionscovered = Integer.parseInt(nextRecord[4]);
      final int instructionsmissed = Integer.parseInt(nextRecord[3]);
      codecoverage = (double) instructionscovered / (instructionscovered + instructionsmissed);
    }
    csvReader.close();
    filereader.close();
    return (int)codecoverage * 100;
  }
}
