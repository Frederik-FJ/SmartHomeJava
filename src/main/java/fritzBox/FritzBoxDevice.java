package fritzBox;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.2
 *
 */
public class FritzBoxDevice {
	
	private String name;
	private String ip;
	private String mac;
	private String state;
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param name (host)name of the device
	 * @param ip intern IP of the device
	 * @param mac MAC address of the device 
	 * @param state state of the device
	 */
	public FritzBoxDevice(String name, String ip, String mac, String state) {
		this.name =name;
		this.ip = ip;
		this.mac = mac;
		this.state = state;
	}
	
	/**
	 * @since version 0.0.2
	 * @return returns a boolean if the device is online
	 */
	public boolean isOnline() {
		return state.equals("online");
	}
	
	/**
	 * @since version 0.0.2
	 * @return returns the (host)name of the Device
	 */
	public String getName() {
		return name;
	}
	/**
	 * @since version 0.0.2
	 * @return returns the IP of the device
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @since version 0.0.2
	 * @return returns the MAC of the device
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @since version 0.0.2
	 * @return returns the state from the device (online or offline)
	 */
	public String getState() {
		return state;
	}

}
