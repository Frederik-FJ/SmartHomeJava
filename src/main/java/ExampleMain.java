
import fritzBox.FritzBoxDevice;
import fritzBox.FritzBoxInformations;
import config.ConfigFile;
import information.Information;
import information.RunningProgramInformation;
import interfaces.Interfaces;
import interfaces.bot.telegram.TelegramBot;
import interfaces.homematic.HomematicConnection;
import interfaces.websocket.WebSocketServer;
import logger.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;

public class ExampleMain {
	

	public static void main(String[] args) {

		HomematicConnection home;
		
		// Adding a Thread for the Webserver
		Thread web = new Thread(() -> WebSocketServer.runServer(1904));



		// Actions which should be executed once at the start of the program
		Logger.log("System", "Boot", "The SmartHome program boot...");
		
		Logger.log("Program", "Boot", "Detected System: " + RunningProgramInformation.RunningSystem);
		
		// Config paths
		String cmdsConfPath = RunningProgramInformation.runningPath + "config/commands.conf";

		//Change this path if yout want to export that programm
		String dataConfPath = RunningProgramInformation.runningPath + "../../../data.conf";
		
		// Create config objects
		ConfigFile cmdsConf = new ConfigFile(cmdsConfPath);
		ConfigFile dataConf = new ConfigFile(dataConfPath);
		
		//load config
		if(!cmdsConf.exists()) {
			cmdsConf.create(new String[][] {{"#","pythonCmd"},{"cmds that the system is using", "python3"}});
		}
		String pythonCmd = cmdsConf.read("pythonCmd");


		Information.SONOS_IP = dataConf.read("Sonos IP");
		Information.FRITZBOX_IP = dataConf.read("FritzBox IP");
		Information.FRITZBOX_PW = dataConf.read("FritzBox Password");

		Information.MAIL_NAME = dataConf.read("Mail Name");
		Information.MAIL_USER = dataConf.read("Mail User");
		Information.MAIL_PW = dataConf.read("Mail Password");
		Information.SENDER_MAIL = dataConf.read("Sender Mail");
		Information.MAIL_RECEIVER = dataConf.read("Mail Receiver");


		Information.HOMEMATIC_IP = dataConf.read("Homematic IP");
		Information.HOMEMATIC_NAME = dataConf.read("Homematic User");
		Information.HOMEMATIC_PASSWORD = dataConf.read("Homematic Password");

		Information.TELEGRAM_BOT_NAME = dataConf.read("Telegram Bot Name");
		Information.TELEGRAM_BOT_TOKEN = dataConf.read("Telegram Bot Token");
		String[] chats = dataConf.read("Allowed Telegram Chats").split(",");
		Information.TELEGRAM_ALLOWED_CHATS = new long[chats.length];
		for(int i = 0; i < chats.length; i++){
			try {
				Information.TELEGRAM_ALLOWED_CHATS[i] = Long.parseLong(chats[i]);
			}catch (NumberFormatException e){
				Logger.logError("Config.Telegram","Only IDs out of numbers are allowes in the config at Allowed Telegram Chats seperated with ','");
			}
		}
		Logger.log("System.boot", "Info", "loaded the config-files");


		// Start Homematic connection
		home = new HomematicConnection(Information.HOMEMATIC_IP);
		home.login(Information.HOMEMATIC_NAME, Information.HOMEMATIC_PASSWORD);
		Logger.log("System.boot", "Info", "started the connection to Homematic");

		//Start Telegram Bot
		ApiContextInitializer.init();
		TelegramBotsApi telegramApi = new TelegramBotsApi();
		TelegramBot telegramBot = null;
		try {
			telegramApi.registerBot((telegramBot = new TelegramBot()));
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
		Logger.log("System.boot", "Info", "started the Telegram Bot");


		// start websocket
		web.start();

		Interfaces interfaces = new Interfaces(telegramBot);

		// Adding a Shutdown Thread which will be executed when the User exit the program (Strg+C)
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println();
			Logger.log("System", "Shutdown", "The SmartHome programm will shut down");

			// do things that must be done before shutdown
			interfaces.send("The program shuts down", true);
			WebSocketServer.stopServer();
			web.interrupt();
			home.logout();

			Logger.log("System", "Shutdown", "The SmartHome program has been shut down");
		}));



		Logger.log("Program", "Boot", "The SmartHome program has booted");
		interfaces.send("Das Programm ist wieder hochgefahren", true);
		
		
		// The loop which will be executed all the Time
		while (true) {
			
			StringBuilder output = new StringBuilder();
			FritzBox fb = new FritzBox(pythonCmd);
			Sonos son = new Sonos(pythonCmd);
			
			// get FritzBox Informations
			for(FritzBoxInformations fbinfo : fb.getWLanState()) {
				String ssid, status;
				status = fbinfo.getState();
				ssid = fbinfo.getSSID();
				output.append(ssid).append(": ").append(status).append("\n");
			}
			Logger.log("FritzBox.State", "Info", output.toString());
			
			// get Devices connected with the FritzBox
			FritzBoxDevice[] devices = fb.getKnownDevices();
			FritzBoxDevice[] onlineDevs = fb.getOnlineDevices(devices);
			for(FritzBoxDevice d: onlineDevs) {
				output = new StringBuilder(d.getName() + "\t" + d.getIp() + "\t" + d.getMac());
				Logger.log("FritzBox.Device", "Info", output.toString());
			}
			
			
			// Log the volume from the sonos
			int volume = son.getVolume();
			Logger.log("Sonos", "Info", "Volume: " + volume);
			
			
			// Wait 60s till execute the loop again
			try {
				Thread.sleep(60*1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		

	}

}
