package website;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import FritzBox.FritzBoxDevice;
import information.Information;
import logger.Logger;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;


/**
 * @since version 0.1
 * @author FrederikFJ
 *
 */
public class SocketHandler implements Runnable {
	
	
	Socket client;
	
	protected SocketHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		
		try {
			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			//Logger.log("WebsiteBackend", "Info", "Es ist eine Nachricht vom Client angekommen");
			
			Sonos sonos = new Sonos(Information.SonosIP);
			FritzBox fb = new FritzBox(Information.FritzBoxIP, Information.FritzBoxPassword);
			
			
			String received = reader.readLine();
			String[]params = received.split("#");
			String cmd = params[0];
			
			if(cmd.equalsIgnoreCase("setvolume") || cmd.equalsIgnoreCase("set_volume")) {
				sonos.setVolume(Integer.parseInt(params[1]));
				Logger.log("WebBackend.Sonos", "Info", "Changed Volume to " + sonos.getVolume());
			}
			
			if(cmd.equalsIgnoreCase("getvolume") || cmd.equalsIgnoreCase("get_volume")) {
				writer.write(sonos.getVolume());
				writer.flush();
				Logger.log("WebBackend.Sonos", "Info", "returned volume");
			}
			
			if(cmd.equalsIgnoreCase("louder")) {
				sonos.louder(Integer.parseInt(params[1]));
				Logger.log("WebBackend.Sonos", "Info", "Changed volume to " + sonos.getVolume());
			}
			
			if(cmd.equalsIgnoreCase("quieter")) {
				sonos.quieter(Integer.parseInt(params[1]));
				Logger.log("WebBackend.Sonos", "Info", "Changed volume to " + sonos.getVolume());
			}
			
			if(cmd.equalsIgnoreCase("getonlinedevices") || cmd.equalsIgnoreCase("get_online_devices")) {
				
				String output = "";
				FritzBoxDevice[] devices = fb.getKnownDevices();
				FritzBoxDevice[] onlineDevs = fb.getOnlineDevices(devices);
				for(FritzBoxDevice d: onlineDevs) {
					output += d.getName() + "\t" + d.getIp() + "\t" + d.getMac() + "#";
					
				}
				writer.write(output);
				writer.flush();
				
				Logger.log("WebBackend.FritzBox", "Info", "returned online Devices");
			}
			
			if(cmd.equalsIgnoreCase("getdevices") || cmd.equalsIgnoreCase("get_devices")) {
				
				String output = "";
				FritzBoxDevice[] devices = fb.getKnownDevices();
				for(FritzBoxDevice d: devices) {
					output += d.getName() + "\t" + d.getIp() + "\t" + d.getMac() + "#";
					
				}
				writer.write(output);
				writer.flush();
				
				Logger.log("WebBackend.FritzBox", "Info", "returned known Devices");
			}
			
			writer.close();
			out.close();
			
			reader.close();
			in.close();
			client.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Logger.logError("WebBackend", "The Server expected a Number of the type int but the Website send sth. different");
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			Logger.logError("WebBackend", "The server expected more params");
		}
		
	}

}
