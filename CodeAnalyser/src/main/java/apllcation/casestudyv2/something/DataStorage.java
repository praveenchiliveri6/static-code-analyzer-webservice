/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation.casestudyv2.something;

import org.springframework.stereotype.Component;

@Component
public class DataStorage {
  public DataStorage(int violations_pmd, int complexity_cyvis, int secVulernablities_simian,
      int coverage) {
    super();
    this.violations_pmd = violations_pmd;
    this.complexity_cyvis = complexity_cyvis;
    this.secVulernablities_simian = secVulernablities_simian;
    this.coverage = coverage;
  }

  public DataStorage() {}

  int violations_pmd;
  int complexity_cyvis;
  int secVulernablities_simian;
  int coverage;

  public int getViolations_pmd() {
    return violations_pmd;
  }

  public void setViolations_pmd(int violations_pmd) {
    this.violations_pmd = violations_pmd;
  }

  public int getComplexity_cyvis() {
    return complexity_cyvis;
  }

  public void setComplexity_cyvis(int complexity_cyvis) {
    this.complexity_cyvis = complexity_cyvis;
  }

  public int getSecVulernablities_simian() {
    return secVulernablities_simian;
  }

  public void setSecVulernablities_simian(int secVulernablities_simian) {
    this.secVulernablities_simian = secVulernablities_simian;
  }

  public int getCoverage() {
    return coverage;
  }

  public void setCoverage(int coverage) {
    this.coverage = coverage;
  }

  @Override
  public String toString() {
    return "DataStorage [violations_pmd=" + violations_pmd + ", complexity_cyvis="
        + complexity_cyvis + ", secVulernablities_simian=" + secVulernablities_simian
        + ", coverage=" + coverage + "]";
  }

}
