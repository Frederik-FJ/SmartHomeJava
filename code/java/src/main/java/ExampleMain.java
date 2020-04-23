import FritzBox.FritzBoxDevice;
import FritzBox.FritzBoxInformations;
import logger.Logger;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;
import system.RunningSystem;

public class ExampleMain {
	
	
	public static void main(String[] args) {

		// Adding a Shutdown Thread which will be executed when the User exit the program (Strg+C)
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("");
				Logger.log("System", "Shutdown", "The SmartHome programm will shut down");

				// do things that must be done before shutdown

				Logger.log("System", "Shutdown", "The SmartHome program has been shut down");
			}
		});
		
		// Actions which should be executed at start
		Logger.log("System", "Boot", "The SmartHome programm boot...");
		
		Logger.log("Programm", "Boot", "Detected System: " + RunningSystem.RunningSystem);
		
		Logger.log("Program", "Boot", "The SmartHome program was booted");
		
		
		// The loop which will be executed all the Time
		while (true) {
			String output = "";
			FritzBox fb = new FritzBox("192.168.178.1", "password");
			for(FritzBoxInformations fbinfo : fb.getWLanState()) {
				String ssid, status;
				status = fbinfo.getState();
				ssid = fbinfo.getSSID();
				output += ssid + ": " + status + "\n";  
			}
			Logger.log("FritzBox", "Info", output);
			
			
			FritzBoxDevice[] devices = fb.getKnownDevices();
			FritzBoxDevice[] onlineDevs = fb.getOnlineDevices(devices);
			for(FritzBoxDevice d: onlineDevs) {
				output = d.getName() + "\t" + d.getIp() + "\t" + d.getMac();
				Logger.log("FritzBox", "Info", output);
			}
			
			Sonos son = new Sonos("Sonos IP");
			
			Logger.log("Sonos", "Info", "Volume: " + son.getVolume());
			
			
			// Wait 60s till execute the loop again
			try {
				Thread.sleep(60*1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}

}
