package config;

import java.io.File;

import ownLibaries.FileLibary.OwnFileReader;
import ownLibaries.FileLibary.OwnFileWriter;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.3
 *
 */
public class ConfigFile {
	
	File config;
	
	/**
	 * @since version 0.0.3
	 * @param path path of the ConfigFile
	 */
	public ConfigFile(String path) {
		config = new File(path);
	}
	
	/**
	 * @since version 0.0.3
	 * @param configFile file of the config file
	 */
	public ConfigFile(File configFile) {
		config = configFile;
	}
	
	/**
	 * @since version 0.0.3
	 * @return returns if the file exists
	 */
	public boolean exists() {
		return config.exists();
	}
	
	/**
	 * @since version 0.0.3
	 * @param var variable which should be proved
	 * @return returns if the variable exists
	 */
	public boolean existVar(String var) {
		String[] fileOutput = OwnFileReader.readFile(config);
		
		for(String line : fileOutput) {
			if(line == null) continue;
			line = line.replace("\t", "");
			if(line.startsWith(var)) {
				String a = line.split("=")[1];
				if(a != null) return true;
			}
		}
		return false;
	}
	
	/**
	 * Reads a value from a variable in the config
	 * @since version 0.0.3
	 * @param var variable from which the value should be returned
	 * @return returns the value of the variable, returns null if the variable doesn't exist
	 */
	
	public String read(String var) {
		String[] fileOutput = OwnFileReader.readFile(config);
		
		for(String line : fileOutput) {
			if(line == null) continue;
			line = line.replace("\t", "");
			if(line.startsWith(var)) {
				try {
					String a = line.split("=")[1];
					if(a != null) return a;
					return "";
				}catch (ArrayIndexOutOfBoundsException e){
					return "";
				}

			}
		}
		return null;
	}
	
	/**
	 * Creates the Config-File with values
	 * 
	 * @since version 0.0.3
	 * @param varsAndValues an two-dimensional array with the variables in the first array and the values in the second array
	 * @return returns an boolean if the method worked correctly
	 */
	public boolean create(String[][] varsAndValues) {
		if (!OwnFileWriter.createFile(config)) return false;
		for(int i = 0;i < varsAndValues[0].length;i++) {
			
			String var = varsAndValues[0][i];
			String value = varsAndValues[1][i];
			
			
			if(var.equals("#")) {
				OwnFileWriter.add(config, var + value);
				continue;
			}
			OwnFileWriter.add(config, var + "=" + value);
		}
		return true;
	}
	
	/**
	 * Adds a new variable with value to the config
	 * @since version 0.0.3
	 * @param var new variable
	 * @param value value of the variable
	 * @return returns if the method worked correctly
	 */
	public boolean add(String var, String value) {
		if(!config.exists()) return false;
		if(var.equals("#")) {
			OwnFileWriter.add(config, var + value);
			return true;
		}
		OwnFileWriter.add(config, var + "=" + value);
		return true;
	}
	
}
