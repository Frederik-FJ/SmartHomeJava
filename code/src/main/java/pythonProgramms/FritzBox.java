package pythonProgramms;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import FritzBox.FritzBoxDevice;
import FritzBox.FritzBoxInformations;
import config.VarsFromConfig;
import information.RunningProgramInformation;
import logger.Logger;

/**
 * 
 * @author FrederikFJ
 * @version version 0.0.1
 *
 * @see <a href="https://fritzconnection.readthedocs.org">fritzconnection</a> is used in the Python programm
 */
public class FritzBox {
	
	String path = RunningProgramInformation.runningPath + "/pythonFiles/fritzbox/";
	
	String statePyFile = path + "fritzbox.py";
	String devicePyFile = path + "fritz_device.py";
	String ip;
	String pw;
	
	String pythonCmd = "python";
	
	/**
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param ip The IP address from the FritzBox
	 * @param pw The password to Login into the FritzBox 
	 */
	public FritzBox(String ip, String pw) {
		this.ip = ip;
		this.pw = pw;
		
		if(RunningProgramInformation.Linux) {
			pythonCmd = "python3";
		}
	}
	

	
	// State
	
	/**
	 * This is the method to get the states of the wifi
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @return returns the states of the wifi
	 */
	public FritzBoxInformations[] getWLanState() {
		
		String service =  "WLANConfiguration";
		String action = "GetInfo";
		String keys = "NewSSID,NewStatus";
		
		String result =  runState(service, action, keys);
		String[] resArray = result.split("\n");
		FritzBoxInformations[] infoArray = new FritzBoxInformations[resArray.length];
		
		for(int i = 0; i < resArray.length; i++) {
			String einzelInfo = resArray[i];
			String[] infoArr = einzelInfo.split(":");
			
			
			// Muss verändert werden, wenn Service,Action oder Keys verändert werden
			FritzBoxInformations fbinfo = new FritzBoxInformations(this.ip);
			fbinfo.setStateInformation(infoArr[0], infoArr[1]);
			
			infoArray[i] = fbinfo;
		}
		
		return infoArray;
	}


	
	
	
	// Device 
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @return returns the known Devices from the FritzBox
	 */
	public FritzBoxDevice[] getKnownDevices() {
		
		Logger.logConsole("FritzBox", "Info", new File(devicePyFile).getAbsolutePath());
		
		String pyOutput = this.runDevice();
		String[] pyOutputArray = pyOutput.split("\n");
		FritzBoxDevice[] devices = new FritzBoxDevice[pyOutputArray.length];
		for(int i = 0; i < pyOutputArray.length; i++) {
			String line = pyOutputArray[i];
			String[] infos = line.split(",");
			
			FritzBoxDevice device = new FritzBoxDevice(infos[0], infos[1], infos[2], infos[3]);
			devices[i] = device;
		}
		
		return devices;
	}
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param devices An Array of devices
	 * @return The online devices 
	 */
	public FritzBoxDevice[] getOnlineDevices(FritzBoxDevice[] devices) {
		int i= 0;
		for(FritzBoxDevice d: devices) {
			if(d.isOnline()) i++;
		}
		FritzBoxDevice[] onlineDevices = new FritzBoxDevice[i];
		i = 0;
		for(FritzBoxDevice d : devices) {
			if(d.isOnline()) {
				onlineDevices[i] = d;
				i++;
			}
		}
		return onlineDevices;
	}
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param devices An array of devices
	 * @param ip IP from the device, which should be returned
	 * @return The device with the given IP in the given Array of devices <br><b>If no Device was found the method return null</b>
	 * 
	 */
	public FritzBoxDevice getDeviceStateByIP(FritzBoxDevice[] devices, String ip) {
		for(FritzBoxDevice d: devices) {
			if(d.getIp().equals(ip)) {
				return d;
			}
		}
		return null;
	}
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param devices An array of devices
	 * @param mac MAC address from the device, which should be returned
	 * @return The device with the given MAC in the given Array of devices <br><b>If no Device was found the method return null</b>
	 * 
	 */
	public FritzBoxDevice getDeviceByMac(FritzBoxDevice[] devices, String mac) {
		for(FritzBoxDevice d: devices) {
			if(d.getMac().equals(mac)) {
				return d;
			}
		}
		return null;
	}
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param devices An array of devices
	 * @param name (host)name from the device, which should be returned
	 * @return The device with the given name in the given Array of devices<br>If two devices have the same (host)name, the first one in the list will be returned <br><b>If no Device was found the method return null</b>
	 * 
	 */
	public FritzBoxDevice getDeviceByName(FritzBoxDevice[] devices, String name) {
		for(FritzBoxDevice d: devices) {
			if(d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}
	
	// Run methods

	/**
	 * The python program to get the State will be called over this method. This method was called run in version 0.0.1
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @param service The Service which is used to contact the FritzBox
	 * @param action The action which will be used with the service
	 * @param keys The values which will be returned from the python program
	 * @return returns the output from the python program
	 * 
	 * @see <a href="https://fritzconnection.readthedocs.org">fritzconnection</a> is used in the Python programm
	 */
	private String runState(String service, String action, String keys) {
		String result = "";
		String cmd = pythonCmd + " " + statePyFile + " --service " + service + " --action " + action + " --ip "
				+ this.ip + " --password " + this.pw + " --keys " + keys;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			Scanner s = new Scanner(new InputStreamReader(p.getInputStream()));
			while (s.hasNextLine()) {
				String st = s.nextLine();
				result += st + "\n";
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError("JavaPython.FritzBox",
					"There is an IO-Exception occured while trying to run this cmd: " + statePyFile);
		}
		
		return result;
	}
	
	
	/**
	 * This method will be executed if you get informations about the hosts
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @return returns the output from the python file
	 * 
	 * @see <a href="https://fritzconnection.readthedocs.io/en/1.2.1/sources/library.html#fritzhosts">fritzconnection</a> is used in the Python programm
	 */
	private String runDevice() {
		String result = "";
		String cmd = pythonCmd + " " + devicePyFile + " --ip " + this.ip + " --password " + this.pw;
		try {
			ProcessBuilder pb = new ProcessBuilder(pythonCmd, devicePyFile, "--ip", this.ip, "--password", this.pw);
			Process p = pb.start();
			Scanner s = new Scanner(new InputStreamReader(p.getInputStream()));
			while (s.hasNextLine()) {
				String st = s.nextLine();
				result += st + "\n";
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError("JavaPython.FritzBox",
					"There is an IO-Exception occured while trying to run this cmd: " + cmd);
		}
		return result;
	}
}
