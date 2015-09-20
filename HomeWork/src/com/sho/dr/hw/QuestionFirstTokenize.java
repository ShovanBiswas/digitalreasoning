package com.sho.dr.hw;

import java.io.IOException;

/* 
 * This is answer to question 1. It invokes FileManager.readFile() to read the contents of nlp_data.txt into a String fileContent.
 * After that it calls FileManager.writeXML(), with parameters fileContent and an appropriate regex expression.
 * Rest of the processing happens in FileManager, which is commented.
 * The final goal is to generate an XML file, namely nlp_data_out.xml, where each sentence is a node and the words of the
 * children of the sentences. Please open nlp_data_out.xml with Chrome or Firefox, it'll be clear.
 */
public class QuestionFirstTokenize {

	public static void main(String[] args) throws IOException {

		FileManager fileManager = new FileManager();
		String fileName = "C:\\Shovan\\personalM_P\\Misc_tech_staff\\SCRIPTS\\Java\\sts36\\workspaces\\projects\\CoreJava_Shovan\\src\\com\\sho\\dr\\hw\\nlp_data.txt";
		String fileContent = null;

		try {
			fileContent = fileManager.readFile(fileName);
		} catch (IOException e) {
			System.out.println("Unable to Open or read file: " + fileName + "\n");
			e.printStackTrace();
			System.out.println("\n" + "Exiting program...");
			System.exit(1);
		} finally {
			if (fileContent != null) {
				System.out.println("Original contents of nlp_data.txt");
				System.out.println("=================================");

				System.out.println(fileContent + "\n");
			}
		}
		
		String regex = "((?<=\\.)|(?<=\\?)|(?<=\\!)|(?<=\n))(?=([^\"^\'^(^)]*[\"\'()][^\"^\'^(^)]*[\"\'()])*[^\"^\'^(^)]*$)";
		fileManager.writeXML(fileContent, regex);
	}
}
