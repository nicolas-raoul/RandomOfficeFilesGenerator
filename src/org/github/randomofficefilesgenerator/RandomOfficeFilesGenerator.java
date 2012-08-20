package org.github.randomofficefilesgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * Generate files that are representative of what files could be found in a typical Japanese company.
 * The goal is especially to produce files with varied content, so that indexing tests are representative of real-use.
 * TODO: Use headless OpenOffice process to generate more representative file formats.
 * @author Nicolas Raoul
 */
public class RandomOfficeFilesGenerator {
	
	private Random random = new Random();
	
	/**
	 * Generate office files.
	 */
	public void generate(int numberOfFiles, int fileSize) {
		// We split files in different directories, so that one directory does not contain too many files.
		// See math at https://wiki.alfresco.com/wiki/Bulk_Importer#In-Place_Bulk_Import_.28Enterprise_Only.29
		int numberOfDirectories = numberOfFiles / 1000;
		
		// Generate hierarchy of directories.
		for (int i=0; i<numberOfDirectories; i++) {
			new File(Integer.toString(i)).mkdir();
		}
		// Write random files in hierarchy.
		for (int i=0; i<numberOfFiles; i++) {
			int filename = Math.abs(random.nextInt());
			String path = random.nextInt(numberOfDirectories) + "/" + filename + ".txt";
			writeRandomFile(path, fileSize);
		}
	}
	
	/**
	 * Generate one office file.
	 */
	private void writeRandomFile(String path, int fileSize) {
		try {
			// Open file for writing.
            BufferedWriter bw =
            	new BufferedWriter(new FileWriter(new File(path), true));
            // Write random characters.
            for (int i=0; i<fileSize; i++) {
            	bw.write(randomCharacter());
            }
            bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Give a random character to be used in a typical Japanese office document.
	 */
	private char randomCharacter() {
		int dice = random.nextInt(100);
		if (dice < 4) {
			return '\n'; // New line
		}
		if (dice < 28) {
			return randomCharacter('\u3000', '\u30ff'); // Half-width Romaji & Punctuation
		}
		if (dice < 52) {
			return randomCharacter('\u3000', '\u30ff'); // Full-width Kana & Punctuation
		}
		if (dice < 76) {
			return randomCharacter('\uff01', '\uff9f'); // Full-width romaji & Half-width katakana
		}
		return randomCharacter('\u4e00', '\u9faf'); // Common and rare kanji 
		// return randomCharacter('\u3400', '\u4dbf'); // Very rare kanji
	}

	/**
	 * Choose a random character in the given Unicode interval.
	 */
	private char randomCharacter(char unicodeStart, char UnicodeEnd) {
		return (char) (unicodeStart + new Random().nextInt(UnicodeEnd - unicodeStart));
	}

	/**
	 * Main.
	 */
	public static void main(String[] args) {
		int numberOfFiles = args.length > 0 ? Integer.decode(args[0]) : 10000;
		int fileSize = args.length > 1 ? Integer.decode(args[1]) : 10000;
		new RandomOfficeFilesGenerator().generate(numberOfFiles, fileSize);
	}
}