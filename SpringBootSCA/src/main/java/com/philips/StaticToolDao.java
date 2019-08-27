/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

public interface StaticToolDao {
  public void insert(String projectname);
  public boolean getallrows(String projectname);
  public Results get(String projectname);
  public void update(String projectname,int value);
}
