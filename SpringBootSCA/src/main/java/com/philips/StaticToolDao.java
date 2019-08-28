/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

public interface StaticToolDao {
  public void insert(String projectname);
  public boolean getallrows(String projectname);
  public Results get(String projectname);
  public void updatecoverage(String projectname,int value);
  public void updatesecurity(String projectname,int value);
  public void updatecomplexity(String projectname,int value);
  public void updatewarnings(String projectname,int value);
}
