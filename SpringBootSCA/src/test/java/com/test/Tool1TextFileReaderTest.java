/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.test;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.philips.Tool1;
import com.philips.Tool1TextFileReader;

public class Tool1TextFileReaderTest {

  @Autowired
  Tool1TextFileReader tool1TextFileReader;
  @Test
  public void putTextFileOutputtest()
  {
    final String s="com.philips.simpleApp,Customer2,toString,1,28,,<init>,1,18,,setPhone,1,7,,"
        + "setCaddr,1,7,,setCname,1,7,,setId,1,7,,<init>,1,6,,getPhone,1,5,,getCaddr"
        + ",1,5,,getCname,1,5,,getId,1,5,";
    final String s1[]=s.split(",");
    final Map<String,Integer> map=new HashMap<>();
    final String packagen=s1[0],classn=s1[1];
    int index = 2;
    while (index < s1.length) {
      map.put(s1[index], Integer.parseInt(s1[index + 1]));
      index = index + 4;
    }
    final Tool1 tool1=new Tool1(packagen,classn,map);
    Assert.assertEquals(tool1, tool1TextFileReader.putTextFileOutput(s1));
  }



}
