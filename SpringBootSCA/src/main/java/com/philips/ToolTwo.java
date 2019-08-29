/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;

import org.springframework.stereotype.Component;

@Component
public class ToolTwo {
  String category;
  String className;
  String line;
  String rule;
  String content;

  public void setCategory(String category) {
    this.category = category;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getCategory() {
    return category;
  }

  public String getClassName() {
    return className;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public void setrule(String category) {
    this.rule = category;
  }

  public void setContent(String className) {
    this.content = className;
  }

  public String getrule() {
    return rule;
  }

  public String getContent() {
    return content;
  }
}
