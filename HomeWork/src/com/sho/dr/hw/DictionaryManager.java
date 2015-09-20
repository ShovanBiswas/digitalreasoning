package com.sho.dr.hw;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

/* 
 * This is generalized class for handling dictionary related matters. This has 2 methods, which are:
 * buildDictionary: After reading reference file NER.txt, this method is called with the contents of the file as parameter.
 *                  This method, builds up an empty dictionary, called dictionaryContent, using the contents NER.txt as keys.
 *                  Provisionally, the values are kept empty. After building up the dictionary, the dictionary is returned.
 * recognizeProperNouns: This method is called with parameters a) fileContent (contents of nlp_data.txt in the form of String)
 *                       and b) dictionaryContent, which contains the dictionary built in buildDictionary() method.
 *                       This method tokenizes the contents of fileContent into paras and eventually into words. Then 2 things
 *                       are checked: i) existence each word in dictionaryContent and ii) existence of every contenated pair
 *                       of consecutive words. Both are checked in the dictionary. If they exist, then their locations
 *                       (paragraph number and word location in the para) are populated as values. Then the dictionary is
 *                       returned.
 */
public class DictionaryManager {

	Map<String, String> buildDictionary(String fileContent) throws IOException {

		Map<String, String> dictionaryContent = new HashMap<String, String>();

		for (String line : fileContent.split("\\n")) {
			if ((line.trim()).equals("")) {
				continue;
			}
			dictionaryContent.put(line, "");
		}
		return dictionaryContent;
	}

	Map<String, String> recognizeProperNouns(String fileContent, Map<String, String> dictionaryContent) throws IOException {

		int paraNumber = 0;
		for (String line : fileContent.split("\\n")) {
			if ((line.trim()).equals("")) {
				continue;
			}
			paraNumber++;

			String regex = ("\\W+");
			String[] words = line.split(regex, -1);
			
			int wordLocationInPara = 0;
			for (String word : words) {
				if ((word.trim()).equals("")) {
					continue;
				}
				
				
				if (wordLocationInPara < (words.length - 1)) {
					String consecutiveWordPair = words[wordLocationInPara] + " " + words[wordLocationInPara + 1];

					if (dictionaryContent.containsKey(consecutiveWordPair)) {
						String value = "para-" + ("" + paraNumber) + " " + "position-" + ("" + wordLocationInPara);
						dictionaryContent.put(consecutiveWordPair, value);
					}

					if (dictionaryContent.containsKey(words[wordLocationInPara])) {
						String value = "para-" + ("" + paraNumber) + " " + "position-" + ("" + wordLocationInPara);
						dictionaryContent.put(words[wordLocationInPara], value);
					}
				}
				wordLocationInPara++;
			}
		}
		
		return dictionaryContent;
	}
}
