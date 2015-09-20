package com.sho.dr.hw;

import java.io.IOException;
import java.util.Map;

/* 
 * This is answer to question 2. It invokes FileManager.readFile() to read the contents of NER.txt into a String fileContent.
 * After that it invokes DictionaryManager.buildDictionary with parameters fileContent. buildDictionary() builds a dictionary
 * of all proper nouns in NER.txt and returns to this program.
 * 
 * Then it again invokes FileManager.readFile() to read nlp_data.txt and receives a String of its contents in fileContent
 * (used the same variable, doesn't hurt).
 * 
 * Finally, invokes DictionaryManager.recognizeProperNouns() with parameter fileContent & dictionaryContent.
 * recognizeProperNouns() determines the proper nouns in the text and populates value column of <key, value>-pair in the
 * dictionary, with the para and location.
 * 
 * Then it receives the dictionary and prints it on screen.
 */
public class QuestionSecondRecognizeProperNouns {

	public static void main(String[] args) throws IOException {

		FileManager fileManager = new FileManager();

		String fileName = "C:\\Shovan\\personalM_P\\Misc_tech_staff\\SCRIPTS\\Java\\sts36\\workspaces\\projects\\CoreJava_Shovan\\src\\com\\sho\\dr\\hw\\NER.txt";
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
/*				System.out.println("Original contents of NER.txt");
				System.out.println("=================================");

				System.out.println(fileContent + "\n");*/
			}
		}

		DictionaryManager dictionaryManager = new DictionaryManager();
		Map<String, String> dictionaryContent = dictionaryManager.buildDictionary(fileContent);
		
		
		
		fileName = "C:\\Shovan\\personalM_P\\Misc_tech_staff\\SCRIPTS\\Java\\sts36\\workspaces\\projects\\CoreJava_Shovan\\src\\com\\sho\\dr\\hw\\nlp_data.txt";
		fileContent = null;

		try {
			fileContent = fileManager.readFile(fileName);
		} catch (IOException e) {
			System.out.println("Unable to Open or read file: " + fileName + "\n");
			e.printStackTrace();
			System.out.println("\n" + "Exiting program...");
			System.exit(1);
		} finally {
			if (fileContent != null) {
/*				System.out.println("Original contents of NER.txt");
				System.out.println("=================================");

				System.out.println(fileContent + "\n");*/
			}
		}
		
		dictionaryContent = dictionaryManager.recognizeProperNouns(fileContent, dictionaryContent);
		System.out.println(dictionaryContent);
	}
}
