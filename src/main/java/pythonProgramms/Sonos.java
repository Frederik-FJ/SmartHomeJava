package pythonProgramms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import information.Information;
import information.RunningProgramInformation;
import logger.Logger;
import ownLibaries.FileLibary.OwnFileWriter;

/**
 * 
 * @author FrederikFJ
 * 
 * @since version 0.0.1
 * @see <a href="http://docs.python-soco.com/en/latest/">SoCo</a> is used in the python program
 */
public class Sonos {

	
	String jarPath = "/pythonFiles/sonos/sonos.py";
	String filePath = RunningProgramInformation.runningPath + jarPath.substring(1);
	File f = new File(filePath);
	
	String ip = Information.SONOS_IP;
	
	
	public static String pythonCmd = "python";
	
	
	
	
	
	
	/**
	 * @since 0.0.1
	 *
	 */
	public Sonos() {
		checkFiles();	
	}
	
	/**
	 * @since version 0.0.3
	 * @param pythonCmd the pythonCmd which should be used (for example python3,python)
	 */
	public Sonos(String pythonCmd) {
		Sonos.pythonCmd = pythonCmd;
		checkFiles();
	}
	
	/**
	 * Checks if the files exists
	 * @since version 0.0.3
	 */
	private void checkFiles() {
		if(!f.exists()) createFile(f, jarPath);
	}
	
	/**
	 * Creates the files
	 * @since version 0.0.3
	 * @param file File which should be created
	 * @param path path in the jar from the file which should be copied
	 */
	private void createFile(File file, String path) {
		InputStream is = getClass().getResourceAsStream(path);
		int i;
		StringBuilder s = new StringBuilder();
		try {
			while((i = is.read())!=-1){
				s.append((char) i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		OwnFileWriter.createFile(file);
		OwnFileWriter.add(file, s.toString());
	}
	
	
	/**
	 * @since version 0.0.1
	 * 
	 * @param cmd Command in the python file which will be executed
	 * @param params Parameter for the Command
	 * @return returns the output from the python program
	 * 
	 * @see <a href="http://docs.python-soco.com/en/latest/">SoCo</a> is used in the python program
	 */
	private String run(String cmd, String params) {
		StringBuilder result = new StringBuilder();
		String execute = pythonCmd + " " + filePath + " --ip " + this.ip + " --cmd " + cmd + " --param " + params;
		try {
			Process p = Runtime.getRuntime().exec(execute);
			Scanner s = new Scanner(new InputStreamReader(p.getInputStream()));
			while(s.hasNextLine()) {
				String st = s.nextLine();
				result.append(st).append("\n");
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError("JavaPython.Sonos", "Eine IO-Exception ist aufgetreten. " + filePath);
		}
		return result.toString();
	}
	
	/**
	 * @since version 0.0.1
	 * 
	 * @return returns the volume from the Sonos
	 */
	public int getVolume() {
		String volume = this.run("getVolume", null);
		volume = volume.replaceAll("\n", "");
		volume = volume.replaceAll(" ", "");
		return Integer.parseInt(volume);
	}
	
	/**
	 * Changes the volume from the Sonos to the new value
	 * 
	 * @since version 0.0.1
	 * @param volume The new Volume
	 */
	public void setVolume(int volume) {

		/*String result = */this.run("setVolume", "" + volume);
		/*String vorher = result.split("-->")[0];
		String nacher = result.split("-->")[1];*/

	}
	
	/**
	 * Set the volume from the Sonos dynamically
	 * 
	 * @since version 0.0.1
	 * 
	 * @param louder The new volume is the actual volume plus the Parameter louder
	 */
	public void louder(int louder) {
		/*String result =*/ this.run("volumeLouder", ""+louder);
	}
	
	/**
	 * Set the volume from the Sonos dynamically
	 * @since version 0.0.1
	 * 
	 * @param quieter The new Volume is the actual volume minus the Parameter quieter
	 */
	public void quieter(int quieter) {
		/*String result =*/ this.run("volumeQuieter", ""+quieter);
	}
	
	/**
	 * @since version 0.0.2
	 * 
	 * @return returns the queue from the Sonos
	 */
	public String[] getQueue() {
		
		String result = this.run("getQueue", null);

		return result.split(",");
	}
	
	/**
	 * @since version 0.0.2
	 * 
	 * @return returns the title from the current playing track
	 */
	public String getCurrectTrack() {
		return this.run("getCurrentTrack", "title");
	}

}
