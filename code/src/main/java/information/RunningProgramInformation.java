package information;

import java.util.Properties;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.2
 *
 */
public class RunningProgramInformation {

	public static final boolean Windows = new RunningProgramInformation().getRunningSystem().toLowerCase().contains("windows");
	public static final boolean Linux = new RunningProgramInformation().getRunningSystem().toLowerCase().equals("linux");
	public static final String RunningSystem = new RunningProgramInformation().getRunningSystem();
	
	public static final String runningPath = System.getProperty("java.class.path");
	
	/**
	 * 
	 * @since version 0.0.2
	 * @return the name of the os
	 */
	public String getRunningSystem() {
		
		Properties p = System.getProperties();
		String os = (String) p.get("os.name");
		return os;
	}

}
