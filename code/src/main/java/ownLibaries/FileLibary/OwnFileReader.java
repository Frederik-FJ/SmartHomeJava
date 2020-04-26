package ownLibaries.FileLibary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import logger.Logger;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.3
 *
 */
public class OwnFileReader {
	
	/**
	 * Reads the content from a file
	 * @since version 0.0.3
	 * @param file file which should be read
	 * @return returns the Strings in an Array, for every line in the file one item in the array
	 */
	public static String[] readFile(File file) {
		
		
		try {
			
			String[] result = new String[getLines(file)];
			Scanner s = new Scanner(file);
			int i = 0;
			while(s.hasNextLine()) {
				result[i] = s.nextLine();
				i++;

				
			}
			s.close();
			return result;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File " + file.getAbsolutePath() + " couldn't be found");
			return null;
		}
	}
	
	/**
	 * @since version 0.0.3
	 * @param path Path of the file which should be read
	 * @return returns the Strings in an Array, for every line in the file one item in the array
	 */
	public static String[] readFile(String path) {
		File file = new File(path);
		
		try {
			
			String[] result = new String[getLines(file)];
			Scanner s = new Scanner(file);
			int i = 0;
			System.out.println(file.getAbsolutePath());
			while(s.hasNextLine()) {
				result[i] = s.nextLine();
				i++;
			}
			s.close();
			return result;
		} catch (FileNotFoundException e) {
			Logger.logError("FileReader", "File " + file.getAbsolutePath() + " couldn't be found");
			return null;
		}
	}
	
	/**
	 * Get the number of lines in a file
	 * @since version 0.0.3
	 * @param file 
	 * @return returns the number of lines in a file
	 */
	public static int getLines(File file) {

		int i = 0;
		Scanner s;
		try {
			s = new Scanner(file);
			while (s.hasNextLine()) {
				s.nextLine();
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return i;

	}
	
	/**
	 * Get the number of lines in a file
	 * @since version 0.0.3
	 * @param path the path of the file 
	 * @return returns the number of lines in a file
	 */
	public static int getLines(String path) {

		int i = 0;
		Scanner s;
		try {
			s = new Scanner(new File(path));
			while (s.hasNextLine()) {
				s.nextLine();
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return i;

	}
	

}
