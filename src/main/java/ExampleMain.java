
import FritzBox.FritzBoxDevice;
import FritzBox.FritzBoxInformations;
import config.ConfigFile;
import information.Information;
import information.RunningProgramInformation;
import interfaces.websocket.WebSocketServer;
import logger.Logger;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;

public class ExampleMain {
	
	
	
	public static void main(String[] args) {
		
		// Adding a Thread for the Webserver
		Thread web = new Thread(() -> WebSocketServer.runServer(1904));

		// Adding a Shutdown Thread which will be executed when the User exit the program (Strg+C)
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println();
			Logger.log("System", "Shutdown", "The SmartHome programm will shut down");

			// do things that must be done before shutdown
			web.interrupt();

			Logger.log("System", "Shutdown", "The SmartHome program has been shut down");
		}));
		
		
		// Actions which should be executed once at the start of the program
		Logger.log("System", "Boot", "The SmartHome programm boot...");
		
		Logger.log("Programm", "Boot", "Detected System: " + RunningProgramInformation.RunningSystem);
		
		// Config paths
		String cmdsConfPath = RunningProgramInformation.runningPath + "config/commands.conf";

		//Change this path if yout want to export that programm
		String dataConfPath = RunningProgramInformation.runningPath + "../../../data.conf";
		
		// Create config objects
		ConfigFile cmdsConf = new ConfigFile(cmdsConfPath);
		ConfigFile dataConf = new ConfigFile(dataConfPath);

		web.start();
		
		//load config
		if(!cmdsConf.exists()) {
			cmdsConf.create(new String[][] {{"#","pythonCmd"},{"cmds that the system is using", "python3"}});
		}
		String pythonCmd = cmdsConf.read("pythonCmd");


		Information.SonosIP = dataConf.read("Sonos IP");
		Information.FritzBoxIP = dataConf.read("FritzBox IP");
		Information.FritzBoxPassword = dataConf.read("FritzBox Password");

		Information.mailName = dataConf.read("Mail Name");
		Information.mailUser = dataConf.read("Mail User");
		Information.mailPw = dataConf.read("Mail Password");
		Information.senderMail = dataConf.read("Sender Mail");
		Information.mailReceiver = dataConf.read("Mail Receiver");

		
		
		
		
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
