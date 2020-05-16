
import FritzBox.FritzBoxDevice;
import FritzBox.FritzBoxInformations;
import config.ConfigFile;
import information.RunningProgramInformation;
import logger.Logger;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;

public class ExampleMain {
	
	
	
	public static void main(String[] args) {
		
		// Adding a Thread for the Webserver
		Thread web = new Thread(new Runnable() {
			
			@Override
			public void run() {
				new ReceiveSocket(1904).startServer();
			}
		});

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
		
		
		// Actions which should be executed once at the start of the program
		Logger.log("System", "Boot", "The SmartHome programm boot...");
		
		Logger.log("Programm", "Boot", "Detected System: " + RunningProgramInformation.RunningSystem);
		
		//starting the web server
		web.start();
		
		
		// Config paths
		String cmdsConfPath = RunningProgramInformation.runningPath + "config/commands.conf";
		
		// Create config objects
		ConfigFile cmdsConf = new ConfigFile(cmdsConfPath);
		
		//load config
		if(!cmdsConf.exists()) {
			cmdsConf.create(new String[][] {{"#","pythonCmd"},{"cmds that the system is using", "python3"}});
		}
		String pythonCmd = cmdsConf.read("pythonCmd");

		
		
		
		
		Logger.log("Program", "Boot", "The SmartHome program was booted");
		
		
		// The loop which will be executed all the Time
		while (true) {
			
			String output = "";
			FritzBox fb = new FritzBox(pythonCmd);
			Sonos son = new Sonos(pythonCmd);
			
			// get FritzBox Informations
			for(FritzBoxInformations fbinfo : fb.getWLanState()) {
				String ssid, status;
				status = fbinfo.getState();
				ssid = fbinfo.getSSID();
				output += ssid + ": " + status + "\n";  
			}
			Logger.log("FritzBox", "Info", output);
			
			// get Devices connected with the FritzBox
			FritzBoxDevice[] devices = fb.getKnownDevices();
			FritzBoxDevice[] onlineDevs = fb.getOnlineDevices(devices);
			for(FritzBoxDevice d: onlineDevs) {
				output = d.getName() + "\t" + d.getIp() + "\t" + d.getMac();
				Logger.log("FritzBox", "Info", output);
			}
			
			
			// Log the volume from the sonos
			int volume = son.getVolume();
			Logger.log("Sonos", "Info", "Volume: " + volume);
			
			
			// Wait 60s till execute the loop again
			try {
				Thread.sleep(60*1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}

}
