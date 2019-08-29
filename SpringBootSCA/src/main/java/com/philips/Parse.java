/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class  Parse{
  public static int no_of_issues=0;
  public static ArrayList<ToolTwo> getToolTwoData(NodeList nList) {
    final ArrayList<ToolTwo> buglist = new ArrayList<>();
    for (int temp = 0; temp < nList.getLength(); temp++) {
      final Node nNode = nList.item(temp);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) nNode;
        final NodeList violations = eElement.getElementsByTagName("violation");
        no_of_issues+=violations.getLength();
        for (int count = 0; count < violations.getLength(); count++) {
          final ToolTwo f = new ToolTwo();
          final Node violationNode = violations.item(count);
          eElement = (Element) violationNode;
          f.setClassName(eElement.getAttribute("class"));
          f.setCategory(eElement.getAttribute("ruleset"));
          f.setrule(eElement.getAttribute("rule"));
          f.setLine(eElement.getAttribute("beginline"));
          f.setContent(eElement.getTextContent());
          buglist.add(f);
        }
      }
    }
    return buglist;
  }

  public static void parseXml(String projectName) {
    try {
      final File fXmlFile = new File(Commands.destinationPath + projectName +".xml");
      if (fXmlFile.exists()) {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final Document doc = dBuilder.parse(fXmlFile);
        final NodeList nList = doc.getElementsByTagName("file");
        final ArrayList<ToolTwo> buglist = getToolTwoData(nList);
      } else {
        System.out.println(" file is not created");
        System.out.println("The possible reasons are :\n");
        System.out.println("The user didn't install pmd in C: Drive.\n");
        System.out.println("The user may have installed the different version of pmd"
            + " other than we provided in README file.\n");
      }
    }

    catch (final Exception e) {
      e.printStackTrace();

    }

  }
}