package system;

import java.util.Properties;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.2
 *
 */
public class RunningSystem {

	public static final boolean Windows = new RunningSystem().getRunningSystem().toLowerCase().contains("windows");
	public static final boolean Linux = new RunningSystem().getRunningSystem().toLowerCase().equals("linux");
	public static final String RunningSystem = new RunningSystem().getRunningSystem();
	
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
