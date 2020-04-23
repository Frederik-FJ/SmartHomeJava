package ownLibaries.FileLibary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import logger.Logger;

public class OwnFileReader {
	
	public static String[] readFile(File file) {
		
		try {
			Scanner s = new Scanner(file);
			String[] result = new String[getLines(file)];
			int i = 0;
			while(s.hasNextLine()) {
				result[i] = s.nextLine();
				i++;
			}
			return result;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File " + file.getAbsolutePath() + " couldn't be found");
			return null;
		}
	}
	
	public static String[] readFile(String path) {
		File file = new File(path);
		
		try {
			Scanner s = new Scanner(file);
			String[] result = new String[getLines(file)];
			int i = 0;
			while(s.hasNextLine()) {
				result[i] = s.nextLine();
				i++;
			}
			return result;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File " + file.getAbsolutePath() + " couldn't be found");
			return null;
		}
	}
	
	
	public static int getLines(File file) {
		try {
			Scanner s = new Scanner(file);
			int i = 0;
			while(s.hasNextLine()) {
				i++;
			}
			return i;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File couldn't be found");
			return 0;
		}
	}
	
	public static int getLines(String path) {
		File file = new File(path);
		try {
			Scanner s = new Scanner(file);
			int i = 0;
			while(s.hasNextLine()) {
				i++;
			}
			return i;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File couldn't be found");
			return 0;
		}
	}

}
