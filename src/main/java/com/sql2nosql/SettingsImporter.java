package com.sql2nosql;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class SettingsImporter {

    public static HashMap<String, Object> importSettings(String path) throws Exception {
        HashMap<String, Object> output = new HashMap<String, Object>();

        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("setting");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                output.put(eElement.getElementsByTagName("key").item(0).getTextContent(), eElement.getElementsByTagName("value").item(0).getTextContent());
            }
        }
        return output;
    }
}
