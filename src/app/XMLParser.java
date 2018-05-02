package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	DocumentBuilder db;
	Document doc;

	public XMLParser() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
	}

	/**
	 * Parses a node for card data. First it checks if the node has an
	 * attribute "collectable" and then checks all child nodes
	 * for potential card data.
	 * 
	 * @param node
	 * @return Card object containing the parsed data
	 */
	private Card parseCard(Node node) {
		Card card = new Card();
		Node attr = node.getAttributes().getNamedItem("collectable");

		if (attr != null) {
			String s = attr.getTextContent();
			card.setCollectable(s.equals("true"));
		} else {
			card.setCollectable(false);
		}

		NodeList p = node.getChildNodes();

		for (int j = 1; j < p.getLength(); j++) {

			Node n1 = p.item(j - 1);
			NodeList nc = n1.getChildNodes();

			if (n1.getNodeType() == 1 && nc.getLength() > 0) {
				String content = nc.item(0).getTextContent();

				switch (n1.getNodeName()) {
				case "Id":
					card.setId(content);
					break;
				case "Name":
					card.setName(content);
					break;
				case "Text":
					card.setText(content);
					break;
				case "ImageIndex":
					card.setImageIndex(content);
					break;
				default:
					break;
				}
			}
		}
		return card;
	}
	
	/**
	 * Parses the xml file by running parseCard on each node.
	 * 
	 * @param file
	 * @return An ArrayList of all the cards
	 * @throws SAXException
	 * @throws IOException
	 */
	public ArrayList<Card> parseDocument(File file) throws SAXException, IOException {
		doc = db.parse(file);
		NodeList cards = doc.getElementsByTagName("Card");
		
		ArrayList<Card> result = new ArrayList<Card>();
		for (int i = 0; i < cards.getLength(); i++) {
			Node n = cards.item(i);
			result.add(parseCard(n));
		}
		return result;
	}
}
