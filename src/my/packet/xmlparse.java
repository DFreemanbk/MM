package my.packet;

import java.awt.Polygon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlparse {
	private Document doc = null;
	private FileInputStream fis = null;
	public void startXMLParse() {
		try {
			fis = new FileInputStream("polygons.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fis);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element rootElement = doc.getDocumentElement();
		NodeList polygonsNodeList = rootElement.getElementsByTagName("polygon");
		NodeList pointsNodeList  = null;
		Element polygonElement = null;
		Element pointElement = null;
		Element tagx = null;
		Element tagy = null;
		for (int i = 0; i < polygonsNodeList.getLength(); i++) {
			polygonElement = (Element) polygonsNodeList.item(i);
			pointsNodeList = polygonElement.getElementsByTagName("point");
			Polygon tempPolygon = new Polygon();
			//System.out.println("Координаты полигона №" + i);
			for(int j = 0; j < pointsNodeList.getLength(); j++)
			{
				pointElement = (Element) pointsNodeList.item(j);
				tagx = getFirstElementByTagName(pointElement, "x");
				tagy = getFirstElementByTagName(pointElement, "y");
				tempPolygon.addPoint(Integer.parseInt(getNodeText(tagx)), Integer.parseInt(getNodeText(tagy)));
				/*System.out.print("x: " + getNodeText(tagx) + " ");
				System.out.println("y: " + getNodeText(tagy));*/
			}
			Global.plist.add(i,tempPolygon);
			tempPolygon = null;
		}
	}

	private Element getFirstElementByTagName(Element element, String tagName) {
		Element e = null;

		NodeList nl = element.getElementsByTagName(tagName);
		e = (Element) nl.item(0);

		return e;
	}

	private String getNodeText(Node node) {
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
				CDATASection section = (CDATASection) child;
				return section.getData();
			} else if (child.getNodeType() == Node.TEXT_NODE) {
				return child.getNodeValue();
			}
		}
		return "";
	}
}
