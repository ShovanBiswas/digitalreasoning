package com.sho.dr.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* 
 * This is generalized class for handling files. There are 2 methods here, which are:
 * FileManager: Reads a file and returns the contents in the form of String.
 * writeXML: Builds the XML data structure for the first question and writes an XML file nlp_data_out.xml.
 *           Sentences have been separated with delimiters: . ? ! \n. The regex has been created to ignore period within
 *           quotes or paranthesis. Each senetence forms a node in the XML and each word is a child node under its parent
 *           sentence. For convinience, all the input and output files were kept under package com.sho.dr.hw.
 */
public class FileManager {

	String readFile(String fileName) throws IOException {

		BufferedReader bufferReader = null;

		try {
			bufferReader = new BufferedReader(new FileReader(fileName));
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferReader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = bufferReader.readLine();
				if (line != null)
					stringBuilder.append("\n");
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (bufferReader != null)
				bufferReader.close();
		}
	}

	void writeXML(String fileContent, String regex) throws IOException {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root element = NlpDataAanalysis
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("NlpDataAanalysis");
			doc.appendChild(rootElement);

			int i = 0;
			for (String sentence : fileContent.split(regex, -1)) {

				if ((sentence.trim()).equals("")) {
					continue;
				}

				i++;
				String str = Integer.toString(i);
				// System.out.println("> " + sentence.trim());

				// Sentence elements
				Element sentenceXML = doc.createElement("Sentence");
				rootElement.appendChild(sentenceXML);

				// Set attribute to senetence element
				Attr attr = doc.createAttribute("id");
				attr.setValue(str);
				sentenceXML.setAttributeNode(attr);
				sentenceXML.appendChild(doc.createTextNode(sentence));


				for (String word : sentence.split("(?<=\\W+)|(?=\\W+)")) {   // Alternative regex expression ("\\W+");
					if (!(word.trim()).equals("")) {
						// System.out.println(">>> " + word);

						// Word element
						Element wordXML = doc.createElement("word");
						wordXML.appendChild(doc.createTextNode(word));
						sentenceXML.appendChild(wordXML);
					}
				}
			}

			// Write output in nlp_data_out.xml
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(
							"C:\\Shovan\\personalM_P\\Misc_tech_staff\\SCRIPTS\\Java\\sts36\\workspaces\\projects\\CoreJava_Shovan\\src\\com\\sho\\dr\\hw\\nlp_data_out.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("nlp_data_out.xml saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
