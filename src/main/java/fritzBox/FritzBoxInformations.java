package fritzBox;

/**
 * A Class to store values from the FritzBox
 * 
 * @author FrederikFJ
 * @version version 0.0.1
 * @see pythonProgramms.FritzBox
 *
 */
public class FritzBoxInformations {
	
	// Zur allgemeinen Identifizierung der FritzBox
	String ip;
	
	// getWlanStatus in pythonProgramms/FritzBox
	private String ssid = null;
	private String state = null;
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param ip IP address from the FritzBox
	 */
	public FritzBoxInformations(String ip) {
		this.ip = ip;
	}
	
	/**
	 * Method to store the status information
	 * 
	 * @author FrederikFj
	 * @since version 0.0.1
	 * 
	 * @param ssid The SSID
	 * @param state The status
	 */
	public void setStateInformation(String ssid, String state) {
		this.ssid = ssid;
		this.state = state;
	}
	
	
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @return returns the stored SSID
	 */
	public String getSSID() {
		return this.ssid;
	}
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @return returns the stored state
	 */
	public String getState() {
		return this.state;
	}

}
