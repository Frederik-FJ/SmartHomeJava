package website;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import logger.Logger;

/**
 * @since version 0.1
 * @author FrederikFJ
 *
 */
public class ReceiveSocket {
	
	int port;
	
	/**
	 * 
	 * @param port Port of the server
	 * @since version 0.1
	 */
	public ReceiveSocket(int port) {
		this.port = port;
	}
	
	/**
	 * starts the Socket to get Informations from the Socket <br> use an extra Thread for this Method, contains a while true loop
	 * @since version 0.1 
	 */
	public void startServer() {
		
		try {
			ServerSocket server = new ServerSocket(port);
			Logger.log("WebBackend", "Info", "Webserver started");
			
			while(true) {
				Socket client = server.accept();
				
				new Thread(new SocketHandler(client)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
