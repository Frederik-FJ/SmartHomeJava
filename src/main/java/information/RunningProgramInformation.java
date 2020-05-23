package information;

import java.util.Properties;

/**
 * Static variables with informations about the system and 
 * 
 * @author FrederikFJ
 * @since version 0.0.2
 *
 */
public class RunningProgramInformation {

	public static final boolean Windows = new RunningProgramInformation().getRunningSystem().toLowerCase().contains("windows");
	public static final boolean Linux = new RunningProgramInformation().getRunningSystem().toLowerCase().equals("linux");
	public static final String RunningSystem = new RunningProgramInformation().getRunningSystem();
	
	/**
	 * The path of the folder with the jar-file
	 * @since version 0.0.3
	 */
	public static final String runningPath = new RunningProgramInformation().getRunningPath();

	/**
	 * 
	 * @since version 0.0.2
	 * @return the name of the os
	 */
	public String getRunningSystem() {
		
		Properties p = System.getProperties();
		return (String) p.get("os.name");
	}

	public String getRunningPath(){
		String path = RunningProgramInformation.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath().substring(0, RunningProgramInformation.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath().lastIndexOf('/')+1);
		return Windows && path.startsWith("/")?path.substring(1):path;
	}

}
