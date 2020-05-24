package interfaces.websocket;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebSocketServer {

    static Server server;

    public static void runServer(int port){
        server = new Server("localhost", port, "/", NormalEndpoint.class);

        try {
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stopServer(){
        server.stop();
    }


}
