package config;

import information.RunningProgramInformation;

public class VarsFromConfig {
	
	
	public static String cmdConfigPath = RunningProgramInformation.runningPath + "/conf/cmdConf.conf";
	
	static ConfigManager cmdConfig = new ConfigManager(cmdConfigPath);
	
	public static String pythonCmd = cmdConfig.read("pythonCommand");

}
