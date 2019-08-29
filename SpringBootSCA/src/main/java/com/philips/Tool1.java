/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Tool1 {
  String packagename;
  String classname;
  Map<String,Integer> functions;
  public String getPackagename() {
    return packagename;
  }
  public void setPackagename(String packagename) {
    this.packagename = packagename;
  }
  public String getClassname() {
    return classname;
  }
  public void setClassname(String classname) {
    this.classname = classname;
  }
  public Map<String, Integer> getFunctions() {
    return functions;
  }
  public void setFunctions(Map<String, Integer> functions) {
    this.functions = functions;
  }
  @Override
  public String toString() {
    return "Tool1 [packagename=" + packagename + ", classname=" + classname + ", functions=" + functions + "]";
  }


}
