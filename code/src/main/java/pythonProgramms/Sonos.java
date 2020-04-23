package pythonProgramms;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import information.RunningProgramInformation;
import logger.Logger;

/**
 * 
 * @author FrederikFJ
 * 
 * @since version 0.0.1
 * @see <a href="http://docs.python-soco.com/en/latest/">SoCo</a> is used in the python program
 */
public class Sonos {
	
	String filePath = "pythonFiles/sonos/sonos.py";
	
	String ip;
	
	String pythonCmd = "python";
	
	// Home IP 192.168.180.49
	
	/**
	 * @author FrederikFJ
	 * @since 0.0.1
	 * 
	 * @param ip The IP address of the Sonos
	 */
	public Sonos(String ip) {
		this.ip = ip;
		
		if(RunningProgramInformation.Linux) {
			pythonCmd = "python3";
		}
	}
	
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param cmd Command in the python file which will be executed
	 * @param params Parameter for the Command
	 * @return returns the output from the python program
	 * 
	 * @see <a href="http://docs.python-soco.com/en/latest/">SoCo</a> is used in the python program
	 */
	private String run(String cmd, String params) {
		String result = "";
		
		try {
			Process p = Runtime.getRuntime().exec(pythonCmd + " " + filePath + " --ip " + this.ip + " --cmd " + cmd + " --param " + params);
			Scanner s = new Scanner(new InputStreamReader(p.getInputStream()));
			while(s.hasNextLine()) {
				String st = s.nextLine();
				result += st + "\n";
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError("JavaPython.Sonos", "Eine IO-Exception ist aufgetreten. " + filePath);
		}
		return result;
	}
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @return returns the volume from the Sonos
	 */
	public int getVolume() {
		String volume = this.run("getVolume", null);
		volume = volume.replaceAll("\n", "");
		volume = volume.replaceAll(" ", "");
		int vol = Integer.parseInt(volume);
		return vol;
	}
	
	/**
	 * Changes the volume from the Sonos to the new value
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param volume The new Volume
	 */
	public void setVolume(int volume) {
		
		String result = this.run("setVolume", ""+volume);
		String vorher = result.split("-->")[0];
		String nacher = result.split("-->")[1];

	}
	
	/**
	 * Set the volume from the Sonos dynamically
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param louder The new volume is the actual volume plus the Parameter louder
	 */
	public void louder(int louder) {
		String result = this.run("volumeLouder", ""+louder);
	}
	
	/**
	 * Set the volume from the Sonos dynamically
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param quieter The new Volume is the actual volume minus the Parameter quieter
	 */
	public void quiter(int quieter) {
		String result = this.run("volumeQuieter", ""+quieter);
	}
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @return returns the queue from the Sonos
	 */
	public String[] getQueue() {
		
		String result = this.run("getQueue", null);
		String[] resultArray = result.split(",");
		
		return resultArray;
	}
	
	/**
	 * @author FrederikFJ
	 * @since version 0.0.2
	 * 
	 * @return returns the title from the current playing track
	 */
	public String getCurrectTrack() {
		String result = this.run("getCurrentTrack", "title");
		return result;
	}

}
