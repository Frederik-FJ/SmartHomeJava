package pythonProgramms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import FritzBox.FritzBoxDevice;
import FritzBox.FritzBoxInformations;
import information.Information;
import information.RunningProgramInformation;
import logger.Logger;
import ownLibaries.FileLibary.OwnFileWriter;

/**
 * 
 * @author FrederikFJ
 * @version version 0.0.1
 *
 * @see <a href="https://fritzconnection.readthedocs.org">fritzconnection</a> is used in the Python programm
 */
public class FritzBox {
	
	
	String jarPath = "/pythonFiles/fritzbox/";
	String path = RunningProgramInformation.runningPath + jarPath.substring(1);
	
	
	String statePyFilePath = path + "fritzbox.py";
	String devicePyFilePath = path + "fritz_device.py";
	File statePyFile = new File(statePyFilePath);
	File devicePyFile = new File(devicePyFilePath);
	String ip = Information.FRITZBOX_IP;
	String pw = Information.FRITZBOX_PW;

	public static List<FritzBoxDevice> storedDevices;
	public static List<FritzBoxInformations> storedState;
	
	public static String pythonCmd = "python";
	
	/**
	 * @since version 0.0.3
	 * @param pythonCmd the pythonCmd which should be used (for example python3,python)
	 */
	public FritzBox(String pythonCmd) {
		this.pythonCmd = pythonCmd;
		checkFiles();
	}
	
	
	/**
	 *
	 * @since version 0.0.1
	 */
	public FritzBox() {
		checkFiles();
	}
	
	/**
	 * Check if the python files exists
	 * @since version 0.0.3
	 */
	private void checkFiles() {
		if(!statePyFile.exists()) createFile(statePyFile, jarPath + "fritzbox.py");
		if(!devicePyFile.exists()) createFile(devicePyFile, jarPath + "fritz_device.py");
	}
	
	/**
	 * Creates the python files outside of the jar file
	 * @since version 0.0.3
	 * @param file File which should be created
	 * @param path Path of the file in the jar which should be copied
	 */
	private void createFile(File file, String path) {
		InputStream is = getClass().getResourceAsStream(path);
		int i;
		String s = "";
		try {
			while((i = is.read())!=-1){
				s += (char) i;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		OwnFileWriter.createFile(file);
		OwnFileWriter.add(file, s);
	}
	

	
	// State
	
	/**
	 * This is the method to get the states of the wifi
	 * 
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
			
			
			// Muss ver?ndert werden, wenn Service,Action oder Keys ver?ndert werden
			FritzBoxInformations fbinfo = new FritzBoxInformations(this.ip);
			try {
				fbinfo.setStateInformation(infoArr[0], infoArr[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				Logger.logError("FritzBox", "The python file gave an invalid output back to the Java programm");
			}
			
			
			infoArray[i] = fbinfo;
		}
		storedState = Arrays.asList(infoArray);
		return infoArray;
	}


	
	
	
	// Device 
	
	/**
	 * @since version 0.0.2
	 * 
	 * @return returns the known Devices from the FritzBox
	 */
	public FritzBoxDevice[] getKnownDevices() {
				
		String pyOutput = this.runDevice();
		String[] pyOutputArray = pyOutput.split("\n");
		FritzBoxDevice[] devices = new FritzBoxDevice[pyOutputArray.length];
		for(int i = 0; i < pyOutputArray.length; i++) {
			String line = pyOutputArray[i];
			String[] infos = line.split(",");
			
			FritzBoxDevice device = new FritzBoxDevice(infos[0], infos[1], infos[2], infos[3]);
			devices[i] = device;
		}
		storedDevices = Arrays.asList(devices);
		return devices;
	}
	
	
	/**
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
		String cmd = pythonCmd + " " + statePyFilePath + " --service " + service + " --action " + action + " --ip "
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
					"There is an IO-Exception occured while trying to run this cmd: " + statePyFilePath);
		}
		
		return result;
	}
	
	
	/**
	 * This method will be executed if you get informations about the hosts
	 * 
	 * @since version 0.0.2
	 * 
	 * @return returns the output from the python file
	 * 
	 * @see <a href="https://fritzconnection.readthedocs.io/en/1.2.1/sources/library.html#fritzhosts">fritzconnection</a> is used in the Python programm
	 */
	private String runDevice() {
		String result = "";
		String cmd = pythonCmd + " " + devicePyFilePath + " --ip " + this.ip + " --password " + this.pw;
		try {
			ProcessBuilder pb = new ProcessBuilder(pythonCmd, devicePyFilePath, "--ip", this.ip, "--password", this.pw);
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
