package logica;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class DataBase {
	
	public DataBase(){
	}
	
	public ArrayList<Nota> loadNotes(){
		ArrayList<Nota> notas = new ArrayList<Nota>();
		
		File fXmlFile = new File("notes.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("note");
			
			for (int i = 0; i < nList.getLength(); i++){
				Node node = nList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE){
					Element e = (Element) node;
					
					Nota n = new Nota(e.getElementsByTagName("name").item(0).getTextContent(),
							e.getElementsByTagName("content").item(0).getTextContent(),
							e.getAttribute("id"));
					
					notas.add(n);
				}
			}	
		} catch (Exception e) {
			createNewXmlDocument();
		}
		
		return notas;
	}
	
	public boolean saveNote(Nota n){
		File fXmlFile = new File("notes.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			
			Node root = doc.getFirstChild();
			
			Element newNote = doc.createElement("note");
			Attr idAttr = doc.createAttribute("id");
			idAttr.setTextContent(n.getId());
			
			Element nameElem = doc.createElement("name");
			nameElem.setTextContent(n.getNombre());
			
			Element contentElem = doc.createElement("content");
			contentElem.setTextContent(n.getContenido());
			
			newNote.setAttributeNode(idAttr);
			newNote.appendChild(nameElem);
			newNote.appendChild(contentElem);
			
			root.appendChild(newNote);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("notes.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			createNewXmlDocument();
			return false;
		}
		
		return true;
	}
	
	public boolean deleteNote(String id){
		File fXmlFile = new File("notes.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			
			Node root = doc.getFirstChild();
			
			NodeList nodeList = doc.getElementsByTagName("note");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				NamedNodeMap attrs = n.getAttributes();
				if (id.equals(attrs.item(0).getTextContent())){
					root.removeChild(n);
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("notes.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean updateNote(Nota newNote){
		
		if (deleteNote(newNote.getId()))
			return saveNote(newNote);
		
		
		return false;
	}
	
	private void createNewXmlDocument(){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("notes");
			doc.appendChild(rootElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("notes.xml"));
			transformer.transform(source, result);
			
		} catch (Exception e1) {
			
		}
	}
}
