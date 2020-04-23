package config;

import java.io.File;

import ownLibaries.FileLibary.OwnFileReader;

public class ConfigManager {
	
	public ConfigManager(File file) {
		
	}
	
	public ConfigManager(String path) {
		
	}
	
	public String read(String var) {
		
		String[] fileOutput = OwnFileReader.readFile(VarsFromConfig.cmdConfigPath);
		
		return null;
	}

}
