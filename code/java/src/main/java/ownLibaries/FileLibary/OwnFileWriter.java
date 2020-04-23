package ownLibaries.FileLibary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import logger.Logger;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.2
 *
 */
public class OwnFileWriter {

	/**
	 * Creates a file
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param f File to create
	 * @return returns if the method worked correctly
	 */
	public static boolean createFile(File f) {
		if(f.exists()) {
			Logger.logError("FileWriter", "The file already exists");
			return false;
		}else {
			try {
				f.createNewFile();
				Logger.log("FileWriter", "Info", "The file " + f.getAbsolutePath() + " has been created");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}	
	}
	
	
	/**
	 * Renames a file
	 * 
	 * @author FredeikFJ
	 * @since version 0.0.1
	 * 
	 * @param f File to rename
	 * @param newName renamed File
	 * @return returns if the method worked correctly
	 */
	public static boolean renameFile(File f, File newName) {
		if(newName.exists()) {
			try {
				f.renameTo(newName);
				Logger.log("FileWriter", "Info", "The file has been renamed");
				return true;
			}catch (SecurityException e) {
				e.printStackTrace();
				Logger.logError("FileWriter", "The program doesn't have the permission to write in that file");
				return false;
			}
			
		}else {
			Logger.logError("FileWriter", "The file with the new name already exists");
			return false;
		}
	}
	
	
	/**
	 * Add a line to a file
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * 
	 * @param f File in which the method should write
	 * @param input The string which should be written in the File
	 * @return returns if the method worked correctly
	 */
	public static boolean addLine(File f, String input) {
		if(f.exists()) {
			
			String n = System.getProperty("line.separator");
			try {
				FileWriter fw = new FileWriter(f, true);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(input);
				bw.write(n);
				bw.close();
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
				Logger.logConsole("FileWriter", "Error", "Couldn't write in the file " + f.getAbsolutePath()+ " because of an IOException");
				return false;
			}catch (NullPointerException e) {
				e.printStackTrace();
				Logger.logError("FileWriter", "There is no possibility to write null into a file");
			}
			
			return true;
		}else {
			Logger.logConsole("FileWriter", "Error", "The file couldn't be found");
			
			if(!f.getName().equals("error.log")) {
				Logger.logError("FileWriter", "The file " + f.getAbsolutePath() + " couldn't be found");
			}
			return false;
		}
	}
	
	
}